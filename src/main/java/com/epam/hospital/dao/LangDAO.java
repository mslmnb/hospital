package com.epam.hospital.dao;

import com.epam.hospital.model.Lang;

import java.util.List;

public interface LangDAO {
    // empty if not found
    List<Lang> getAll();
}
