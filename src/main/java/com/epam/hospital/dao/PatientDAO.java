package com.epam.hospital.dao;

import com.epam.hospital.model.Patient;

import java.util.List;

public interface PatientDAO {

    Patient create(Patient patient);

    Patient update(Patient patient);

    Patient updatePrimaryExamAndDischarge(Patient patient);

    // false if not found
    boolean delete(int id);

    // null if not found
    Patient get(int id);

    List<Patient> getAll();

}
