package com.epam.hospital.service;

import com.epam.hospital.model.handbk.Lang;

import java.util.List;

public interface LangService {
    String ID_PARAMETER = "id";
    String NAME_PARAMETER = "name";

    List<Lang> getAll();
}
