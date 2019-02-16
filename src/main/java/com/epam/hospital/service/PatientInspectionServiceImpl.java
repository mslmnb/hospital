package com.epam.hospital.service;

import com.epam.hospital.dao.PatientInspectionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientInspection;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.*;

public class PatientInspectionServiceImpl implements PatientInspectionService{
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
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(INSPECTION_PARAMETER, inspection);
        parameters.put(COMPLAINTS_PARAMETER, complaints);
        checkNotEmpty(parameters, checkResult, true);
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
