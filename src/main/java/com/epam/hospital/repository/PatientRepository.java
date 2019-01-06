package com.epam.hospital.repository;

import com.epam.hospital.model.Patient;

import java.util.List;

public interface PatientRepository {
    Patient save(Patient user);

    // false if not found
    boolean delete(int id);

    // null if not found
    Patient get(int id);

    List<Patient> getAll();

}
