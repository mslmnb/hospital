package com.epam.hospital.web.patient;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.to.PatientTo;
import java.util.List;

public interface PatientController {

    public Patient create(Patient patient);

    public void update(Patient patient, int id);

    public void delete(int id);

    public Patient get(int id);

    public List<Patient> getAll();

    public List<PatientTo> getAllTo();

    boolean connectionPoolIsNull();

}
