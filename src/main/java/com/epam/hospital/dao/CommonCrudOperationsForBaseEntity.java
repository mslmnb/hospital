package com.epam.hospital.dao;

import com.epam.hospital.model.BaseEntity;

/**
 * The interface of common crud operations for entities having key.
 * @param <T> the runtime type of class
 */

public interface CommonCrudOperationsForBaseEntity<T extends BaseEntity> {

    /**
     * Creates record into table for the specified entity
     * @param entity the entity
     * @return the entity with initialized key
     */
    T create(T entity);

    /**
     * Updates record into table for the specified entity
     * @param entity the entity
     * @return the entity
     *   if record for specified entity are not found that returns null
     */
    T update(T entity);

    /**
     * Deletes the entity from the table by specified key
     * @param id the key of entity
     * @return {@code true} if the entity was deleted from the table, else
     *         {@code false}
     */
    boolean delete(int id);

    /**
     * Gets the entity by specified key
     * @param id the key of entity
     * @return the entity
     *   if record for specified key are not found that returns null
     */
    T get(int id);
}
