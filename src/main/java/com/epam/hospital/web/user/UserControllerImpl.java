package com.epam.hospital.web.user;

import com.epam.hospital.model.User;
import com.epam.hospital.service.UserService;

import java.util.List;

public class UserControllerImpl implements UserController {
    private final UserService service;

    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @Override
    public User create(User user) {
        //checkNew(user);  ?????
        return service.save(user);
    }

    @Override
    public void update(User user, int id) {
        //checkIdConsistent(staff_id, id);  ?????
        service.update(user);

    }

    @Override
    public void delete(int staff_id) {
        service.delete(staff_id);
    }

    @Override
    public User get(int staff_id) {
        return service.get(staff_id);
    }

    @Override
    public List<User> getAll() {
        return service.getAll();
    }

    @Override
    public boolean connectionPoolIsNull() {
        return service.connectionPoolIsNull();
    }

    @Override
    public User getByLogin(String login) {
        return service.getByLogin(login);
    }
}
