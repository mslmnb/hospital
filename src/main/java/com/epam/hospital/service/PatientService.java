package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.List;

public interface PatientService extends HavingDeleteMethod, HavingGetMethod {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";
    String ADDITIONAL_NAME_PARAMETER = "additionalName";
    String SURNAME_PARAMETER = "surname";
    String PHONE_PARAMETER = "phone";
    String EMAIL_PARAMETER = "email";
    String BITHDAY_PARAMETER = "birthday";
    String ADMISSION_DATE_PARAMETER = "admissionDate";
    String DISCHARGE_DATE_PARAMETER = "dischargeDate";
    String PRIMARY_DIAGNOSIS_ID_PARAMETER = "primaryDiagnosisId";
    String FINAL_DIAGNOSIS_ID_PARAMETER = "finalDiagnosisId";
    String PRIMARY_INSPECTION_PARAMETER = "primaryInspection";
    String PRIMARY_COMPLAINTS_PARAMETER = "primaryComplaints";


    void save(String idAsString, String name, String additionalName, String surname,
              String birthdayAsString, String phone, String email,
              String admissionDateAsString) throws AppException;

    void save(Patient patient) throws AppException;

    void updatePrimaryExamAndDischarge(String idAsString, String primaryInspection, String primaryComplaints,
                                       String primaryDiagnssIdAsString, String finalDiagnssIdAsString,
                                       String dischargeDateAsString) throws AppException;

    void updatePrimaryExamAndDischarge(Patient patient) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    Patient get(String idAsString) throws AppException;

    Patient get(int id) throws AppException;

    List<Patient> getAll();

}
