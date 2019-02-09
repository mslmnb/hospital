package com.epam.hospital.util;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static com.epam.hospital.util.exception.AppException.NO_DB_CONNECTION_ERROR;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class DaoUtil {
    private static String DELETE_FROM_START = "DELETE FROM ";
    private static String DELETE_FROM_END = " WHERE id = ?";

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

    public static boolean deleteFromTable(String tableName, Logger logger,
                                          ConnectionPool pool, int id) {
        return deleteFromTable(tableName, logger, pool, id, null);
    }

    public static boolean deleteFromTable(String tableName, Logger logger,
                                          ConnectionPool pool, int id, Map<String, String> errorResolver) {
        boolean result = false;
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(DELETE_FROM_START + tableName + DELETE_FROM_END)) {
                statement.setInt(1, id);
                if (statement.executeUpdate()>0) {
                    result = true;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(errorResolver, e, logger);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(logger);
        }
        return result;
    }

    public static void logAndThrowForNoDbConnectionError(Logger logger) {
        logger.error("There is no database connection.");
        throw new AppException(new CheckResult(NO_DB_CONNECTION_ERROR));
    }
}
