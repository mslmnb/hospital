package com.epam.hospital.service;

import com.epam.hospital.util.exception.AppException;

public interface HavingDeleteMethod {
    void delete(String idAsString) throws AppException;
}
