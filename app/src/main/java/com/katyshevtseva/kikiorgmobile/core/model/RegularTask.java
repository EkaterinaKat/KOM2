package com.katyshevtseva.kikiorgmobile.core.model;

import static com.katyshevtseva.kikiorgmobile.utils.GeneralUtil.getLoppedDateListString;

import com.katyshevtseva.kikiorgmobile.core.enums.PeriodType;
import com.katyshevtseva.kikiorgmobile.core.enums.TaskType;
import com.katyshevtseva.kikiorgmobile.core.enums.TaskUrgency;
import com.katyshevtseva.kikiorgmobile.core.enums.TimeOfDay;
import com.katyshevtseva.kikiorgmobile.db.lib.Entity;

import java.util.Date;
import java.util.List;

public class RegularTask implements Task, Entity {
    private long id;
    private String title;
    private String desc;
    private PeriodType periodType;
    private int period;
    private List<Date> dates;
    private TaskUrgency urgency;
    private TimeOfDay timeOfDay;

    public String getAdminTaskListDesk() {
        return String.format("%s\n\n%s\n%s %s\n%s",
                desc, timeOfDay, period, periodType, getLoppedDateListString(dates));
    }

    public String getLogTaskDesk() {
        return String.format("[(%d) %s \n%s \n(%s, %d %s)]", id, title, desc, timeOfDay, period, periodType);
    }

    @Override
    public String toString() {
        return title;
    }

    public String getBackupString() {
        return "RegularTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", periodType=" + periodType +
                ", period=" + period +
                ", dates=" + dates +
                '}';
    }

    @Override
    public TaskType getType() {
        return TaskType.REGULAR;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    @Override
    public TaskUrgency getUrgency() {
        return urgency;
    }

    @Override
    public void setUrgency(TaskUrgency urgency) {
        this.urgency = urgency;
    }

    @Override
    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
}
