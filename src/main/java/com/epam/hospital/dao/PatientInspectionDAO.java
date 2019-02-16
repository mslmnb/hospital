package com.epam.hospital.dao;

import com.epam.hospital.model.PatientInspection;

import java.util.List;

public interface PatientInspectionDAO {
    PatientInspection create(PatientInspection patientInspection);

    PatientInspection update(PatientInspection patientInspection);

    // false if not found
    boolean delete(int id);

    // null if not found
    PatientInspection get(int id);

    List<PatientInspection> getAll(int patientId);
}
