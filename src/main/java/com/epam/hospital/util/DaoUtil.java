package com.epam.hospital.util;

import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

import static com.epam.hospital.util.exception.AppException.NO_DB_CONNECTION_ERROR;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class DaoUtil {

    public static void logAndThrowForSQLException(SQLException e, Logger logger) {
        logAndThrowForSQLException(null, e, logger);
    }

    public static void logAndThrowForSQLException(Map<String, String> errorResolver, SQLException e, Logger logger) {
        String errorMsg = e.getMessage();
        logger.error(errorMsg);
        CheckResult checkResult = new CheckResult();
        if (errorResolver!=null) {
            for (Map.Entry<String, String> pair : errorResolver.entrySet()) {
                if (errorMsg.contains(pair.getKey())) {
                    checkResult.addErrorMessage(pair.getValue());
                    break;
                }
            }
        }
        if (!checkResult.foundErrors()) {
            checkResult.addErrorMessage(UNKNOWN_ERROR);
        }
        throw new AppException(checkResult);
    }

    public static void logAndThrowForNoDbConnectionError(Logger logger) {
        logger.error("There is no database connection.");
        throw new AppException(new CheckResult(NO_DB_CONNECTION_ERROR));
    }

}
