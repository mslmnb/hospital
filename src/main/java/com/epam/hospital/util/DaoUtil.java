package com.epam.hospital.util;

import com.epam.hospital.util.exception.SqlAppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

/**
 * The class of utilities for DAO classes
 */

public class DaoUtil {
    private static final String NO_DB_CONNECTION_ERROR = "error.common.noDbConnection";

    /**
     * Registers an error message of specified {@code SQLException} by specified logger
     * and throws {@code SqlAppException}
     * @param e {@code SQLException}
     * @param logger the logger
     */
    public static void logAndThrowForSQLException(SQLException e, Logger logger) {
        logAndThrowForSQLException(null, e, logger);
    }

    /**
     * Registers an error message of specified {@code SQLException} by specified logger
     * and throws {@code SqlAppException}
     * @param errorResolver the list of the expected errors
     * @param e {@code SQLException}
     * @param logger the logger
     */
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
        throw new SqlAppException(checkResult);
    }

    /**
     * Registers an error message about no connection with database by specified logger
     * and throws {@code SqlAppException}
     * @param logger the logger for registration an error message
     */
    public static void logAndThrowForNoDbConnectionError(Logger logger) {
        logger.error("There is no database connection.");
        throw new SqlAppException(new CheckResult(NO_DB_CONNECTION_ERROR));
    }

}
