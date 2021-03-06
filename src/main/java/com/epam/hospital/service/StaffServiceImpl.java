package com.epam.hospital.service;

import com.epam.hospital.dao.StaffDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Position;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

/**
 * The class of service operation for {@code Staff} entity
 */

public class StaffServiceImpl implements StaffService {
    public static final String NAME_PARAMETER = "name";
    public static final String ADDITIONAL_NAME_PARAMETER = "additionalName";
    public static final String SURNAME_PARAMETER = "surname";
    public static final String POSITION_ID_PARAMETER = "positionId";
    public static final String POSITION_PARAMETER = "position";

    private final StaffDAO dao;

    public StaffServiceImpl(StaffDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String name, String additionalName,
                     String surname, String positionIdAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        checkNotEmpty(name, NAME_PARAMETER, checkResult, false);
        checkNotEmpty(surname, SURNAME_PARAMETER, checkResult, false);
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
    public void save(String... args) throws AppException {
        save(args[0], args[1], args[2], args[3], args[4]);
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
    public List<Staff> getAll(String locale) {
        return dao.getAll(locale);
    }
}
