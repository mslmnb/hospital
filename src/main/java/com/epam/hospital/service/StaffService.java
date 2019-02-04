package com.epam.hospital.service;

import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface StaffService {

    User create(Staff staff) throws AppException;

    void update(Staff staff) throws AppException;

    void delete(int id) throws AppException;

    Staff get(int id) throws AppException;

    List<Staff> getAll();


}
