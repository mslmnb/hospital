package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.dao.PatientDAO;
import com.epam.hospital.model.handbk.Diagnosis;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.List;

import static com.epam.hospital.util.ValidationUtil.*;

public class PatientServiceImpl implements PatientService {
    public static final String ID_PARAMETER = "id";
    public static final String NAME_PARAMETER = "name";
    public static final String ADDITIONAL_NAME_PARAMETER = "additionalName";
    public static final String SURNAME_PARAMETER = "surname";
    public static final String FULLNAME_PARAMETER = "fullname";
    public static final String PHONE_PARAMETER = "phone";
    public static final String EMAIL_PARAMETER = "email";
    public static final String BITHDAY_PARAMETER = "birthday";
    public static final String ADMISSION_DATE_PARAMETER = "admissionDate";
    public static final String DISCHARGE_DATE_PARAMETER = "dischargeDate";
    public static final String PRIMARY_DIAGNOSIS_ID_PARAMETER = "primaryDiagnosisId";
    public static final String FINAL_DIAGNOSIS_ID_PARAMETER = "finalDiagnosisId";
    public static final String PRIMARY_INSPECTION_PARAMETER = "primaryInspection";
    public static final String PRIMARY_COMPLAINTS_PARAMETER = "primaryComplaints";

    private final PatientDAO dao;

    public PatientServiceImpl(PatientDAO dao) {
        this.dao = dao;
    }


    @Override
    public void save(String idAsString, String name, String additionalName, String surname,
                     String birthdayAsString, String phone, String email,
                     String admissionDateAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        checkNotEmpty(name, NAME_PARAMETER, checkResult, false);
        checkNotEmpty(surname, SURNAME_PARAMETER, checkResult, false);
        LocalDate birthday = checkAndReturnDate(birthdayAsString, BITHDAY_PARAMETER, checkResult, false);
        checkPhone(phone, checkResult, false);
        checkEmail(email, checkResult, false);
        LocalDate admissionDate = checkAndReturnDate(admissionDateAsString,
                                                     ADMISSION_DATE_PARAMETER, checkResult, true);
        Patient patient = new Patient(id, name, additionalName, surname,
                birthday, phone, email, admissionDate);
        save(patient);
    }

    @Override
    public void save(Patient patient) throws AppException {
        if (patient.isNew()) {
            dao.create(patient);
        } else {
            checkNotFound(dao.update(patient));
        }
    }

    @Override
    public void save(String... args) throws AppException {
        save(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
    }

    @Override
    public void updatePrimaryExamAndDischarge(String idAsString, String primaryInspection, String primaryComplaints,
                                              String primaryDiagnssIdAsString, String finalDiagnssIdAsString,
                                              String dischargeDateAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Diagnosis primaryDiagnosis;
        if ((primaryDiagnssIdAsString == null || primaryDiagnssIdAsString.isEmpty()) &&
                (primaryInspection == null || primaryInspection.isEmpty()) &&
                (primaryComplaints == null || primaryComplaints.isEmpty())) {
            primaryDiagnosis = null;
        } else {
            checkNotEmpty(primaryComplaints, PRIMARY_COMPLAINTS_PARAMETER, checkResult, false);
            checkNotEmpty(primaryInspection, PRIMARY_INSPECTION_PARAMETER, checkResult, false);
            primaryDiagnosis = new Diagnosis(checkAndReturnInt(primaryDiagnssIdAsString, PRIMARY_DIAGNOSIS_ID_PARAMETER,
                    checkResult, false));
        }
        Diagnosis finalDiagnosis;
        LocalDate dischargeDate;
        if ((finalDiagnssIdAsString == null || finalDiagnssIdAsString.isEmpty()) &&
                (dischargeDateAsString == null || dischargeDateAsString.isEmpty())) {
            finalDiagnosis = null;
            dischargeDate = null;
        } else {
            dischargeDate = checkAndReturnDate(dischargeDateAsString, DISCHARGE_DATE_PARAMETER,
                                               checkResult, false);
            finalDiagnosis = new Diagnosis(checkAndReturnInt(finalDiagnssIdAsString,
                    FINAL_DIAGNOSIS_ID_PARAMETER, checkResult, false));
        }
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, true);
        Patient patient = new Patient(id, primaryInspection, primaryComplaints, primaryDiagnosis,
                                      finalDiagnosis, dischargeDate);
        updatePrimaryExamAndDischarge(patient);
    }

    @Override
    public void updatePrimaryExamAndDischarge(Patient patient) throws AppException {
        checkNotFound(dao.updatePrimaryExamAndDischarge(patient));
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
    public Patient get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public Patient get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<Patient> getAll() {
        return dao.getAll();
    }
}
