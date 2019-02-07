package com.epam.hospital.service;

import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface HandbkService {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";

    void save(String idAsString, String name, HandbkType handbkType) throws AppException;

    void save(Handbk handbk) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    Handbk get(String idAsString) throws AppException;

    Handbk get(int id) throws AppException;

    List<Handbk> getAll(HandbkType handbkType);

    List<Handbk> getAllTranslations(String lang, HandbkType handbkType);

}
