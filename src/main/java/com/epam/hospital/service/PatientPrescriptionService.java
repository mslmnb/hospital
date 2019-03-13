package com.epam.hospital.service;

import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientPrescriptionService extends HavingGetMethod, HavingDeleteMethod, HavingSaveMethod {

    void save(String idAsString, String patientIdAsString, String applicationDateAsString,
              String typeIdAsString, String description,
              String executionDateAsString, String resultNotes) throws AppException;

    void save(PatientPrescription prescription) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    PatientPrescription get(String idAsString) throws AppException;

    PatientPrescription get(int id) throws AppException;

    List<PatientPrescription> getAll(String patientIdAsString, String locale);

    List<PatientPrescription> getAll(int patientId, String locale);
}
