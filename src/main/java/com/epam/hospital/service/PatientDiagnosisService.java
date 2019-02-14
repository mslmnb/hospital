package com.epam.hospital.service;

import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientDiagnosisService extends HavingGetMethod, HavingDeleteMethod {
    String ID_PARAMETER = "id";
    String PATIENT_ID_PARAMETER = "patientId";
    String DATE_PARAMETER = "date";
    String DIAGNOSIS_ID_PARAMETER = "diagnosisId";
    String DIAGNOSIS_TYPE_ITEM_ID_PARAMETER = "diagnosisTypeId";
    String DIAGNOSIS_PARAMETER = "diagnosis";
    String DIAGNOSIS_TYPE_PARAMETER = "diagnosisType";

    void save(String idAsString, String patientIdAsString, String dateAsString,
              String diagnosisIdAsString, String diagnosisTypeIdAsString) throws AppException;

    void save(PatientDiagnosis patientDiagnosis) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    PatientDiagnosis get(String idAsString) throws AppException;

    PatientDiagnosis get(int id) throws AppException;

    List<PatientDiagnosis> getAll(String patientIdAsString, String locale);

    List<PatientDiagnosis> getAll(int patientId, String locale);

}
