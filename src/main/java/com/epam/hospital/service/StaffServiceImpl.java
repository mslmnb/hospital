package com.epam.hospital.service;

import com.epam.hospital.dao.StaffDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public class StaffServiceImpl implements StaffService{
    private final StaffDAO dao;

    public StaffServiceImpl(StaffDAO dao) {
        this.dao = dao;
    }

    @Override
    public User create(Staff staff) throws AppException {
        return null;
    }

    @Override
    public void update(Staff staff) throws AppException {

    }

    @Override
    public void delete(int id) throws AppException {

    }

    @Override
    public Staff get(int id) throws AppException {
        return null;
    }


    @Override
    public List<Staff> getAll() {
        return dao.getAll();
    }
}
