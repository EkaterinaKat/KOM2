package com.katyshevtseva.kikiorgmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.katysh.kikiorgmobile.R;
import com.katyshevtseva.kikiorgmobile.core.backup.JsonBackupService;
import com.katyshevtseva.kikiorgmobile.view.utils.AdminTaskRecycleView.TaskListAdapter;
import com.katyshevtseva.kikiorgmobile.view.utils.KomActivity;
import com.katyshevtseva.kikiorgmobile.view.utils.ViewUtils;

public class AdminActivity extends KomActivity {
    private TaskListAdapter taskListAdapter;

    public AdminActivity() {
        setOnStart(() -> taskListAdapter.updateContent());
        setImmersiveStickyMode(true);
        setOnLeftSwipe(this::finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.new_task_button).setOnClickListener(view ->
                startActivity(RtEditActivity.newIntent(AdminActivity.this, null)));
        findViewById(R.id.logs_button).setOnClickListener(view -> startActivity(new Intent(this, LogsActivity.class)));
        findViewById(R.id.backup_button).setOnClickListener(view -> {
            ViewUtils.copyToClipboard(this, JsonBackupService.INSTANCE.getBackup());
            Toast.makeText(this, "Backup copied to clipboard", Toast.LENGTH_LONG).show();
        });
        findViewById(R.id.restore_button).setOnClickListener(view -> {
            openRestoreDialog();
        });
        ViewUtils.setEditTextListener(findViewById(R.id.search_edit_text), s -> taskListAdapter.updateContent(s));
        RecyclerView taskList = findViewById(R.id.admin_task_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskListAdapter = new TaskListAdapter(this);
        taskList.setAdapter(taskListAdapter);
    }

    private void openRestoreDialog() {
        new RestoreDialog(taskListAdapter::updateContent).show(getSupportFragmentManager(), "RestoreDialog");
    }
}
