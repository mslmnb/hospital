package com.epam.hospital.service;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.UserRepository;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;
import java.util.Set;

import static com.epam.hospital.util.ValidationUtil.checkNotFoundWithId;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    private static Object getInstance(Object repository) {
        return new UserServiceImpl((UserRepository) repository);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int staff_id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(staff_id), staff_id);
    }

    @Override
    public User get(int staff_id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(staff_id), staff_id);
    }

    @Override
    public void update(User patient) {
        repository.save(patient);
    }

    @Override
    public User getByLoginWithOutRoles(String login) {
        return repository.getByLoginWithoutRoles(login);
    }

    @Override
    public Set<Role> getRoles(int staff_id) {
        return repository.getRoles(staff_id);
    }

    @Override
    public List<User> getAllWithoutRoles() {
        return repository.getAllWithoutRoles();
    }

    @Override
    public boolean connectionPoolIsNull() {
        return repository.connectionPoolIsNull();
    }


}
