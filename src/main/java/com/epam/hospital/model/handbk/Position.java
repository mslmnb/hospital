package com.epam.hospital.model.handbk;

import com.epam.hospital.model.NamedEntity;

import static com.epam.hospital.model.handbk.Handbk.POSITION;

public class Position extends NamedEntity implements UsingHandbk {

    public Position(Integer id, String name) {
        super(id, name);
    }

    @Override
    public Handbk getHandbk() {
        return POSITION;
    }
}
