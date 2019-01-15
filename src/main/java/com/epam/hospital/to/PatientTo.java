package com.epam.hospital.to;

public class PatientTo extends NamedTo{
    private String surName;

    public PatientTo() {
    }

    public PatientTo(Integer id, String name, String surName) {
        super(id, name);
        this.surName = surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getSurName() {
        return surName;
    }

    public String getJsonString () {
        return "{ " +
                "\"id\": " + getId() + ", " +
                "\"name\": \"" + getName() + "\", " +
                "\"surName\": \"" + surName + "\" " +
                '}';
    }

    @Override
    public String toString() {
        return "PatientTo" + getJsonString();
    }
}
