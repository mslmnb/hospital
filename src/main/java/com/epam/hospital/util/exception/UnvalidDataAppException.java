package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public class UnvalidDataAppException extends AppException{
    public UnvalidDataAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
