package com.epam.hospital.model;

import org.json.JSONObject;

import static com.epam.hospital.service.UserServiceImpl.ID_PARAMETER;
import static com.epam.hospital.service.UserServiceImpl.NAME_PARAMETER;

/**
 *  The class represents a role of system's user.
 */

public enum Role implements HavingJsonView {
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_NURSE;

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, this.name());
        userJsonObj.put(NAME_PARAMETER, this.name());
        return userJsonObj.toString();
    }
}