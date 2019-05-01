package com.epam.hospital.dao;

import com.epam.hospital.model.PatientInspection;

import java.util.List;

/**
 * The interface of crud operation for {@code PatientInspection} entity
 */

public interface PatientInspectionDAO extends CommonCrudOperationsForBaseEntity<PatientInspection> {

    /**
     * Gets all patient's inspection for specified patient's key
     * @param patientId the patient's key
     * @return the list of patient's inspection for specified patient's key
     *     if records are not found that returns empty list
     */
    List<PatientInspection> getAll(int patientId);
}
