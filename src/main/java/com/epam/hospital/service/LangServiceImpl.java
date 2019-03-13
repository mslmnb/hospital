package com.epam.hospital.service;

import com.epam.hospital.dao.LangDAO;
import com.epam.hospital.model.Lang;

import java.util.List;

public class LangServiceImpl implements LangService{
    public final static String ID_PARAMETER = "id";
    public final static String NAME_PARAMETER = "name";

    private final LangDAO dao;

    public LangServiceImpl(LangDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Lang> getAll() {
        return dao.getAll();
    }
}
