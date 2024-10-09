package com.katyshevtseva.kikiorgmobile.view.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.katysh.kikiorgmobile.R;
import com.katyshevtseva.kikiorgmobile.core.IrregularTaskService;
import com.katyshevtseva.kikiorgmobile.core.RegularTaskService;
import com.katyshevtseva.kikiorgmobile.core.model.IrregularTask;
import com.katyshevtseva.kikiorgmobile.core.model.RegularTask;
import com.katyshevtseva.kikiorgmobile.core.model.Task;
import com.katyshevtseva.kikiorgmobile.view.IrtEditActivity;
import com.katyshevtseva.kikiorgmobile.view.QuestionDialog;
import com.katyshevtseva.kikiorgmobile.view.QuestionDialog.AnswerHandler;
import com.katyshevtseva.kikiorgmobile.view.RtEditActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminTaskRecycleView {

    private static List<TaskListItem> getTaskListItems(String searchString) {
        List<TaskListItem> items = new ArrayList<>();
        List<IrregularTask> its = IrregularTaskService.INSTANCE.getIrregularTasks(searchString);
        items.add(getHeader("Irregular tasks (" + its.size() + ")"));
        for (IrregularTask irregularTask : its) {
            items.add(toListItem(irregularTask));
        }
        List<RegularTask> rts = RegularTaskService.INSTANCE.findRegularTasks(searchString);
        items.add(getHeader("Regular tasks (" + rts.size() + ")"));
        for (RegularTask regularTask : rts) {
            items.add(toListItem(regularTask));
        }
        return items;
    }

    static class TaskHolder extends RecyclerView.ViewHolder {
        private TaskListAdapter taskListAdapter;
        private AppCompatActivity context;

        TaskHolder(View view, TaskListAdapter taskListAdapter, AppCompatActivity context) {
            super(view);
            this.taskListAdapter = taskListAdapter;
            this.context = context;
        }

        void bind(TaskListItem item) {
            switch (item.getType()) {
                case HEADER:
                    ((TextView) itemView.findViewById(R.id.header_text_view)).setText(item.getText());
                    break;
                case TASK:
                    bindTask(item.getTask());
            }
        }

        private void bindTask(final Task task) {
            ((TextView) itemView.findViewById(R.id.task_title_view)).setText(task.getTitle());
            ((TextView) itemView.findViewById(R.id.task_desc_view)).setText(task.getAdminTaskListDesk());
            itemView.findViewById(R.id.edit_task_button).setOnClickListener(
                    view -> startEditActivity(task));
            Button deleteButton = itemView.findViewById(R.id.delete_task_button);
            deleteButton.setOnClickListener(view -> {

                AnswerHandler deletionDialogAnswerHandler = answer -> {
                    if (answer) {
                        delete(task);
                        Toast.makeText(context, "Deleted!", Toast.LENGTH_LONG).show();
                        taskListAdapter.updateContent();
                    }
                };

                DialogFragment dlg1 = new QuestionDialog("Delete?", deletionDialogAnswerHandler);
                dlg1.show(context.getSupportFragmentManager(), "dialog1");
            });
        }

        private void startEditActivity(Task task) {
            if (task instanceof RegularTask) {
                context.startActivity(RtEditActivity.newIntent(context, (RegularTask) task));
            } else if (task instanceof IrregularTask) {
                context.startActivity(IrtEditActivity.newIntent(context, (IrregularTask) task));
            } else
                throw new RuntimeException();
        }

        private void delete(Task task) {
            if (task instanceof RegularTask) {
                RegularTaskService.INSTANCE.delete((RegularTask) task);
            } else if (task instanceof IrregularTask) {
                IrregularTaskService.INSTANCE.delete((IrregularTask) task);
            } else
                throw new RuntimeException();
        }
    }

    public static class TaskListAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<TaskListItem> items;
        private final AppCompatActivity context;

        public TaskListAdapter(AppCompatActivity context) {
            this.context = context;
            updateContent();
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);
            return new TaskHolder(view, this, context);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            TaskListItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            TaskListItem item = items.get(position);
            if (item.getType() == TaskListItemType.HEADER)
                return R.layout.task_list_header;
            return R.layout.admin_task_list_item;
        }

        public void updateContent(String searchString) {
            items = getTaskListItems(searchString);
            notifyDataSetChanged();
        }

        public void updateContent() {
            updateContent(null);
        }
    }

    private enum TaskListItemType {
        TASK, HEADER
    }

    private interface TaskListItem {
        TaskListItemType getType();

        String getText();

        Task getTask();
    }

    private static TaskListItem getHeader(final String text) {
        return new TaskListItem() {
            @Override
            public TaskListItemType getType() {
                return TaskListItemType.HEADER;
            }

            @Override
            public String getText() {
                return text;
            }

            @Override
            public Task getTask() {
                return null;
            }
        };
    }

    private static TaskListItem toListItem(final RegularTask regularTask) {
        return new TaskListItem() {
            @Override
            public TaskListItemType getType() {
                return TaskListItemType.TASK;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public Task getTask() {
                return regularTask;
            }

        };
    }

    private static TaskListItem toListItem(final IrregularTask irregularTask) {
        return new TaskListItem() {
            @Override
            public TaskListItemType getType() {
                return TaskListItemType.TASK;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public Task getTask() {
                return irregularTask;
            }
        };
    }
}
