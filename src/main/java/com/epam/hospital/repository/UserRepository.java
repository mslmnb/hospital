package com.epam.hospital.repository;

import com.epam.hospital.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int staff_id);

    // null if not found
    User get(int staff_id);

    // null if not found
    User getByLogin(String login);

    List<User> getAll();

    boolean connectionPoolIsNull();

}
