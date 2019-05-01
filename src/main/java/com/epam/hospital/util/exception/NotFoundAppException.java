package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public class NotFoundAppException extends AppException{
    public NotFoundAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
