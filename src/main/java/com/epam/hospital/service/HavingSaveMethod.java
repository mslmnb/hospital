package com.epam.hospital.service;

import com.epam.hospital.util.exception.AppException;

public interface HavingSaveMethod {
    void save(String ... args)  throws AppException;
}
