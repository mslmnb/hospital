package com.epam.hospital.service;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.dao.UserDAO;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

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
        checkNotEmpty(login, LOGIN_PARAMETER, checkResult, false);
        checkNotEmpty(roleAsString, ROLE_PARAMETER, checkResult, false);
        checkNotEmpty(password, PASSWORD_PARAMETER, checkResult);
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
