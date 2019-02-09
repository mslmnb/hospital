package com.epam.hospital.dao.jdbc;

import com.epam.hospital.model.handbk.Lang;

import java.util.List;

public interface LangDAO {
    // empty if not found
    List<Lang> getAll();
}
