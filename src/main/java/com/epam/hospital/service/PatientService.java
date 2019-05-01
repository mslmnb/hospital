package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

/**
 * The interface of service operations for {@code Patient} entities.
 */
public interface PatientService extends CommonServiceOperation<Patient>, CommonServiceOperationForBaseEntity<Patient> {

    /**
     * Creates or updates record about patient into table by specified data
     * @param idAsString the patient's key as string
     * @param name the patient's name
     * @param additionalName the patient's additional name
     * @param surname the patient's surname
     * @param birthdayAsString the patient's birthday as string
     * @param phone the patient's phone
     * @param email the patient's email
     * @param admissionDateAsString the patient's admission date as string
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String name, String additionalName, String surname,
              String birthdayAsString, String phone, String email,
              String admissionDateAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Updates record about patient's primary exam and discharge into table by specified data
     * @param idAsString the patient's key as string
     * @param primaryInspection the patient's primary inspection
     * @param primaryComplaints the patient's primary complaints
     * @param primaryDiagnssIdAsString the key diagnosis from handbook for primary diagnosis
     * @param finalDiagnssIdAsString the key diagnosis from handbook for final diagnosis
     * @param dischargeDateAsString the patient's discharge date as string
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void updatePrimaryExamAndDischarge(String idAsString, String primaryInspection, String primaryComplaints,
                                       String primaryDiagnssIdAsString, String finalDiagnssIdAsString,
                                       String dischargeDateAsString) throws UnvalidDataAppException,
                                                                            NotFoundAppException, SqlAppException;

    /**
     * Updates record about patient's primary exam and discharge into table by specified {@code Patient} entity
     * @param patient the {@code Patient} entity
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void updatePrimaryExamAndDischarge(Patient patient) throws NotFoundAppException, SqlAppException;
}
