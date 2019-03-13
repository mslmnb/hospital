package com.epam.hospital.service;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.dao.UserDAO;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkAndReturnInt;
import static com.epam.hospital.util.ValidationUtil.checkNotEmpty;
import static com.epam.hospital.util.ValidationUtil.checkNotFound;

public class UserServiceImpl implements UserService {
    public final static String ID_PARAMETER = "id";
    public final static String NAME_PARAMETER = "name";
    public final static String STAFF_ID_PARAMETER = "staffId";
    public final static String STAFF_NAME_PARAMETER = "staffName";
    public final static String LOGIN_PARAMETER = "login";
    public final static String ROLE_PARAMETER = "role";
    public final static String PASSWORD_PARAMETER = "password";
    private final static Integer ADMIN_ID = 100000;
    private final static Integer DOCTOR_ID = 100001;
    private final static Integer NURSE_ID = 100004;

    private final UserDAO dao;
    private static final String MODIFICATION_RESTRICTION = "modificationRestriction";
    private static final List<Integer> modificationRestriction = Arrays.asList(ADMIN_ID, DOCTOR_ID, NURSE_ID);

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
            checkModificationRestriction(user.getId());
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
        checkModificationRestriction(id);
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

    private void checkModificationRestriction(int id) throws AppException {
        if (modificationRestriction.contains(id)) {
            throw new AppException(new CheckResult(MODIFICATION_RESTRICTION));
        }
    }

}

