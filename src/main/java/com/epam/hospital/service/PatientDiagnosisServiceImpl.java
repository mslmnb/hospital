package com.epam.hospital.service;

import com.epam.hospital.dao.PatientDiagnosisDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.handbk.Diagnosis;
import com.epam.hospital.model.handbk.DiagnosisType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnDate;
import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class PatientDiagnosisServiceImpl implements PatientDiagnosisService {
    public static final String ID_PARAMETER = "id";
    public static final String PATIENT_ID_PARAMETER = "patientId";
    public static final String DATE_PARAMETER = "date";
    public static final String DIAGNOSIS_ID_PARAMETER = "diagnosisId";
    public static final String DIAGNOSIS_TYPE_ITEM_ID_PARAMETER = "diagnosisTypeId";
    public static final String DIAGNOSIS_PARAMETER = "diagnosis";
    public static final String DIAGNOSIS_TYPE_PARAMETER = "diagnosisType";

    private final PatientDiagnosisDAO dao;

    public PatientDiagnosisServiceImpl(PatientDiagnosisDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String patientIdAsString, String dateAsString,
                     String diagnosisIdAsString, String diagnosisTypeIdAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER, checkResult, false);
        LocalDate date = checkAndReturnDate(dateAsString, DATE_PARAMETER, checkResult, false);
        Integer diagnosisTypeId = checkAndReturnInt(diagnosisTypeIdAsString,
                                                    DIAGNOSIS_TYPE_ITEM_ID_PARAMETER, checkResult, false);
        Integer diagnosisId = checkAndReturnInt(diagnosisIdAsString, DIAGNOSIS_ID_PARAMETER, checkResult, true);
        Patient patient = new Patient(patientId);
        DiagnosisType diagnosisType = new DiagnosisType(diagnosisTypeId);
        Diagnosis diagnosis = new Diagnosis(diagnosisId);
        PatientDiagnosis patientDiagnosis = new PatientDiagnosis(id, patient, date, diagnosisType, diagnosis);
        save(patientDiagnosis);
    }

    @Override
    public void save(PatientDiagnosis patientDiagnosis) throws AppException {
        if (patientDiagnosis.isNew()) {
            dao.create(patientDiagnosis);
        } else {
            checkNotFound(dao.update(patientDiagnosis));
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
    public PatientDiagnosis get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public PatientDiagnosis get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<PatientDiagnosis> getAll(String patientIdAsString, String locale) throws AppException {
        Integer patientId = checkAndReturnInt(patientIdAsString, PATIENT_ID_PARAMETER);
        return getAll(patientId, locale);
    }

    @Override
    public List<PatientDiagnosis> getAll(int patientId, String locale) {
        return dao.getAll(patientId, locale);
    }
}
