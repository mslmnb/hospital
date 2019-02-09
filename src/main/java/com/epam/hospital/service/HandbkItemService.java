package com.epam.hospital.service;

import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface HandbkItemService {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";

    void save(String idAsString, String name, HandbkType handbkType) throws AppException;

    void save(HandbkItem handbkItem) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    HandbkItem get(String idAsString) throws AppException;

    HandbkItem get(int id) throws AppException;

    List<HandbkItem> getAll(HandbkType handbkType);

    List<HandbkItem> getAllTranslations(String lang, HandbkType handbkType);
}
