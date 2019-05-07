package com.epam.hospital.service;

import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

/**
 * The interface of service operations for {@code PatientDiagnosis} entities.
 */
public interface PatientDiagnosisService extends CommonServiceOperationForBaseEntity<PatientDiagnosis> {

    /**
     * Creates or updates record about patient's diagnosis into table by specified data
     * @param idAsString the diagnosis' key for specified patient as string
     * @param patientIdAsString the patient's key as string
     * @param dateAsString the date of diagnosis as string
     * @param diagnosisIdAsString the diagnosis' key from handbook
     * @param diagnosisTypeIdAsString the key of diagnosis' type from handbook
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String patientIdAsString, String dateAsString, String diagnosisIdAsString,
              String diagnosisTypeIdAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all diagnoses for specified patient
     * @param patientIdAsString the patient's key as string
     * @param locale the language
     * @return the list of {@code PatientDiagnosis} entities
     *     if records are not found that returns empty list
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientDiagnosis> getAll(String patientIdAsString,
                                  String locale) throws UnvalidDataAppException, SqlAppException;

    /**
     * Gets all diagnoses for specified patient
     * @param patientId the patient's key
     * @param locale the language
     * @return the list of {@code PatientDiagnosis} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<PatientDiagnosis> getAll(int patientId, String locale) throws SqlAppException;
}
