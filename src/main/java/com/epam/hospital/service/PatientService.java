package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientService extends HavingDeleteMethod, HavingGetMethod {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";
    String ADDITIONAL_NAME_PARAMETER = "additionalName";
    String SURNAME_PARAMETER = "surname";
    String PHONE_PARAMETER = "phone";
    String EMAIL_PARAMETER = "email";
    String BITHDAY_PARAMETER = "birthday";

    void save(String idAsString, String name, String additionalName, String surname,
              String birthdayAsString, String phone, String email) throws AppException;

    void save(Patient patient) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    User get(String idAsString) throws AppException;

    Patient get(int id) throws AppException;

    List<Patient> getAll();

}
