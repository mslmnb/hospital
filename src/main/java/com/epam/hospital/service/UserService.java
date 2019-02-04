package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;

import java.util.List;

public interface UserService {
    String ID_PARAMETER = "id";
    String STAFF_ID_PARAMETER = "staffId";
    String LOGIN_PARAMETER = "login";
    String ROLE_PARAMETER = "role";
    String PASSWORD_PARAMETER = "password";


    void save(String idAsString, String staffIdAsString, String login,
              String password, String roleAsString) throws AppException;

    void save(User user) throws AppException;

    void delete(String idAsString) throws AppException;

    void delete(int id) throws AppException;

    User get(String idAsString) throws AppException;

    // null if not found
    User getByLogin(String login);

    List<User> getAll();

}
