package com.epam.hospital.to;

import com.epam.hospital.HavingId;

public abstract class BaseTo implements HavingId {
    private Integer id;

    protected BaseTo() {
    }

    protected BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
