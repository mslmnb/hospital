package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int staff_id) throws NotFoundException;

    User get(int staff_id) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    boolean connectionPoolIsNull();

    User getByLogin(String login); //throws NotFoundException;
}
