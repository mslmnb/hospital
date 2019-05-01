package com.epam.hospital.service;

import com.epam.hospital.dao.LangDAO;
import com.epam.hospital.model.Lang;

import java.util.List;

/**
 * The class of service operation for {@code Lang} entity
 */

public class LangServiceImpl implements LangService{
    public static final String ID_PARAMETER = "id";
    public static final String NAME_PARAMETER = "name";

    private final LangDAO dao;

    public LangServiceImpl(LangDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Lang> getAll() {
        return dao.getAll();
    }
}
