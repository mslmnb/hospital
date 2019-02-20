package com.epam.hospital.service;

import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientPrescriptionService extends HavingGetMethod, HavingDeleteMethod, HavingSaveMethod {
    String ID_PARAMETER = "id";
    String PATIENT_ID_PARAMETER = "patientId";
    String APPLICATION_DATE_PARAMETER = "applicationDate";
    String TYPE_ID_PARAMETER = "typeId";
    String TYPE_PARAMETER = "type";
    String DESCRIPTION_PARAMETER = "description";
    String EXECUTON_DATE_PARAMETER = "executionDate";
    String RESULT_PARAMETER = "result";

    void save(String idAsString, String patientIdAsString, String applicationDateAsString,
              String typeIdAsString, String description,
              String executionDateAsString, String resultParameter) throws AppException;

    void save(PatientPrescription prescription) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    PatientPrescription get(String idAsString) throws AppException;

    PatientPrescription get(int id) throws AppException;

    List<PatientPrescription> getAll(String patientIdAsString, String locale);

    List<PatientPrescription> getAll(int patientId, String locale);
}
