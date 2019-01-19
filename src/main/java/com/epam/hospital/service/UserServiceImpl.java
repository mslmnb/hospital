package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.repository.UserRepository;
import com.epam.hospital.util.exception.NotFoundException;

import java.util.List;

import static com.epam.hospital.util.ValidationUtil.checkNotFoundWithId;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(User patient) {
        repository.save(patient);
    }

    @Override
    public boolean connectionPoolIsNull() {
        return repository.connectionPoolIsNull();
    }

    @Override
    public User getByLogin(String login) { //throws NotFoundException {
        //return checkNotFound(repository.getByLogin(login), "login=" + login);
        return repository.getByLogin(login);
    }

}
