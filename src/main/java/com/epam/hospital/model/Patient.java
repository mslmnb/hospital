package com.epam.hospital.model;

import java.time.LocalDate;

public class Patient extends NamedEntity{
    private String additionalName;
    private String surname;
    private LocalDate birthDay;
    private String phone;
    private String email;

    public Patient() {
    }

    public Patient(Integer id, String name, String additionalName, String surname, LocalDate birthDay, String phone, String email) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getSurName() {
        return surname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
