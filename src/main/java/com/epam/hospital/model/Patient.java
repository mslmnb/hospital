package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Diagnosis;
import org.json.JSONObject;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientService.*;
import static com.epam.hospital.util.ValidationUtil.FORMATTER;

public class Patient extends NamedEntity implements HavingJsonView{
    private String additionalName;
    private String surname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private LocalDate admissionDate;
    private String primaryInspection;
    private String primaryComplaints;
    private Diagnosis primaryDiagnosis;
    private Diagnosis finalDiagnosis;
    private LocalDate dischargeDate;

    public Patient() {
    }

    public Patient(Integer id) {
        super(id, null);
    }

    public Patient(Integer id, String name, String additionalName, String surname, LocalDate birthday,
                   String phone, String email, LocalDate admissionDate, LocalDate dischargeDate) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public Patient(Integer id, String name, String additionalName, String surname, LocalDate birthday,
                   String phone, String email, LocalDate admissionDate) {
        super(id, name);
        this.additionalName = additionalName;
        this.surname = surname;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.admissionDate = admissionDate;
    }

    public Patient(Integer id, String primaryInspection, String primaryComplaints,
                   Diagnosis primaryDiagnosis, Diagnosis finalDiagnosis, LocalDate dischargeDate) {
        super(id, null);
        this.primaryInspection = primaryInspection;
        this.primaryComplaints = primaryComplaints;
        this.primaryDiagnosis = primaryDiagnosis;
        this.finalDiagnosis = finalDiagnosis;
        this.dischargeDate = dischargeDate;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public Diagnosis getFinalDiagnosis() {
        return finalDiagnosis;
    }

    public String getPrimaryInspection() {
        return primaryInspection;
    }

    public String getPrimaryComplaints() {
        return primaryComplaints;
    }

    public void setFinalDiagnosis(Diagnosis finalDiagnosis) {
        this.finalDiagnosis = finalDiagnosis;
    }

    public Diagnosis getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    public void setPrimaryDiagnosis(Diagnosis primaryDiagnosis) {
        this.primaryDiagnosis = primaryDiagnosis;
    }

    public void setPrimaryInspection(String primaryInspection) {
        this.primaryInspection = primaryInspection;
    }

    public void setPrimaryComplaints(String primaryComplaints) {
        this.primaryComplaints = primaryComplaints;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(NAME_PARAMETER, getName());
        userJsonObj.put(ADDITIONAL_NAME_PARAMETER, additionalName);
        userJsonObj.put(SURNAME_PARAMETER, surname);
        userJsonObj.put(PHONE_PARAMETER, phone);
        userJsonObj.put(EMAIL_PARAMETER, email);
        userJsonObj.put(BITHDAY_PARAMETER, birthday.format(FORMATTER));
        userJsonObj.put(ADMISSION_DATE_PARAMETER, admissionDate.format(FORMATTER));
        userJsonObj.put(DISCHARGE_DATE_PARAMETER, (dischargeDate) == null ? "" : dischargeDate.format(FORMATTER));
        userJsonObj.put(PRIMARY_DIAGNOSIS_ID_PARAMETER, (primaryDiagnosis == null) ? null : primaryDiagnosis.getId());
        userJsonObj.put(PRIMARY_COMPLAINTS_PARAMETER, primaryComplaints);
        userJsonObj.put(PRIMARY_INSPECTION_PARAMETER, primaryInspection);
        userJsonObj.put(FINAL_DIAGNOSIS_ID_PARAMETER, (finalDiagnosis == null) ? null : finalDiagnosis.getId());

        return userJsonObj.toString();
    }
}
