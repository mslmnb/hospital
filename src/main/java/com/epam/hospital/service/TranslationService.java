package com.epam.hospital.service;

import com.epam.hospital.model.handbk.Translation;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface TranslationService extends HavingDeleteMethod, HavingGetMethod {
    String ID_PARAMETER = "id";
    String HANDBK_ITEM_ID_PARAMETER = "handbkItemId";
    String LOCALE_PARAMETER = "locale";
    String TRANSLATION_PARAMETER = "translation";

    void save(String idAsString, String handbkItemIdAsString, String locale, String translation) throws AppException;

    void save(Translation translation) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    Translation get(String idAsString) throws AppException;

    Translation get(int id) throws AppException;

    List<Translation> getAll(String handbkItemIdAsString);

    List<Translation> getAll(int handbkItemId);

}
