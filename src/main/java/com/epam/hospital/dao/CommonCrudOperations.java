package com.epam.hospital.dao;

import com.epam.hospital.util.exception.SqlAppException;

import java.util.List;

/**
 * The interface of common crud operations with DAO.
 * @param <T> the runtime type of class
 */

public interface CommonCrudOperations<T> {

    /**
     * Gets all entities
     * @return the list of entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<T> getAll() throws SqlAppException;
}
