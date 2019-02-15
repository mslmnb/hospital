package com.epam.hospital.service;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.dao.UserDAO;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(String idAsString, String staffIdAsString,
                     String login, String password, String roleAsString) throws AppException {
        CheckResult checkResult = new CheckResult();
        Integer id = (idAsString.isEmpty()) ? null : checkAndReturnInt(idAsString, ID_PARAMETER, checkResult, false);
        Integer staffId = checkAndReturnInt(staffIdAsString, STAFF_ID_PARAMETER, checkResult, false);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(LOGIN_PARAMETER, login);
        parameters.put(ROLE_PARAMETER, roleAsString);
        parameters.put(PASSWORD_PARAMETER, password);
        checkNotEmpty(parameters, checkResult, true);
        Staff staff = new Staff(staffId);
        User user = new User(id, staff, login, DigestUtils.md5Hex(password), Role.valueOf(roleAsString));
        save(user);
    }

    @Override
    public void save(User user) throws AppException {
        if (user.isNew()) {
            dao.create(user);
        } else {
            checkNotFound(dao.updatePassword(user));
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
    public User get(String idAsString) throws AppException {
        Integer id = checkAndReturnInt(idAsString, ID_PARAMETER);
        return get(id);
    }

    @Override
    public User get(int id) throws AppException {
        return checkNotFound(dao.get(id));
    }

    @Override
    public User getByLogin(String login) {
        return dao.getByLogin(login);
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }
}
