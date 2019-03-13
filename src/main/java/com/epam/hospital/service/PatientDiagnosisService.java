package com.epam.hospital.service;

import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientDiagnosisService extends HavingGetMethod, HavingDeleteMethod, HavingSaveMethod {

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
