package com.epam.hospital.service;

import java.util.List;

/**
 * The interface of common service operations with DAO.
 * @param <T> the runtime type of class
 */

public interface CommonServiceOperation<T> {

    /**
     * Gets all entities
     * @return the list of entities
     *    if records are not found that returns empty list
     */
    List<T> getAll();
}
