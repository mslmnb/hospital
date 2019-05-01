package com.epam.hospital.service;

import com.epam.hospital.model.Staff;
import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.SqlAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.util.List;

public interface StaffService extends CommonServiceOperationForBaseEntity<Staff> {

    /**
     * Creates or updates record about staff person into table by specified data
     * @param idAsString the key of staff person as string
     * @param name the name of staff person
     * @param additionalName the additional name of staff person
     * @param surname the surname of staff person
     * @param positionIdAsString the position's key from handbook as string
     * @throws UnvalidDataAppException if method's parameters contains unvalid data
     * @throws NotFoundAppException if record which should be updated is not found
     * @throws SqlAppException if a database access error occurs
     */
    void save(String idAsString, String name, String additionalName, String surname,
              String positionIdAsString) throws UnvalidDataAppException, NotFoundAppException, SqlAppException;

    /**
     * Gets all staff
     * @param locale the language for translate position of staff person
     * @return the list of {@code Staff} entities
     *     if records are not found that returns empty list
     * @throws SqlAppException if a database access error occurs
     */
    List<Staff> getAll(String locale)throws SqlAppException;
}
