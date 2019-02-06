package com.epam.hospital.dao;

import com.epam.hospital.model.User;
import java.util.List;

public interface UserDAO {
    User create(User user);

    // null if not found
    User updatePassword(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByLogin(String login);

    // empty if not found
    List<User> getAll();
}
