package com.epam.hospital.dao;

import com.epam.hospital.model.Staff;

import java.util.List;

/**
 * The interface of crud operation for {@code Staff} entity
 */

public interface StaffDAO extends CommonCrudOperationsForBaseEntity<Staff>{
    /**
     * Gets all staff for specified language
     * @param locale the language
     * @return the list of staff for specified language
     *   if records are not found that returns empty list
     */
    List<Staff> getAll(String locale);
}
