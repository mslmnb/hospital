package com.epam.hospital.service;

import com.epam.hospital.model.handbk.Lang;

import java.util.List;

public interface LangService {
    String LOCALE_PARAMETER = "locale";

    List<Lang> getAll();
}
