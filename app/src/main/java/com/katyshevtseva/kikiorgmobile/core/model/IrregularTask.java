package com.katyshevtseva.kikiorgmobile.core.model;

import static com.katyshevtseva.kikiorgmobile.utils.DateUtils.getDateString;

import com.katyshevtseva.kikiorgmobile.core.enums.TaskType;
import com.katyshevtseva.kikiorgmobile.core.enums.TaskUrgency;
import com.katyshevtseva.kikiorgmobile.core.enums.TimeOfDay;
import com.katyshevtseva.kikiorgmobile.db.lib.Entity;
import com.katyshevtseva.kikiorgmobile.utils.DateUtils;

import java.util.Date;

public class IrregularTask implements Task, Entity {
    private long id;
    private String title;
    private String desc;
    private Date date;
    private TaskUrgency urgency;
    private TimeOfDay timeOfDay;

    public IrregularTask() {
    }

    public IrregularTask(long id, String title, String desc, Date date, TaskUrgency urgency, TimeOfDay timeOfDay) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.urgency = urgency;
        this.timeOfDay = timeOfDay;
    }

    public String getAdminTaskListDesk() {
        return String.format("%s\n\n%s\n%s",
                desc, timeOfDay, DateUtils.getDateStringWithWeekDay(date));
    }

    public String getLogTaskDesk() {
        return String.format("[(%d) %s \n%s \n(%s, %s)]", id, title, desc, timeOfDay, getDateString(date));
    }

    @Override
    public TaskType getType() {
        return TaskType.IRREGULAR;
    }

    public String getBackupString() {
        return "IrregularTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", timeOfDay=" + timeOfDay +
                ", date=" + date +
                '}';
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public TaskUrgency getUrgency() {
        return urgency;
    }

    @Override
    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public void setUrgency(TaskUrgency urgency) {
        this.urgency = urgency;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
}
