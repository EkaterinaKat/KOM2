package com.katyshevtseva.kikiorgmobile.core;

import com.katyshevtseva.kikiorgmobile.core.enums.TaskType;
import com.katyshevtseva.kikiorgmobile.core.enums.TaskUrgency;
import com.katyshevtseva.kikiorgmobile.core.model.IrregularTask;
import com.katyshevtseva.kikiorgmobile.core.model.RegularTask;
import com.katyshevtseva.kikiorgmobile.core.model.Task;
import com.katyshevtseva.kikiorgmobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Service {
    public static Service INSTANCE;
    private final KomDao komDao;

    public static void init(KomDao komDao) {
        INSTANCE = new Service(komDao);
    }

    private Service(KomDao komDao) {
        this.komDao = komDao;
    }

    public List<Task> getTasksForMainList(Date date) {
        List<Task> tasks = new ArrayList<>();

        tasks.addAll(komDao.getIrregularTasksByDate(date));
        tasks.addAll(komDao.getRegularTasksByDate(date));

        return tasks;
    }

    public Date getEarliestTaskDate() {
        Stream<Date> irtDatesStream = komDao.getAllIrregularTasks().stream()
                .map(IrregularTask::getDate);

        Stream<Date> rtDatesStream = RegularTaskService.INSTANCE.findRegularTasks(null).stream()
                .flatMap(task -> task.getDates().stream());

        return Stream.concat(irtDatesStream, rtDatesStream).sorted().findFirst().orElse(null);
    }

    public boolean overdueTasksExist() {
        Date earliestTaskDate = getEarliestTaskDate();
        if(earliestTaskDate==null){
            return false;
        }
        return DateUtils.beforeIgnoreTime(earliestTaskDate, new Date());
    }

    public IrregularTask makeIrregularTaskFromRegular(long regularTaskId) {
        RegularTask regularTask = komDao.getRegularTaskById(regularTaskId);
        IrregularTask task = new IrregularTask();
        task.setUrgency(regularTask.getUrgency());
        task.setTitle(regularTask.getTitle());
        task.setDesc(regularTask.getDesc());
        task.setTimeOfDay(regularTask.getTimeOfDay());
        task.setDate(new Date());
        return task;
    }

    public void setUrgency(Task task, TaskUrgency urgency) {
        task.setUrgency(urgency);

        if (task.getType() == TaskType.REGULAR) {
            komDao.update((RegularTask) task);
        } else {
            komDao.update((IrregularTask) task);
        }
    }
}
