package com.epam.hospital.model;

import org.json.JSONObject;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientService.*;

public class Patient extends NamedEntity implements HavingJsonView{
    private String additionalName;
    private String surname;
    private LocalDate birthday;
    private String phone;
    private String email;

    public Patient() {
    }

    public Patient(Integer id, String name, String additionalName, String surname,
                   LocalDate birthday, String phone, String email) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(NAME_PARAMETER, getName());
        userJsonObj.put(ADDITIONAL_NAME_PARAMETER, additionalName);
        userJsonObj.put(SURNAME_PARAMETER, surname);
        userJsonObj.put(PHONE_PARAMETER, phone);
        userJsonObj.put(EMAIL_PARAMETER, email);
        userJsonObj.put(BITHDAY_PARAMETER, getBirthday());
        return userJsonObj.toString();
    }
}
