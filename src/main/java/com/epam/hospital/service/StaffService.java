package com.epam.hospital.service;

import com.epam.hospital.model.Staff;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface StaffService extends HavingDeleteMethod, HavingGetMethod, HavingSaveMethod {

    void save(String idAsString, String name, String additionalName,
              String surname, String positionIdAsString) throws AppException;

    void save(Staff staff) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    Staff get(String idAsString) throws AppException;

    Staff get(int id) throws AppException;

    List<Staff> getAll(String locale);
}
