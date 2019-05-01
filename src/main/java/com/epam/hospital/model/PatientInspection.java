package com.epam.hospital.model;

import com.epam.hospital.util.ValidationUtil;
import org.json.JSONObject;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientInspectionServiceImpl.*;

public class PatientInspection extends BaseEntity {

    private final Patient patient;
    private final LocalDate date;
    private final String inspection;
    private final String complaints;

    public PatientInspection(Integer id, Patient patient, LocalDate date, String inspection, String complaints) {
        super(id);
        this.patient = patient;
        this.date = date;
        this.inspection = inspection;
        this.complaints = complaints;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getInspection() {
        return inspection;
    }

    public String getComplaints() {
        return complaints;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(PATIENT_ID_PARAMETER, patient.getId());
        userJsonObj.put(DATE_PARAMETER, date.format(ValidationUtil.FORMATTER));
        userJsonObj.put(INSPECTION_PARAMETER, inspection);
        userJsonObj.put(COMPLAINTS_PARAMETER, complaints);
        return userJsonObj.toString();
    }
}
