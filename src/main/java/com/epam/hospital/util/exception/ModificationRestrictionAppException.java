package com.epam.hospital.util.exception;

import com.epam.hospital.util.CheckResult;

/**
 * This exception is thrown to indicate that demonstrational records cannot be changed
 */
public class ModificationRestrictionAppException extends AppException{
    public ModificationRestrictionAppException(CheckResult checkResult) {
        super(checkResult);
    }
}
