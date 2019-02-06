package com.epam.hospital.service;

import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface HandbkService {

    Handbk create(Handbk handbk) throws AppException;

    void update(Handbk handbk) throws AppException;

    void delete(int id) throws AppException;

    Handbk get(int id) throws AppException;

    List<Handbk> getAll(String lang, HandbkType handbkType);


}
