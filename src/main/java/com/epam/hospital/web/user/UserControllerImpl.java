package com.epam.hospital.web.user;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.UserService;

import java.util.List;
import java.util.Set;

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
    public User getByLoginWithOutRoles(String login) {
        return service.getByLoginWithOutRoles(login);
    }

    @Override
    public Set<Role> getRoles(int staff_id) {
        return service.getRoles(staff_id);
    }

    @Override
    public List<User> getAllWithoutRoles() {
        return service.getAllWithoutRoles();
    }

    @Override
    public boolean connectionPoolIsNull() {
        return service.connectionPoolIsNull();
    }

}
