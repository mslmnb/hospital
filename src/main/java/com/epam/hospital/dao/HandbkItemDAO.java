package com.epam.hospital.dao;

import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;

import java.util.List;

/**
 * The interface of crud operation for {@code HandbkItem} entity
 */

public interface HandbkItemDAO extends CommonCrudOperationsForBaseEntity<HandbkItem> {

    /**
     * Gets all entities for specified type of handbook
     * @param type the type of handbook
     * @return the list of entities for specified type of handbook
     *   if records are not found that returns empty list
     */
    List<HandbkItem> getAll(HandbkType type);

    /**
     * Gets all translations for specified language and specified type of handbook
     * @param locale the language
     * @param type the type of handbook
     * @return the list of handbook's items for specified language and specified type of handbook
     *   if records are not found that returns empty list
     */
    List<HandbkItem> getAllTranslations(String locale, HandbkType type);

}
