package com.epam.hospital.service;

import com.epam.hospital.model.PatientInspection;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

/**
 * The interface of service operations for {@code PatientInspection} entities.
 */
public interface PatientInspectionService extends CommonServiceOperationForBaseEntity<PatientInspection> {

    /**
     * Creates or updates record about patient's inspection into table by specified data
     * @param idAsString the inspection's key for specified patient as string
     * @param patientIdAsString the patient's key as string
     * @param dateAsString the date of inspection as string
     * @param inspection the result of inspection
     * @param complaints the complaints of patient
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String patientIdAsString, String dateAsString, String inspection,
              String complaints) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all inspection for specified patient
     * @param patientIdAsString the patient's key as string
     * @return the list of {@code PatientInspection} entities
     *     if records are not found that returns empty list
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientInspection> getAll(String patientIdAsString)throws UnvalidDataAppException, SqlAppException;

    /**
     * Gets all inspection for specified patient
     * @param patientId the patient's key
     * @return the list of {@code PatientInspection} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientInspection> getAll(int patientId)throws SqlAppException;
}