package com.epam.hospital.dao;

import com.epam.hospital.model.Patient;

/**
 * The interface of crud operation for {@code Patient} entity
 */

public interface PatientDAO extends CommonCrudOperations<Patient>, CommonCrudOperationsForBaseEntity<Patient> {

    /**
     * Updates the primary exam and the discharge for specified patient
     * @param patient the patient
     * @return the patient
     *   if specified patient are not found that returns null
     */
    Patient updatePrimaryExamAndDischarge(Patient patient);
}
