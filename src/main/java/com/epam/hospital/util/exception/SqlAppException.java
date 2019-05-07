package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

/**
 * This exception is thrown to indicate that a database access error occurs
 */

public class SqlAppException extends AppException {
    public SqlAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
