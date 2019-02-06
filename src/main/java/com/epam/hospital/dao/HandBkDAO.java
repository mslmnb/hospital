package com.epam.hospital.dao;

import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;

import java.util.List;

public interface HandBkDAO {
    Handbk create(Handbk handbk);

    Staff create(Staff staff);

    // null if not found
    Staff update(Staff staff);

    // false if not found
    boolean delete(int id);

    // null if not found
    Handbk get(int id);

    // empty if not found
    List<Handbk> getAll(String lang, HandbkType handbkType);
}
