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

    public static CheckResult AnalyzerSQLException(Map<String, String> errorResolver, SQLException e) {
        CheckResult result = new CheckResult();
        String errorMsg = e.getMessage();
        if (errorResolver!=null) {
            for (Map.Entry<String, String> pair : errorResolver.entrySet()) {
                if (errorMsg.contains(pair.getKey())) {
                    result.addErrorMessage(pair.getValue());
                    break;
                }
            }
        }
        if (!result.foundErrors()) {
            result.addErrorMessage(UNKNOWN_ERROR);
        }
        return result;
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
                CheckResult checkResult = AnalyzerSQLException(errorResolver, e);
                throw new AppException(checkResult);
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

    public static void logAndThrowForUnknowError(Logger logger, SQLException e) {
        logger.error(e.getMessage());
        throw new AppException(new CheckResult(UNKNOWN_ERROR));
    }

}
