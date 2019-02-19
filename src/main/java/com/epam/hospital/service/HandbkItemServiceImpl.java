package com.epam.hospital.service;

import com.epam.hospital.dao.HandbkItemDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class HandbkItemServiceImpl implements HandbkItemService {
    private final HandbkItemDAO dao;

    public HandbkItemServiceImpl(HandbkItemDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String name, String typeAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        checkNotEmpty(name, NAME_PARAMETER, checkResult);
        HandbkType type = HandbkType.valueOf(typeAsString.toUpperCase());
        HandbkItem handbkItem = new HandbkItem(id, name, type);
        save(handbkItem);
    }

    @Override
    public void save(HandbkItem handbkItem) throws AppException {
        if (handbkItem.isNew()) {
            dao.create(handbkItem);
        } else {
            checkNotFound(dao.update(handbkItem));
        }
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
    public HandbkItem get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public HandbkItem get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<HandbkItem> getAll(HandbkType handbkType) {
        return dao.getAll(handbkType);
    }

    @Override
    public List<HandbkItem> getAllTranslations(String lang, HandbkType handbkType) {
        return dao.getAllTranslations(lang, handbkType);
    }

}
