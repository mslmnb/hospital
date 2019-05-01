package com.epam.hospital.service;

import com.epam.hospital.dao.PatientInspectionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientInspection;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.List;

import static com.epam.hospital.util.ValidationUtil.*;

/**
 * The class of service operation for {@code PatientInspection} entity
 */

public class PatientInspectionServiceImpl implements PatientInspectionService{
    public static final String PATIENT_ID_PARAMETER = "patientId";
    public static final String DATE_PARAMETER = "date";
    public static final String INSPECTION_PARAMETER = "inspection";
    public static final String COMPLAINTS_PARAMETER = "complaints";

    private final PatientInspectionDAO dao;

    public PatientInspectionServiceImpl(PatientInspectionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String patientIdAsString, String dateAsString,
                     String inspection, String complaints) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty())
                ? null
                : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER, checkResult, false);
        LocalDate date = checkAndReturnDate(dateAsString, DATE_PARAMETER, checkResult, false);
        checkNotEmpty(inspection, INSPECTION_PARAMETER, checkResult, false);
        checkNotEmpty(complaints, COMPLAINTS_PARAMETER, checkResult);
        PatientInspection patientInspection = new PatientInspection(id, new Patient(patientId),
                                                                    date, inspection, complaints);
        save(patientInspection);
    }

    @Override
    public void save(PatientInspection patientInspection) throws AppException {
        if (patientInspection.isNew()) {
            dao.create(patientInspection);
        } else {
            checkNotFound(dao.update(patientInspection));
        }
    }

    @Override
    public void save(String... args) throws AppException {
        save(args[0], args[1], args[2], args[3], args[4]);
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
    public PatientInspection get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public PatientInspection get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<PatientInspection> getAll(String patientIdAsString) {
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER);
        return getAll(patientId);
    }

    @Override
    public List<PatientInspection> getAll(int patientId) {
        return dao.getAll(patientId);
    }
}
