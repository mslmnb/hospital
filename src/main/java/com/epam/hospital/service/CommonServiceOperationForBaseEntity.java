package com.epam.hospital.service;

import com.epam.hospital.model.BaseEntity;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

/**
 * The interface of common service operations for entities having key.
 * @param <T> the runtime type of class
 */

public interface CommonServiceOperationForBaseEntity<T extends BaseEntity> {
    String ID_PARAMETER = "id";

    /**
     * Creates or updates record into table by specified data
     * @param args the data for creation or updating of record
     * @throws UnvalidDataAppException if {@code args} contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String ... args)  throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Creates or updates record into table for specified entity
     * @param entity the entity which should be updated or created
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(T entity) throws NotFoundAppException, SqlAppException;

    /**
     * Gets the entity by specified key
     * @param idAsString the entity's key as string
     * @return the entity
     * @throws UnvalidDataAppException if {@code idAsString} cannot be transformed to an integer number
     * @throws NotFoundAppException if record is not found
     * @throws SqlAppException if a database access error occurs
     */
    T get(String idAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets the entity by specified key
     * @param id the entity's key
     * @return the entity
     * @throws NotFoundAppException if record is not found
     * @throws SqlAppException if a database access error occurs
     */
    T get(int id) throws NotFoundAppException, SqlAppException;

    /**
     * Deletes the record by specified key
     * @param idAsString the entity's key as string
     * @throws UnvalidDataAppException if {@code idAsString} cannot be transformed to an integer number
     * @throws NotFoundAppException if record is not found
     * @throws SqlAppException if a database access error occurs
     */
    void delete(String idAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Deletes the record by specified key
     * @param id the entity's key
     * @throws NotFoundAppException if record is not found
     * @throws SqlAppException if a database access error occurs
     */
    void delete(int id) throws  NotFoundAppException, SqlAppException;
}
