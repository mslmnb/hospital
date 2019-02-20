package com.epam.hospital.service;

import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface HandbkItemService extends HavingDeleteMethod, HavingGetMethod, HavingSaveMethod {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";
    String TYPE_PARAMETER = "type";

    void save(String idAsString, String name, String typeAsString) throws AppException;

    void save(HandbkItem handbkItem) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    HandbkItem get(String idAsString) throws AppException;

    HandbkItem get(int id) throws AppException;

    List<HandbkItem> getAll(HandbkType handbkType);

    List<HandbkItem> getAllTranslations(String lang, HandbkType handbkType);
}
