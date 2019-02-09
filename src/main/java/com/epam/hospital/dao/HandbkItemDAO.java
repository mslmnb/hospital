package com.epam.hospital.dao;

import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;

import java.util.List;

public interface HandbkItemDAO {
    HandbkItem create(HandbkItem handbkItem);

    // null if not found
    HandbkItem update(HandbkItem handbkItem);

    // false if not found
    boolean delete(int id);

    // null if not found
    HandbkItem get(int id);

    // empty if not found
    List<HandbkItem> getAll(HandbkType handbkType);

    // empty if not found
    List<HandbkItem> getAllTranslations(String lang, HandbkType handbkType);
}
