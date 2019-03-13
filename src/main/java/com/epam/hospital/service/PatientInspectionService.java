package com.epam.hospital.service;

import com.epam.hospital.model.PatientInspection;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface PatientInspectionService extends HavingGetMethod, HavingDeleteMethod, HavingSaveMethod {

    void save(String idAsString, String patientIdAsString, String dateAsString,
              String inspection, String complaints) throws AppException;

    void save(PatientInspection inspection) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    PatientInspection get(String idAsString) throws AppException;

    PatientInspection get(int id) throws AppException;

    List<PatientInspection> getAll(String patientIdAsString);

    List<PatientInspection> getAll(int patientId);
}