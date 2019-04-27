package com.epam.hospital.service;

import com.epam.hospital.dao.PatientPrescriptionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.model.handbk.PrescriptionType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.List;

import static com.epam.hospital.util.ValidationUtil.*;

public class PatientPrescriptionServiceImpl implements PatientPrescriptionService {
    public static final String ID_PARAMETER = "id";
    public static final String PATIENT_ID_PARAMETER = "patientId";
    public static final String APPLICATION_DATE_PARAMETER = "applicationDate";
    public static final String TYPE_ID_PARAMETER = "typeId";
    public static final String TYPE_PARAMETER = "type";
    public static final String DESCRIPTION_PARAMETER = "description";
    public static final String EXECUTON_DATE_PARAMETER = "executionDate";
    public static final String RESULT_PARAMETER = "result";

    private final PatientPrescriptionDAO dao;

    public PatientPrescriptionServiceImpl(PatientPrescriptionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String patientIdAsString,
                     String applicationDateAsString, String typeIdAsString, String description,
                     String executionDateAsString, String resultNotes) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty())
                     ? null
                     : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER, checkResult, false);
        LocalDate applicationDate = checkAndReturnDate(applicationDateAsString,
                APPLICATION_DATE_PARAMETER, checkResult, false);
        Integer typeId = checkAndReturnInt(typeIdAsString, TYPE_ID_PARAMETER, checkResult, false);
        PrescriptionType type = new PrescriptionType(typeId);
        LocalDate executionDate;
        if (resultNotes.isEmpty()) {
            executionDate = (executionDateAsString.isEmpty())
                            ? null
                            : checkAndReturnDate(executionDateAsString,
                                                 EXECUTON_DATE_PARAMETER, checkResult, false);
        } else {
            executionDate = checkAndReturnDate(executionDateAsString,
                                               EXECUTON_DATE_PARAMETER, checkResult, false);
        }
        checkNotEmpty(description, DESCRIPTION_PARAMETER, checkResult);
        PatientPrescription prescription = new PatientPrescription(id, new Patient(patientId), applicationDate,
                                                                   type, description, executionDate, resultNotes);
        save(prescription);
    }

    @Override
    public void save(PatientPrescription patientPrescription) throws AppException {
        if (patientPrescription.isNew()) {
            dao.create(patientPrescription);
        } else {
            checkNotFound(dao.update(patientPrescription));
        }
    }

    @Override
    public void save(String... args) throws AppException {
        save(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
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
