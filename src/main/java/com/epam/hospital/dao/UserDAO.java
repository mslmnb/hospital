package com.epam.hospital.dao;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {
    User save(User user);

    // false if not found
    boolean delete(int staff_id);

    // null if not found
    User get(int staff_id);

    // null if not found
    User getByLogin(String login);

    // empty if not found
    List<User> getAllWithoutRoles();

}
