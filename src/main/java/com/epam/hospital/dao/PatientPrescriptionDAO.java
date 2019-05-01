package com.epam.hospital.dao;

import com.epam.hospital.model.PatientPrescription;

import java.util.List;

/**
 * The interface of crud operation for {@code PatientPrescription} entity
 */

public interface PatientPrescriptionDAO extends CommonCrudOperationsForBaseEntity<PatientPrescription>{

    /**
     * Gets all patient's prescriptions for specified language and specified patient's key
     * @param locale the language
     * @param patientId the patient's key
     * @return the list of patient's prescriptions for specified language and specified patient's key
     *   if records are not found that returns empty list
     */
    List<PatientPrescription> getAll(String locale, int patientId);
}

