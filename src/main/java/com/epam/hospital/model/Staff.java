package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Position;
import org.json.JSONObject;

import static com.epam.hospital.service.StaffServiceImpl.*;

public class Staff extends NamedEntity implements HavingJsonView{
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
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(NAME_PARAMETER, getName());
        userJsonObj.put(ADDITIONAL_NAME_PARAMETER, additionalName);
        userJsonObj.put(SURNAME_PARAMETER, surname);
        userJsonObj.put(POSITION_ID_PARAMETER, getPosition().getId());
        userJsonObj.put(POSITION_PARAMETER, getPosition().getName());
        return userJsonObj.toString();
    }

    @Override
    public String toString() {
        return getSurnameWithInitials();
    }
}
