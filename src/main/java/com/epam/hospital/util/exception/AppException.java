package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public abstract class AppException extends RuntimeException{
    public static final String UNKNOWN_ERROR = "error.common.unknownError";

    private final CheckResult checkResult;

    public AppException(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public CheckResult getCheckResult() {
        return checkResult;
    }
}
