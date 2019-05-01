package com.epam.hospital.util;

import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_FOR_PHONE = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String I18N_KEY_FOR_INVALID_EMAIL = "invalidEmail";
    private static final String I18N_KEY_FOR_INVALID_PHONE = "invalidPhone";
    private static final String I18N_KEY_FOR_EMPTY = "empty";
    private static final String I18N_KEY_FOR_INVALID = "invalid";
    private static final String I18N_KEY_FOR_NOT_FOUND = "notFound";

    public static <T> T checkNotFound(T object) throws NotFoundAppException {
        if (object == null) {
            throw new NotFoundAppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
        return object;
    }

    public static void checkNotFound(boolean found) throws NotFoundAppException {
        if (!found) {
            throw new NotFoundAppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
    }

    public static void checkNotEmpty(String fieldValue, String fieldName,
                                     CheckResult checkResult, boolean throwException) throws UnvalidDataAppException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            checkResult.addErrorMessage(I18N_KEY_FOR_EMPTY + getWithCapitalFirstLetter(fieldName));
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    public static void checkNotEmpty(String fieldValue, String fieldName,
                                     CheckResult checkResult) throws UnvalidDataAppException {
        checkNotEmpty(fieldValue, fieldName, checkResult, true);
    }

    private static void checkNotEmpty(String fieldValue, String fieldName) throws UnvalidDataAppException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new UnvalidDataAppException(new CheckResult(I18N_KEY_FOR_EMPTY +
                                                              getWithCapitalFirstLetter(fieldName)));
        }
    }

    private static String getWithCapitalFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void checkEmail(String email, CheckResult checkResult, boolean throwException) {
        try {
            checkEmail(email);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    public static void checkEmail(String email) {
        checkByRegex(REGEX_FOR_EMAIL, email, I18N_KEY_FOR_INVALID_EMAIL);
    }

    public static void checkPhone(String phone, CheckResult checkResult, boolean throwException) {
        try {
            checkPhone(phone);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    public static void checkPhone(String phone) {
        checkByRegex(REGEX_FOR_PHONE, phone, I18N_KEY_FOR_INVALID_PHONE);
    }

    private static void checkByRegex(String regex, String target, String errorMsg) {
        if (!target.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(target);
            if (!matcher.matches()) {
                throw new UnvalidDataAppException(new CheckResult(errorMsg));
            }
        }
    }

    public static LocalDate checkAndReturnDate(String dateAsString, String fieldName, CheckResult checkResult,
                                               boolean throwException) throws UnvalidDataAppException {
        LocalDate result = null;
        try {
            result = checkAndReturnDate(dateAsString, fieldName);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
        return result;
    }

    public static LocalDate checkAndReturnDate(String dateAsString, String fieldName) throws UnvalidDataAppException {
        LocalDate result;
        checkNotEmpty(dateAsString, fieldName);
        try {
            result = LocalDate.parse(dateAsString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new UnvalidDataAppException(new CheckResult(I18N_KEY_FOR_INVALID +
                                                              getWithCapitalFirstLetter(fieldName)));
        }
        return result;
    }

    public static Integer checkAndReturnInt(String intAsString, String fieldName, CheckResult checkResult,
                                            boolean throwException) throws UnvalidDataAppException {
        Integer result = null;
        try {
            result = checkAndReturnInt(intAsString, fieldName);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
        return result;
    }

    public static Integer checkAndReturnInt(String intAsString, String fieldName) throws UnvalidDataAppException {
        Integer result;
        checkNotEmpty(intAsString, fieldName);
        try {
            result = Integer.parseInt(intAsString);
        } catch (NumberFormatException e) {
            throw new UnvalidDataAppException(new CheckResult(I18N_KEY_FOR_INVALID +
                                                              getWithCapitalFirstLetter(fieldName)));
        }
        return result;
    }

}