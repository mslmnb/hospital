package com.epam.hospital.service;

import com.epam.hospital.dao.HandBkDAO;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;
import com.sun.org.apache.bcel.internal.generic.LADD;

import java.util.List;

public class HandbkServiceImpl implements HandbkService{
    private final HandBkDAO dao;

    public HandbkServiceImpl(HandBkDAO dao) {
        this.dao = dao;
    }

    @Override
    public Handbk create(Handbk handbk) throws AppException {
        return null;
    }

    @Override
    public void update(Handbk handbk) throws AppException {

    }

    @Override
    public void delete(int id) throws AppException {

    }

    @Override
    public Handbk get(int id) throws AppException {
        return null;
    }

    @Override
    public List<Handbk> getAll(String lang, HandbkType handbkType) {
        return dao.getAll(lang, handbkType);
    }
}
