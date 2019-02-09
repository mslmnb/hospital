package com.epam.hospital.service;

import com.epam.hospital.dao.jdbc.LangDAO;
import com.epam.hospital.model.handbk.Lang;

import java.util.List;

public class LangServiceImpl implements LangService{

    private final LangDAO dao;

    public LangServiceImpl(LangDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Lang> getAll() {
        return dao.getAll();
    }
}
