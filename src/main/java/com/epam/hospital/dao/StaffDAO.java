package com.epam.hospital.dao;

import com.epam.hospital.model.Staff;

import java.util.List;

public interface StaffDAO {
    Staff create(Staff staff);

    Staff update(Staff staff);

    // false if not found
    boolean delete(int id);

    // null if not found
    Staff get(int id);

    // empty if not found
    List<Staff> getAll(String locale);
}
