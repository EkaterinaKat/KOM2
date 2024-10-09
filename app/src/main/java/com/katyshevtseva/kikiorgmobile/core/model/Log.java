package com.katyshevtseva.kikiorgmobile.core.model;

import static com.katyshevtseva.kikiorgmobile.utils.DateUtils.getDateTimeString;

import com.katyshevtseva.kikiorgmobile.db.lib.Entity;

import java.util.Date;


public class Log implements Entity {
    private long id;
    private Date date;
    private Action action;
    private Subject subject;
    private String desc;

    public Log() {
    }

    public Log(long id, Date date, Action action, Subject subject, String desc) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.desc = desc;
    }

    public Log(Date date, Action action, Subject subject, String desc) {
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.desc = desc;
    }

    public enum Action {
        CREATION("#5CCBFF"),
        DELETION("#FF685C"),
        RESCHEDULE("#FFEC5C"),
        COMPLETION("#67FF5C"),
        ARCHIVATION("#999999"),
        EDITING("#CB5CFF"),
        RESUME("#1F75FE");

        private final String color;

        Action(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    public enum Subject {
        REGULAR_TASK, IRREGULAR_TASK, DATELESS_TASK, OPTIONAL_TASK
    }

    public String getFullDesc() {
        return String.format("%d) %s %s \n%s\n%s", id, action, subject, desc, getDateTimeString(date));
    }

    public String getBackupString() {
        return "Log{" +
                "id=" + id +
                ", date=" + date +
                ", action=" + action +
                ", subject=" + subject +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
