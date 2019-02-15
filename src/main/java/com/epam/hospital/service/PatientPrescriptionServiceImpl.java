package com.epam.hospital.service;

import com.epam.hospital.dao.PatientPrescriptionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.model.handbk.PrescriptionType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.*;

public class PatientPrescriptionServiceImpl implements PatientPrescriptionService {
    private final PatientPrescriptionDAO dao;

    public PatientPrescriptionServiceImpl(PatientPrescriptionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String patientIdAsString, String applicationDateAsString,
                     String typeIdAsString, String description,
                     String executionDateAsString, String resultParameter) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty())
                     ? null
                     : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER, checkResult, false);
        LocalDate applicationDate = checkAndReturnDate(applicationDateAsString,
                APPLICATION_DATE_PARAMETER, checkResult, false);
        Integer typeId = checkAndReturnInt(typeIdAsString, TYPE_ID_PARAMETER, checkResult, false);
        PrescriptionType type = new PrescriptionType(typeId);
        LocalDate executionDate = (executionDateAsString.isEmpty())
                                  ? null
                                  : checkAndReturnDate(executionDateAsString,
                                                       EXECUTON_DATE_PARAMETER, checkResult, false);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(DESCRIPTION_PARAMETER, description);
        checkNotEmpty(parameters, checkResult, true);
        PatientPrescription prescription = new PatientPrescription(id, new Patient(patientId), applicationDate,
                                                                   type, description, executionDate, resultParameter);
        save(prescription);
    }

    @Override
    public void save(PatientPrescription prescription) throws AppException {
        if (prescription.isNew()) {
            dao.create(prescription);
        } else {
            checkNotFound(dao.update(prescription));
        }
    }

    @Override
    public void delete(String idAsString) throws AppException {
        delete(checkAndReturnInt(idAsString, ID_PARAMETER));
    }

    @Override
    public void delete(int id) throws AppException {
        checkNotFound(dao.delete(id));
    }

    @Override
    public PatientPrescription get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public PatientPrescription get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<PatientPrescription> getAll(String patientIdAsString, String locale) {
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER);
        return getAll(patientId, locale);
    }

    @Override
    public List<PatientPrescription> getAll(int patientId, String locale) {
        return dao.getAll(patientId, locale);
    }
}
