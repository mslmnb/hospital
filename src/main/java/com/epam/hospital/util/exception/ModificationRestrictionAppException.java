package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

public class ModificationRestrictionAppException extends AppException{
    public ModificationRestrictionAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
