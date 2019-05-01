package com.epam.hospital.util;

import com.epam.hospital.util.exception.SqlAppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class DaoUtil {
    private static final String NO_DB_CONNECTION_ERROR = "error.common.noDbConnection";

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
        throw new SqlAppException(checkResult);
    }

    public static void logAndThrowForNoDbConnectionError(Logger logger) {
        logger.error("There is no database connection.");
        throw new SqlAppException(new CheckResult(NO_DB_CONNECTION_ERROR));
    }

}
