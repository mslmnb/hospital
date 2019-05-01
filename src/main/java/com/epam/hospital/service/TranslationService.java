package com.epam.hospital.service;

import com.epam.hospital.model.Translation;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

public interface TranslationService extends CommonServiceOperationForBaseEntity<Translation> {

    /**
     * Creates or updates translation of handbook item into table by specified data
     * @param idAsString the key of translation as string
     * @param handbkItemIdAsString the key of handbook item as string
     * @param locale the language
     * @param translation the translation
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String handbkItemIdAsString, String locale,
              String translation) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all translations for specified handbook item
     * @param handbkItemIdAsString the key of handbook item as string
     * @return the list of {@code Translation} entities
     *     if records are not found that returns empty list
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws SqlAppException if a database access error occurs
     */
    List<Translation> getAll(String handbkItemIdAsString)throws UnvalidDataAppException, SqlAppException;

    /**
     * Gets all translations for specified handbook item
     * @param handbkItemId the key of handbook item
     * @return the list of {@code Translation} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<Translation> getAll(int handbkItemId) throws SqlAppException;

}
