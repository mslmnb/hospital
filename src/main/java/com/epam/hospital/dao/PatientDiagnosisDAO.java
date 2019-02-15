package com.epam.hospital.dao;

import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.handbk.Diagnosis;

import java.util.List;

public interface PatientDiagnosisDAO {
    PatientDiagnosis create(PatientDiagnosis patientDiagnosis);

    PatientDiagnosis update(PatientDiagnosis patientDiagnosis);

    // false if not found
    boolean delete(int id);

    // null if not found
    PatientDiagnosis get(int id);

    List<PatientDiagnosis> getAll(int patientId, String locale);
}
