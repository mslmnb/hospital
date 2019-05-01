package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

/**
 * The root interface of common jdbc operations with DAO.
 * @param <T> the runtime type of class
 */

public interface CommonJdbcOperations<T> {

    /**
     * Gets the entity from specified result set
     * @param resultSet the result set
     * @return the entity
     * @throws SQLException if a database access error occurs or this method is
     *         called on a closed result set
     */
    T getObject(ResultSet resultSet) throws SQLException;

    /**
     * Gets all entities using the sql request without parameters
     * @param pool the connection pool
     * @param sql sql request without parameters
     * @param log the logger
     * @return list of entities
     */
    default List<T> getAll(ConnectionPool pool, String sql, Logger log) {
        String[] strArgs = {};
        Integer[] intArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

    /**
     * Gets all entities using the sql request with string parameters
     * @param pool the connection pool
     * @param sql the sql request with string parameters
     * @param log the logger
     * @param strArgs the string parameters for sql request
     * @return list of  entities
     */
    default List<T> getAll(ConnectionPool pool, String sql, Logger log, String[] strArgs) {
        Integer[] intArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

    /**
     * Gets all entities using the sql request with integer parameters
     * @param pool the connection pool
     * @param sql the sql request with integer parameters
     * @param log the logger
     * @param intArgs the integer parameters for sql request
     * @return list of  entities
     */
    default List<T> getAll(ConnectionPool pool, String sql, Logger log, Integer[] intArgs) {
        String[] strArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

    /**
     * Gets all entities using the sql request with string parameters and integer parameters
     * @param pool the connection pool
     * @param sql the sql request with string parameters and integer parameters
     * @param log the logger
     * @param strArgs the string parameters for sql request
     * @param intArgs the integer parameters for sql request
     * @return list of  entities
     */
    default List<T> getAll(ConnectionPool pool, String sql, Logger log, String[] strArgs, Integer[] intArgs) {
        Connection con = pool.getConnection();
        List<T> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                int i;
                for (i = 0; i < strArgs.length; i++) {
                    statement.setString(i + 1, strArgs[i]);
                }
                for (int j = 0; j < intArgs.length; j++) {
                    statement.setInt(i + j + 1, intArgs[j]);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getObject(resultSet));
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, log);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(log);
        }
        return results;
    }
}
