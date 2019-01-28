package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.dao.UserDAO;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkNotFoundWithId;

public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public void delete(int staff_id) throws NotFoundException {
        checkNotFoundWithId(dao.delete(staff_id), staff_id);
    }

    @Override
    public User get(int staff_id) throws NotFoundException {
        return checkNotFoundWithId(dao.get(staff_id), staff_id);
    }

    @Override
    public void update(User patient) {
        dao.save(patient);
    }

    @Override
    public User getByLogin(String login) {
        return dao.getByLogin(login);
    }

    @Override
    public List<User> getAllWithoutRoles() {
        return dao.getAllWithoutRoles();
    }
}
