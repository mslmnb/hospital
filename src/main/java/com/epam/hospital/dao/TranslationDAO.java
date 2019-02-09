package com.epam.hospital.dao;

import com.epam.hospital.model.handbk.Translation;

import java.util.List;

public interface TranslationDAO {
    Translation create(Translation translation);

    // null if not found
    Translation update(Translation translation);

    // false if not found
    boolean delete(int id);

    // null if not found
    Translation get(int id);

    // empty if not found
    List<Translation> getAll(Integer handbkItemId);

}
