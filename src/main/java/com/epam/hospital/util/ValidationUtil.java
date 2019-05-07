package com.epam.hospital.util;

import com.epam.hospital.util.exception.NotFoundAppException;
import com.epam.hospital.util.exception.UnvalidDataAppException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class of utilities for the validation of input data
 */

public class ValidationUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_FOR_PHONE = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String I18N_KEY_FOR_INVALID_EMAIL = "invalidEmail";
    private static final String I18N_KEY_FOR_INVALID_PHONE = "invalidPhone";
    private static final String I18N_KEY_FOR_EMPTY = "empty";
    private static final String I18N_KEY_FOR_INVALID = "invalid";
    private static final String I18N_KEY_FOR_NOT_FOUND = "notFound";

    /**
     * Throws {@code NotFoundAppException} if the result of request to database is empty
     * @param object the result of request to database
     * @param <T> the runtime type of class
     * @return the same object if object is found (not null) else
     *     throws {@code NotFoundAppException}
     * @throws NotFoundAppException if the result of request to database is empty
     */
    public static <T> T checkNotFound(T object) throws NotFoundAppException {
        if (object == null) {
            throw new NotFoundAppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
        return object;
    }

    /**
     * Throws {@code NotFoundAppException} if record in database is not found
     * @param found the indication that record in database is found or not
     * @throws NotFoundAppException if record in database is not found
     */
    public static void checkNotFound(boolean found) throws NotFoundAppException {
        if (!found) {
            throw new NotFoundAppException(new CheckResult(I18N_KEY_FOR_NOT_FOUND));
        }
    }

    /**
     * Checks that specified field's value is not null.
     * Adds error message to {@code checkResult} if specified field's value is null.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * and {@code throwException} is true.
     * @param fieldValue the field's value
     * @param fieldName the field's name
     * @param checkResult the object for keeping error messages
     * @param throwException the indicator specifies it is possible or it is impossible to throw an exception
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages and
     *     {@code throwException} is true
     */
    public static void checkNotEmpty(String fieldValue, String fieldName,
                                     CheckResult checkResult, boolean throwException) throws UnvalidDataAppException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            checkResult.addErrorMessage(I18N_KEY_FOR_EMPTY + getWithCapitalFirstLetter(fieldName));
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    /**
     * Checks that specified field's value is not null.
     * Adds error message to {@code checkResult} if specified field's value is null.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * @param fieldValue the input field's value
     * @param fieldName the input field's name
     * @param checkResult the object for keeping error messages
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages
     */
    public static void checkNotEmpty(String fieldValue, String fieldName,
                                     CheckResult checkResult) throws UnvalidDataAppException {
        checkNotEmpty(fieldValue, fieldName, checkResult, true);
    }

    private static String getWithCapitalFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * Checks that specified email is valid.
     * Adds error message to {@code checkResult} if specified email is not valid.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * and {@code throwException} is true.
     * @param email the email
     * @param checkResult the object for keeping error messages
     * @param throwException the indicator specifies it is possible or it is impossible to throw an exception
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages and
     *     {@code throwException} is true
     */
    public static void checkEmail(String email, CheckResult checkResult,
                                  boolean throwException) throws UnvalidDataAppException {
        try {
            checkEmail(email);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    private static void checkEmail(String email) {
        checkByRegex(REGEX_FOR_EMAIL, email, I18N_KEY_FOR_INVALID_EMAIL);
    }

    /**
     * Checks that specified phone number is valid.
     * Adds error message to {@code checkResult} if specified phone number is not valid.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * and {@code throwException} is true.
     * @param phone the phone number
     * @param checkResult the object for keeping error messages
     * @param throwException the indicator specifies it is possible or it is impossible to throw an exception
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages and
     *     {@code throwException} is true
     */
    public static void checkPhone(String phone, CheckResult checkResult,
                                  boolean throwException) throws UnvalidDataAppException {
        try {
            checkPhone(phone);
        } catch (UnvalidDataAppException e) {
            checkResult.addErrorMessage(e.getCheckResult());
        }
        if (throwException && checkResult.foundErrors()) {
            throw new UnvalidDataAppException(checkResult);
        }
    }

    private static void checkPhone(String phone) {
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

    /**
     * Checks that specified date is valid.
     * Adds error message to {@code checkResult} if specified date is not valid.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * and {@code throwException} is true.
     * @param dateAsString the date as string
     * @param fieldName the name of input field for date
     * @param checkResult the object for keeping error messages
     * @param throwException the indicator specifies it is possible or it is impossible to throw an exception
     * @return the date as {@code LocalDate}
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages and
     *     {@code throwException} is true
     */
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

    private static void checkNotEmpty(String fieldValue, String fieldName) throws UnvalidDataAppException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new UnvalidDataAppException(new CheckResult(I18N_KEY_FOR_EMPTY +
                    getWithCapitalFirstLetter(fieldName)));
        }
    }

    private static LocalDate checkAndReturnDate(String dateAsString, String fieldName) throws UnvalidDataAppException {
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

    /**
     * Checks that specified integer value is valid.
     * Adds error message to {@code checkResult} if specified integer value is not valid.
     * Throws {@code UnvalidDataAppException} if {@code checkResult} contains error messages
     * and {@code throwException} is true.
     * @param intAsString the integer value as string
     * @param fieldName the name of input field for integer value
     * @param checkResult the object for keeping error messages
     * @param throwException the indicator specifies it is possible or it is impossible to throw an exception
     * @return the integer value as {@code Integer}
     * @throws UnvalidDataAppException if {@code checkResult} contains error messages and
     *     {@code throwException} is true
     */
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

    /**
     * Checks that specified integer value is valid.
     * @param intAsString the integer value as string
     * @param fieldName the name of input field for integer value
     * @return the integer value as {@code Integer}
     * @throws UnvalidDataAppException if specified integer value is not valid
     */
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