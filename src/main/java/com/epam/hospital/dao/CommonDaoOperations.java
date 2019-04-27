package com.epam.hospital.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public interface CommonDaoOperations<T> {
    String DELETE_FROM_START = "DELETE FROM ";
    String DELETE_FROM_END = " WHERE id = ?";

    <T> T getObject(ResultSet resultSet) throws SQLException;

    default List<T> getAll(ConnectionPool pool, String sql, Logger log) {
        String[] strArgs = {};
        Integer[] intArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

    default List<T> getAll(ConnectionPool pool, String sql, Logger log, String[] strArgs) {
        Integer[] intArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

    default List<T> getAll(ConnectionPool pool, String sql, Logger log, Integer[] intArgs) {
        String[] strArgs = {};
        return getAll(pool, sql, log, strArgs, intArgs);
    }

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

    default T get(ConnectionPool pool, String sql, Logger log, int id) {
        Connection con = pool.getConnection();
        T obj = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        obj = getObject(resultSet);
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, log);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(log);
        }
        return obj;
    }

    default boolean deleteFromTable(String tableName, Logger logger,
                                          ConnectionPool pool, int id) {
        return deleteFromTable(tableName, logger, pool, id, null);
    }


    default boolean deleteFromTable(String tableName, Logger logger,
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

}
