package com.epam.hospital.web.user;

import com.epam.hospital.model.User;

import java.util.List;

public interface UserController {

    User create(User user);

    void update(User user, int staff_id);

    void delete(int staff_id);

    User get(int staff_id);

    List<User> getAll();

    boolean connectionPoolIsNull();

    User getByLogin(String login);
}
