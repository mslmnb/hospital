package com.epam.hospital.service;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    User save(User user);

    void delete(int staff_id) throws NotFoundException;

    User get(int staff_id) throws NotFoundException;

    void update(User user);

    // null if not found
    User getByLogin(String login);

    List<User> getAllWithoutRoles();

}
