package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Diagnosis;
import com.epam.hospital.model.handbk.DiagnosisType;
import com.epam.hospital.util.ValidationUtil;
import org.json.JSONObject;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientDiagnosisServiceImpl.*;

/**
 *  The class represents a patient's medical diagnosis.
 */

public class PatientDiagnosis extends BaseEntity {
    private final Patient patient;
    private final LocalDate date;
    private final DiagnosisType diagnosisType;
    private final Diagnosis diagnosis;

    public PatientDiagnosis(Integer id, Patient patient, LocalDate date, DiagnosisType diagnosisType, Diagnosis diagnosis) {
        super(id);
        this.patient = patient;
        this.date = date;
        this.diagnosisType = diagnosisType;
        this.diagnosis = diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public DiagnosisType getDiagnosisType() {
        return diagnosisType;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(DATE_PARAMETER, date.format(ValidationUtil.FORMATTER));
        userJsonObj.put(DIAGNOSIS_ID_PARAMETER, diagnosis.getId());
        userJsonObj.put(DIAGNOSIS_PARAMETER, diagnosis.getName());
        userJsonObj.put(DIAGNOSIS_TYPE_ITEM_ID_PARAMETER, diagnosisType.getId());
        userJsonObj.put(DIAGNOSIS_TYPE_PARAMETER, diagnosisType.getName());
        return userJsonObj.toString();
    }
}
