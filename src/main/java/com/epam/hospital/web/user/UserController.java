package com.epam.hospital.web.user;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import java.util.List;
import java.util.Set;

public interface UserController {

    User create(User user);

    void update(User user, int staff_id);

    void delete(int staff_id);

    User get(int staff_id);

    User getByLoginWithOutRoles(String login);

    Set<Role> getRoles(int staff_id);

    List<User> getAllWithoutRoles();

    boolean connectionPoolIsNull();

}
