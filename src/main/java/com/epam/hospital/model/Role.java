package com.epam.hospital.model;

public enum Role implements HavingJsonView {
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_NURSE;

    @Override
    public String getJsonString() {
        return "{ \"role\": \"" + this.name() + "\" }";
    }
}