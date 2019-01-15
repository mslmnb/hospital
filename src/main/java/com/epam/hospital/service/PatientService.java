package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

public interface PatientService {

    Patient save(Patient patient);

    void delete(int id) throws NotFoundException;

    Patient get(int id) throws NotFoundException;

    List<Patient> getAll();

    void update(Patient patient);

    boolean connectionPoolIsNull();

}
