package com.katyshevtseva.kikiorgmobile.core.model;

import com.katyshevtseva.kikiorgmobile.db.lib.Entity;

public class OptionalTask implements Entity {
    private long id;
    private String title;

    public OptionalTask() {
    }

    public OptionalTask(long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
