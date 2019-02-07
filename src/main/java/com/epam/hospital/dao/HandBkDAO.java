package com.epam.hospital.dao;

import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;

import java.util.List;

public interface HandBkDAO {
    Handbk create(Handbk handbk);

    // null if not found
    Handbk update(Handbk handbk);

    // false if not found
    boolean delete(int id);

    // null if not found
    Handbk get(int id);

    // empty if not found
    List<Handbk> getAll(HandbkType handbkType);

    // empty if not found
    List<Handbk> getAllTranslations(String lang, HandbkType handbkType);
}
