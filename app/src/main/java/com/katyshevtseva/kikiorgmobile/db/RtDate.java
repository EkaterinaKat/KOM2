package com.katyshevtseva.kikiorgmobile.db;

import com.katyshevtseva.kikiorgmobile.db.lib.Entity;

import java.util.Date;

class RtDate implements Entity {
    private long id;
    private long regularTaskId;
    private Date value;

    public RtDate() {
    }

    public RtDate(long id, long regularTaskId, Date value) {
        this.id = id;
        this.regularTaskId = regularTaskId;
        this.value = value;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegularTaskId() {
        return regularTaskId;
    }

    public void setRegularTaskId(long regularTaskId) {
        this.regularTaskId = regularTaskId;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
