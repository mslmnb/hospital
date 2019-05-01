package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public class SqlAppException extends AppException {
    public SqlAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
