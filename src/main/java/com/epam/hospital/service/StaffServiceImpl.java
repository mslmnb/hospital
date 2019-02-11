package com.epam.hospital.service;

import com.epam.hospital.dao.StaffDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Position;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class StaffServiceImpl implements StaffService {
    private final StaffDAO dao;

    public StaffServiceImpl(StaffDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String name, String additionalName,
                     String surname, String positionIdAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = idAsString.isEmpty() ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(NAME_PARAMETER, name);
        parameters.put(SURNAME_PARAMETER, surname);
        checkNotEmpty(parameters, checkResult, false);
        Integer positionId = checkAndReturnInt(positionIdAsString, POSITION_ID_PARAMETER, checkResult, true);
        Staff staff = new Staff(id, name, additionalName, surname, new Position(positionId));
        save(staff);
    }

    @Override
    public void save(Staff staff) throws AppException {
        if (staff.isNew()) {
            dao.create(staff);
        } else {
            checkNotFound(dao.update(staff));
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
    public Staff get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public Staff get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public List<Staff> getAll(String lang) {
        return dao.getAll(lang);
    }
}
