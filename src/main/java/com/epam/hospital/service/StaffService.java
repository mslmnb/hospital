package com.epam.hospital.service;

import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface StaffService extends HavingDeleteMethod, HavingGetMethod {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";
    String ADDITIONAL_NAME_PARAMETER = "additionalName";
    String SURNAME_PARAMETER = "surname";
    String POSITION_ID_PARAMETER = "positionId";
    String POSITION_PARAMETER = "position";

    void save(String idAsString, String name, String additionalName,
              String surname, String positionIdAsString) throws AppException;

    void save(Staff staff) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    Staff get(String idAsString) throws AppException;

    Staff get(int id) throws AppException;

    List<Staff> getAll(String lang);
}
