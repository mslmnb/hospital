package com.epam.hospital.dao;

import com.epam.hospital.model.PatientPrescription;

import java.util.List;

public interface PatientPrescriptionDAO {
    PatientPrescription create(PatientPrescription patientPrescription);

    PatientPrescription update(PatientPrescription patientPrescription);

    // false if not found
    boolean delete(int id);

    // null if not found
    PatientPrescription get(int id);

    List<PatientPrescription> getAll(int patientId, String locale);
}

