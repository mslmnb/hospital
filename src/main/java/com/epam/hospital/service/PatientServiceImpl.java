package com.epam.hospital.service;

import com.epam.hospital.model.Patient;
import com.epam.hospital.dao.PatientDAO;
import com.epam.hospital.model.User;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.*;

public class PatientServiceImpl implements PatientService {

    private final PatientDAO dao;

    public PatientServiceImpl(PatientDAO dao) {
        this.dao = dao;
    }


    @Override
    public void save(String idAsString, String name, String additionalName, String surname,
                     String birthdayAsString, String phone, String email) throws AppException {
        Integer id = idAsString.isEmpty() ? null : checkAndReturnInt(idAsString, ID_PARAMETER);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(NAME_PARAMETER,name);
        parameters.put(SURNAME_PARAMETER, surname);
        CheckResult checkResult = new CheckResult();
        checkNotEmpty(parameters, checkResult, false);
        LocalDate birthday = checkAndReturnDate(birthdayAsString, BITHDAY_PARAMETER, checkResult, false);
        checkPhone(phone, checkResult, false);
        checkEmail(email, checkResult, true);
        Patient patient = new Patient(id, name, additionalName, surname,
                birthday, phone, email);
        save(patient);
    }

    @Override
    public void save(Patient patient) throws AppException {
        if (patient.isNew()) {
            dao.create(patient);
        } else {
            checkNotFound(dao.update(patient));
        }
    }


    @Override
    public void delete(String idAsString) throws AppException {

    }

    @Override
    public void delete(int id) throws AppException {
//        checkNotFoundWithId(dao.delete(id), id);
    }

    @Override
    public User get(String idAsString) throws AppException {
        return null;
    }

    @Override
    public Patient get(int id) throws AppException {
  //      return checkNotFoundWithId(dao.get(id), id);
        return null;
    }

    @Override
    public List<Patient> getAll() {
        return dao.getAll();
    }
}
