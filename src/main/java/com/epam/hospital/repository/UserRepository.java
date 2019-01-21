package com.epam.hospital.repository;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int staff_id);

    // null if not found
    User get(int staff_id);

    // null if not found
    //User getByLogin(String login);

    // null if not found
    User getByLoginWithoutRoles(String login);

    // empty if not found
    Set<Role> getRoles(int staff_id);

    // empty if not found
    List<User> getAllWithoutRoles();

    boolean connectionPoolIsNull();

}
