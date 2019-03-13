package com.epam.hospital.service;

import com.epam.hospital.dao.TranslationDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.Translation;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class TranslationServiceImpl implements TranslationService{
    public final static String ID_PARAMETER = "id";
    public final static String HANDBK_ITEM_ID_PARAMETER = "handbkItemId";
    public final static String LOCALE_PARAMETER = "locale";
    public final static String TRANSLATION_PARAMETER = "translation";

    private final TranslationDAO dao;

    public TranslationServiceImpl(TranslationDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String handbkItemIdAsString,
                     String locale, String translation) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer handbkItemId = checkAndReturnInt(handbkItemIdAsString, HANDBK_ITEM_ID_PARAMETER, checkResult, false);
        checkNotEmpty(locale, LOCALE_PARAMETER, checkResult, false);
        checkNotEmpty(translation, TRANSLATION_PARAMETER, checkResult);
        Translation translation1 = new Translation(id, new HandbkItem(handbkItemId), locale, translation);
        save(translation1);
    }

    @Override
    public void save(Translation translation) throws AppException {
        if (translation.isNew()) {
            dao.create(translation);
        } else {
            checkNotFound(dao.update(translation));
        }
    }

    @Override
    public void save(String... args) throws AppException {
        save(args[0], args[1], args[2], args[3]);
    }

    @Override
    public void delete(String idAsString) throws AppException {
        delete(checkAndReturnInt(idAsString, ID_PARAMETER));
    }

    @Override
    public void delete(int id) throws AppException {
        checkNotFound(dao.delete(id));
    }

    @Override
    public Translation get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public Translation get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<Translation> getAll(String handbkItemIdAsString) {
        return getAll(checkAndReturnInt(handbkItemIdAsString, HANDBK_ITEM_ID_PARAMETER));
    }

    @Override
    public List<Translation> getAll(int handbkItemId) {
        return dao.getAll(handbkItemId);
    }
}
