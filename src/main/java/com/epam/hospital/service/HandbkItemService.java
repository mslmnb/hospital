package com.epam.hospital.service;

import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.exception.AppException;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

/**
 * The interface of service operations for {@code HandbkItem} entities.
 */

public interface HandbkItemService extends CommonServiceOperationForBaseEntity<HandbkItem> {

    /**
     * Creates or updates record into table by specified data
     * @param idAsString the entity's key as string
     * @param name the name of handbook's item
     * @param typeAsString the type of handbook as string
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String name,
              String typeAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all {@code HandbkItem} entities
     * @param type the type of handbook
     * @return the list of {@code HandbkItem} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<HandbkItem> getAll(HandbkType type) throws SqlAppException;

    /**
     * Gets all translations for specified language and specified type of handbook
     * @param lang the language
     * @param type the type of handbook
     * @return the list of {@code HandbkItem} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<HandbkItem> getAllTranslations(String lang, HandbkType type) throws SqlAppException;
}
