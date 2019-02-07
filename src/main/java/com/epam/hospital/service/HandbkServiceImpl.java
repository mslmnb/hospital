package com.epam.hospital.service;

import com.epam.hospital.dao.HandBkDAO;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class HandbkServiceImpl implements HandbkService{
    private final HandBkDAO dao;

    public HandbkServiceImpl(HandBkDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String name, HandbkType handbkType) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = idAsString.isEmpty() ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(NAME_PARAMETER, name);
        checkNotEmpty(parameters, checkResult, true);
        Handbk handbk = new Handbk(id, name, handbkType);
        save(handbk);
    }

    @Override
    public void save(Handbk handbk) throws AppException {
        if (handbk.isNew()) {
            dao.create(handbk);
        } else {
            checkNotFound(dao.update(handbk));
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
    public Handbk get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public Handbk get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<Handbk> getAll(HandbkType handbkType) {
        return dao.getAll(handbkType);
    }

    @Override
    public List<Handbk> getAllTranslations(String lang, HandbkType handbkType) {
        return dao.getAllTranslations(lang, handbkType);
    }

}
