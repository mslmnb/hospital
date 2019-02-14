package com.epam.hospital.model;

import com.epam.hospital.model.handbk.Diagnosis;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.epam.hospital.service.PatientService.*;

public class Patient extends NamedEntity implements HavingJsonView{
    private String additionalName;
    private String surname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private Diagnosis primaryDiagnosis;
    private Diagnosis finalDiagnosis;

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

    public void setFinalDiagnosis(Diagnosis finalDiagnosis) {
        this.finalDiagnosis = finalDiagnosis;
    }

    public Diagnosis getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    public void setPrimaryDiagnosis(Diagnosis primaryDiagnosis) {
        this.primaryDiagnosis = primaryDiagnosis;
    }

    @Override
    public String getJsonString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(NAME_PARAMETER, getName());
        userJsonObj.put(ADDITIONAL_NAME_PARAMETER, additionalName);
        userJsonObj.put(SURNAME_PARAMETER, surname);
        userJsonObj.put(PHONE_PARAMETER, phone);
        userJsonObj.put(EMAIL_PARAMETER, email);
        userJsonObj.put(BITHDAY_PARAMETER, birthday.format(formatter));
        userJsonObj.put(ADMISSION_DATE_PARAMETER, admissionDate.format(formatter));
        userJsonObj.put(DISCHARGE_DATE_PARAMETER, dischargeDate == null ? "" : dischargeDate.format(formatter));
        userJsonObj.put(PRIMARY_DIAGNOSIS_ID_PARAMETER, primaryDiagnosis.getId());
        userJsonObj.put(FINAL_DIAGNOSIS_ID_PARAMETER, finalDiagnosis.getId());

        return userJsonObj.toString();
    }
}
