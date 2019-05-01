package com.epam.hospital.service;

import com.epam.hospital.dao.HandbkItemDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.CheckResult;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

/**
 * The class of service operation for {@code HandbkItem} entity
 */
public class HandbkItemServiceImpl implements HandbkItemService {
    public static final String NAME_PARAMETER = "name";
    public static final String TYPE_PARAMETER = "type";

    private final HandbkItemDAO dao;

    public HandbkItemServiceImpl(HandbkItemDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String name, String typeAsString){
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        checkNotEmpty(name, NAME_PARAMETER, checkResult);
        HandbkType type = HandbkType.valueOf(typeAsString.toUpperCase());
        HandbkItem handbkItem = new HandbkItem(id, name, type);
        save(handbkItem);
    }

    @Override
    public void save(HandbkItem handbkItem){
        if (handbkItem.isNew()) {
            dao.create(handbkItem);
        } else {
            checkNotFound(dao.update(handbkItem));
        }
    }

    @Override
    public void save(String... args){
        save(args[0], args[1], args[2]);
    }

    @Override
    public void delete(String idAsString){
        delete(checkAndReturnInt(idAsString, ID_PARAMETER));
    }

    @Override
    public void delete(int id){
        checkNotFound(dao.delete(id));
    }

    @Override
    public HandbkItem get(String idAsString){
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public HandbkItem get(int id){
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<HandbkItem> getAll(HandbkType type) {
        return dao.getAll(type);
    }

    @Override
    public List<HandbkItem> getAllTranslations(String lang, HandbkType type) {
        return dao.getAllTranslations(lang, type);
    }

}
