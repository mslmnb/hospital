package com.epam.hospital.dao;

import com.epam.hospital.model.Translation;

import java.util.List;

/**
 * The interface of crud operation for {@code Translation} entity
 */

public interface TranslationDAO extends CommonCrudOperationsForBaseEntity<Translation> {

    /**
     * Gets all translation for specified handbook's item
     * @param handbkItemId the key of handbook's item
     * @return the list of translation for specified handbook's item
     *   if records are not found that returns empty list
     */
    List<Translation> getAll(Integer handbkItemId);
}
