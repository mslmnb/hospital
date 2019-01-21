package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Position;

public class Staff extends NamedEntity{
    private String additionalName;
    private String surname;
    private Position position;

    public Staff() {
    }

    public Staff(Integer id) {
        super(id, null);
    }

    public Staff(Integer id, String name, String additionalName, String surname, Position position) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.position = position;
    }

    public Staff(Integer id, String name, String additionalName, String surname) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.position = null;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getSurnameWithInitials() {
        return surname + " " + getName().charAt(0) + "." +
                (additionalName.isEmpty() ? "" : additionalName.charAt(0) + ".");
    }
}
