package com.epam.hospital.model.handbk;

import com.epam.hospital.model.HavingJsonView;
import com.epam.hospital.model.NamedEntity;

public class Handbk extends NamedEntity implements HavingJsonView{
    HandbkType type;

    public Handbk(Integer id, String name, HandbkType type) {
        super(id, name);
        this.type = type;
    }

    public HandbkType getType() {
        return type;
    }

    @Override
    public String getJsonString() {
        return "{ " +
                "\"id\": " + getId() + ", " +
                "\"name\": \"" + getName() + "\" " +
                "}";
    }
}
