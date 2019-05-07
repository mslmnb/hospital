package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Position;
import org.json.JSONObject;

import static com.epam.hospital.service.StaffServiceImpl.*;

/**
 *  The class represents a staff person.
 */

public class Staff extends NamedEntity {
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
        return surname==null ? "" : surname + " " + getName().charAt(0) + "." +
                (additionalName.isEmpty() ? "" : additionalName.charAt(0) + ".");
    }

    @Override
    public String getJsonString() {
        JSONObject staffJsonObj = new JSONObject();
        staffJsonObj.put(ID_PARAMETER, getId());
        staffJsonObj.put(NAME_PARAMETER, getName());
        staffJsonObj.put(ADDITIONAL_NAME_PARAMETER, additionalName);
        staffJsonObj.put(SURNAME_PARAMETER, surname);
        staffJsonObj.put(POSITION_ID_PARAMETER, getPosition().getId());
        staffJsonObj.put(POSITION_PARAMETER, getPosition().getName());
        return staffJsonObj.toString();
    }

    @Override
    public String toString() {
        return getSurnameWithInitials();
    }
}
