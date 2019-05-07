package com.epam.hospital.service;

import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

public interface PatientPrescriptionService extends CommonServiceOperationForBaseEntity<PatientPrescription> {

    /**
     * Creates or updates record about patient's prescription into table by specified data
     * @param idAsString the prescription' key for specified patient as string
     * @param patientIdAsString the patient's key as string
     * @param applicationDateAsString the application date of prescription as string
     * @param typeIdAsString the key of prescription's type from handbook
     * @param description the description of prescription
     * @param executionDateAsString the execution date of prescription as string
     * @param resultNotes the notes about execution
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String patientIdAsString, String applicationDateAsString,
              String typeIdAsString, String description, String executionDateAsString,
              String resultNotes) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all prescription for specified patient
     * @param patientIdAsString the patient's key as string
     * @param locale the language
     * @return the list of {@code PatientPrescription} entities
     *     if records are not found that returns empty list
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientPrescription> getAll(String patientIdAsString,
                                     String locale)throws UnvalidDataAppException, SqlAppException;

    /**
     * Gets all prescription for specified patient
     * @param patientId the patient's key
     * @param locale the language
     * @return the list of {@code PatientPrescription} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientPrescription> getAll(int patientId, String locale)throws SqlAppException;
}
