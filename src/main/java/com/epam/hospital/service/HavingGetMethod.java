package com.epam.hospital.service;

import com.epam.hospital.model.HavingJsonView;
import com.epam.hospital.util.exception.AppException;

public interface HavingGetMethod {
    HavingJsonView get(String idAsString) throws AppException;
}
