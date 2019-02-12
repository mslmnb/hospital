package com.epam.hospital.util;

import com.epam.hospital.util.exception.AppException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_FOR_PHONE = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String I18N_KEY_FOR_INVALID_EMAIL = "invalidEmail";
    private static final String I18N_KEY_FOR_INVALID_PHONE = "invalidPhone";
    private static final String I18N_KEY_FOR_EMPTY = "empty";
    private static final String I18N_KEY_FOR_INVALID = "invalid";
    private static final String I18N_KEY_FOR_NOT_FOUND = "notFound";

    public static <T> T checkNotFound(T object) throws AppException {
        if (object == null) {
            throw new AppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
        return object;
    }

    public static void checkNotFound(boolean found) throws AppException {
        if (!found) {
            throw new AppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
    }

    public static void checkNotEmpty(Map<String, String> parameters,
                                     CheckResult checkResult, boolean throwException) throws AppException {
        try {
            checkNotEmpty(parameters);
        } catch (AppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new AppException(checkResult);
        }
    }

    public static void checkNotEmpty(Map<String, String> parameters) throws AppException {
        CheckResult checkResult = new CheckResult();
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            String fieldName = pair.getKey();
            String fieldValue = pair.getValue();
            if (fieldValue == null || fieldValue.isEmpty()) {
                checkResult.addErrorMessage(I18N_KEY_FOR_EMPTY + getWithCapitalFirstLetter(fieldName));
            }
        }
        if (checkResult.foundErrors()) {
            throw new AppException(checkResult);
        }
    }

    public static void checkNotEmpty(String fieldValue, String fieldName) throws AppException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(fieldName, fieldValue);
        checkNotEmpty(parameters);
    }

    private static String getWithCapitalFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void checkEmail(String email, CheckResult checkResult, boolean throwException) {
        try {
            checkEmail(email);
        } catch (AppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new AppException(checkResult);
        }
    }

    public static void checkEmail(String email) {
        checkByRegex(REGEX_FOR_EMAIL, email, I18N_KEY_FOR_INVALID_EMAIL);
    }

    public static void checkPhone(String phone, CheckResult checkResult, boolean throwException) {
        try {
            checkPhone(phone);
        } catch (AppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new AppException(checkResult);
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
                throw new AppException(new CheckResult(errorMsg));
            }
        }
    }

    public static LocalDate checkAndReturnDate(String dateAsString, String fieldName,
                                               CheckResult checkResult, boolean throwException) throws AppException {
        LocalDate result = null;
        try {
            result = checkAndReturnDate(dateAsString, fieldName);
        } catch (AppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new AppException(checkResult);
        }
        return result;
    }

    public static LocalDate checkAndReturnDate(String dateAsString, String fieldName) throws AppException {
        LocalDate result = null;
        checkNotEmpty(dateAsString, fieldName);
        try {
            result = LocalDate.parse(dateAsString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new AppException(new CheckResult(I18N_KEY_FOR_INVALID + getWithCapitalFirstLetter(fieldName)));
        }
        return result;
    }

    public static Integer checkAndReturnInt(String intAsString, String fieldName,
                                            CheckResult checkResult, boolean throwException) throws AppException {
        Integer result = null;
        try {
            result = checkAndReturnInt(intAsString, fieldName);
        } catch (AppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new AppException(checkResult);
        }
        return result;
    }

    public static Integer checkAndReturnInt(String intAsString, String fieldName) throws AppException {
        Integer result = null;
        checkNotEmpty(intAsString, fieldName);
        try {
            result = Integer.parseInt(intAsString);
        } catch (NumberFormatException e) {
            throw new AppException(new CheckResult(I18N_KEY_FOR_INVALID + getWithCapitalFirstLetter(fieldName)));
        }
        return result;
    }

}