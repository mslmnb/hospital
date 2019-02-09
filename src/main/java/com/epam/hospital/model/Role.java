package com.epam.hospital.model;

import org.json.JSONObject;

import static com.epam.hospital.service.UserService.ROLE_PARAMETER;

public enum Role implements HavingJsonView {
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_NURSE;

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ROLE_PARAMETER, this.name());
        return userJsonObj.toString();
    }
}