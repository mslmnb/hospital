package com.epam.hospital.util;

import com.epam.hospital.model.BaseEntity;
import com.epam.hospital.util.exception.NotFoundException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_FOR_PHONE = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static void checkNotEmpty(String str, String fieldName, CheckResult checkResult) {
        if (str == null || str.isEmpty()) {
            checkResult.addErrorMessage(fieldName + " must not be empty.");
        }
    }

    public static void checkEmail(String email, CheckResult checkResult) {
        checkByRegex(REGEX_FOR_EMAIL, email, checkResult, "Email address is invalid.");
    }

    public static void checkPhone(String phone, CheckResult checkResult) {
        checkByRegex(REGEX_FOR_PHONE, phone, checkResult, "Phone number is invalid.");
    }

    private static void checkByRegex(String regex, String target,  CheckResult checkResult, String errorMsg){
        if (!target.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(target);
            if (!matcher.matches()) {
                checkResult.addErrorMessage(errorMsg);
            }
        }
    }



    public static LocalDate checkAndReturnDate(String date, CheckResult checkResult) {
        LocalDate result = null;
        checkNotEmpty(date, "Date", checkResult);
        if (! checkResult.foundErrors()) {
            try {
                result = LocalDate.parse(date, FORMATTER);
            } catch (DateTimeParseException e) {
                checkResult.addErrorMessage("Date is invalid.");
                result = null;
            }
        }
        return result;
    }

}