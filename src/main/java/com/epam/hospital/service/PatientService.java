package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientService extends HavingDeleteMethod, HavingGetMethod, HavingSaveMethod {

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
