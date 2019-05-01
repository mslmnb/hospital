package com.epam.hospital.dao;

import com.epam.hospital.model.User;
import java.util.List;

/**
 * The interface of crud operation for {@code User} entity
 */

public interface UserDAO extends CommonCrudOperations<User>, CommonCrudOperationsForBaseEntity<User> {

    /**
     * Gets the user with specified login
     * @param login the login
     * @return the user with specified login
     *   if record are not found that returns null
     */
    User getByLogin(String login);
}
