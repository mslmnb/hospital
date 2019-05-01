package com.epam.hospital.dao;

import com.epam.hospital.model.PatientDiagnosis;

import java.util.List;

/**
 * The interface of crud operation for {@code PatientDiagnosis} entity
 */

public interface PatientDiagnosisDAO extends CommonCrudOperationsForBaseEntity<PatientDiagnosis> {

    /**
     * Gets all patient's diagnosises for specified language and specified patient's key
     * @param locale the language
     * @param patientId the patient's key
     * @return the list of patient's diagnosises for specified language and specified patient's key
     *   if records are not found that returns empty list
     */
    List<PatientDiagnosis> getAll(String locale, int patientId);
}
