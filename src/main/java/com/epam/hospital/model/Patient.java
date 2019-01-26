package com.epam.hospital.model;

import java.time.LocalDate;

public class Patient extends NamedEntity{
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
}
