package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkNotFoundWithId;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Patient get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Patient> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(Patient patient) {
        repository.save(patient);
    }
}
