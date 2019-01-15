package com.epam.hospital.web.patient;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.util.PatientUtil;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.util.ValidationUtil.checkIdConsistent;
import static com.epam.hospital.util.ValidationUtil.checkNew;

public class PatientControllerImpl implements PatientController{
    private final PatientService service;

    public PatientControllerImpl(PatientService service) {
        this.service = service;
    }

    @Override
    public Patient create(Patient patient) {
        checkNew(patient);
        return service.save(patient);
    }

    @Override
    public void update(Patient patient, int id) {
        checkIdConsistent(patient, id);
        service.update(patient);
    }

    @Override
    public void delete(int id) {
        service.delete(id);
    }

    @Override
    public Patient get(int id) {
        return service.get(id);
    }

    @Override
    public List<Patient> getAll() {
        return service.getAll();
    }

    @Override
    public List<PatientTo> getAllTo() {
        return service.getAll().stream()
                               .map(patient -> PatientUtil.createTo(patient))
                               .collect(Collectors.toList());
    }

    @Override
    public boolean connectionPoolIsNull() {
        return service.connectionPoolIsNull();
    }
}
