package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public class AppException extends RuntimeException{
    public static final String NO_DB_CONNECTION_ERROR = "error.common.noDbConnection";
    public static final String UNKNOWN_ERROR = "error.common.unknownError";

    private CheckResult checkResult;

    public AppException(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public CheckResult getCheckResult() {
        return checkResult;
    }
}
