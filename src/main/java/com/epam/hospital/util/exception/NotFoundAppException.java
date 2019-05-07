package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

/**
 * This exception is thrown to indicate that the record is not found
 */

public class NotFoundAppException extends AppException{
    public NotFoundAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
