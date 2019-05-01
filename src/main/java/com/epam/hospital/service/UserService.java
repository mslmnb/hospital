package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.AppException;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

public interface UserService extends CommonServiceOperation<User>, CommonServiceOperationForBaseEntity<User>{

    /**
     * Creates or updates record about user of system into table by specified data
     * @param idAsString the user's key as string
     * @param staffIdAsString the key of staff person as string
     * @param login the user's login
     * @param password  the user's password
     * @param roleAsString the user's role
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String staffIdAsString, String login, String password,
              String roleAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets user of system by specified login
     * @param login the user's login
     * @return {@code User} entity or null if the user is not found
     * @throws SqlAppException if a database access error occurs
     */
    User getByLogin(String login)throws SqlAppException;

}
