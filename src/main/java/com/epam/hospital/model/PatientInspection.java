package com.epam.hospital.model;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.epam.hospital.service.PatientInspectionService.*;

public class PatientInspection extends BaseEntity implements HavingJsonView {

    private Patient patient;
    private LocalDate date;
    private String inspection;
    private String complaints;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(PATIENT_ID_PARAMETER, patient.getId());
        userJsonObj.put(DATE_PARAMETER, date.format(formatter));
        userJsonObj.put(INSPECTION_PARAMETER, inspection);
        userJsonObj.put(COMPLAINTS_PARAMETER, complaints);
        return userJsonObj.toString();
    }
}