package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.dao.PatientDAO;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkNew;
import static com.epam.hospital.util.ValidationUtil.checkNotFoundWithId;

public class PatientServiceImpl implements PatientService {

    private final PatientDAO dao;

    public PatientServiceImpl(PatientDAO dao) {
        this.dao = dao;
    }

    @Override
    public Patient create(Patient patient) {
        checkNew(patient);
        return save(patient);
    }

    @Override
    public Patient save(Patient patient) {
        return dao.save(patient);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(dao.delete(id), id);
    }

    @Override
    public Patient get(int id) throws NotFoundException {
        return checkNotFoundWithId(dao.get(id), id);
    }

    @Override
    public List<Patient> getAll() {
        return dao.getAll();
    }

    @Override
    public void update(Patient patient) {
        dao.save(patient);
    }

}
