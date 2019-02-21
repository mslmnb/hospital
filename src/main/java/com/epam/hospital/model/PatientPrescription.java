package com.epam.hospital.model;

import com.epam.hospital.model.handbk.PrescriptionType;
import com.epam.hospital.util.ValidationUtil;
import org.json.JSONObject;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientPrescriptionService.*;

public class PatientPrescription extends BaseEntity implements HavingJsonView {
    private Patient patient;
    private LocalDate applicationDate;
    private PrescriptionType type;
    private String description;
    private LocalDate executionDate;
    private String result;

    public PatientPrescription(Integer id, Patient patient, LocalDate applicationDate, PrescriptionType type,
                               String description, LocalDate executionDate, String result) {
        super(id);
        this.patient = patient;
        this.applicationDate = applicationDate;
        this.type = type;
        this.description = description;
        this.executionDate = executionDate;
        this.result = result;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public PrescriptionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(PATIENT_ID_PARAMETER, patient.getId());
        userJsonObj.put(APPLICATION_DATE_PARAMETER, applicationDate.format(ValidationUtil.FORMATTER));
        userJsonObj.put(TYPE_ID_PARAMETER, type.getId());
        userJsonObj.put(TYPE_PARAMETER, type.getName());
        userJsonObj.put(DESCRIPTION_PARAMETER, description);
        String executionDateAsString = (executionDate==null) ? "" : executionDate.format(ValidationUtil.FORMATTER);
        userJsonObj.put(EXECUTON_DATE_PARAMETER, executionDateAsString);
        userJsonObj.put(RESULT_PARAMETER, result);
        return userJsonObj.toString();
    }
}
