package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

/**
 * This exception is thrown to indicate that data is unvalid
 */

public class UnvalidDataAppException extends AppException{
    public UnvalidDataAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
