package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.model.BaseEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

/**
 * The interface of common jdbc operations for entities having key.
 * @param <T> the runtime type of class
 */

public interface CommonJdbcOperationsForBaseEntity<T extends BaseEntity> extends CommonJdbcOperations<T> {
    String DELETE_FROM_START = "DELETE FROM ";
    String DELETE_FROM_END = " WHERE id = ?";
    String ID_FIELDNAME = "id";

    /**
     * Gets the entity using the sql request by specified key
     * @param pool the connection pool
     * @param sql the sql request
     * @param log the logger
     * @param id the key of entity
     * @return the entity
     *   if record for specified key are not found that returns null
     */
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

    /**
     * Deletes the entity from the table using the sql request by specified key
     * @param tableName the name of the table
     * @param logger the logger
     * @param pool the connection pool
     * @param id the key of entity
     * @return {@code true} if the entity was deleted from the table, else
     *         {@code false}
     */
    default boolean deleteFromTable(String tableName, Logger logger,
                                    ConnectionPool pool, int id) {
        return deleteFromTable(tableName, logger, pool, id, null);
    }


    /**
     * Deletes the entity from the table using the sql request by specified key
     * with the registration of specific errors
     * @param tableName the name of the table
     * @param logger the logger
     * @param pool the connection pool
     * @param id the key of entity
     * @param errorResolver the list of specific errors
     * @return {@code true} if the entity was deleted from the table, else
     *         {@code false}
     */
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

    /**
     * Sets parameters of specified SQL statement for creating record into table for the specified entity
     * @param statement the prepared SQL statement
     * @param entity the entity
     * @throws SQLException if parameters does not correspond to a parameters
     * marker in the SQL statement; if a database access error occurs or
     * this method is called on a closed <code>PreparedStatement</code>
     */
    void setParametersForCreatingRecord(PreparedStatement statement, T entity) throws SQLException;

    /**
     * Sets parameters of specified SQL statement for updating record into table for the specified entity
     * @param statement the prepared SQL statement
     * @param entity the entity
     * @throws SQLException if parameters does not correspond to a parameters
     * marker in the SQL statement; if a database access error occurs or
     * this method is called on a closed <code>PreparedStatement</code>
     */
    void setParametersForUpdatingRecord(PreparedStatement statement, T entity) throws SQLException;

    /**
     * Creates record into table for the specified entity
     * @param pool the connection pool
     * @param log the logger
     * @param sql the SQL request
     * @param entity the entity
     * @return the entity with initialized key
     */
    default T create(ConnectionPool pool, Logger log, String sql, T entity) {
        return create(pool, log, sql, entity, null);
    }

    /**
     * Creates record into table for the specified entity
     * with the registration of specific errors
     * @param pool the connection pool
     * @param log the logger
     * @param sql the SQL request
     * @param entity the entity
     * @param errorResolver the list of specific errors
     * @return the entity with initialized key
     */
    default T create(ConnectionPool pool, Logger log, String sql, T entity, Map<String, String> errorResolver) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParametersForCreatingRecord(statement, entity);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    entity.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(errorResolver, e, log);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(log);
        }
        return entity;
    }

    /**
     * Updates record into table for the specified entity
     * @param pool the connection pool
     * @param log the logger
     * @param sql the SQL request
     * @param entity the entity
     * @return the entity
     *   if record for specified entity are not found that returns null
     */
    default T update(ConnectionPool pool, Logger log, String sql, T entity) {
        return update(pool, log, sql, entity, null);
    }

    /**
     * Updates record into table for the specified entity
     * with registration of specific errors
     * @param pool the connection pool
     * @param log the logger
     * @param sql the SQL request
     * @param entity the entity
     * @param errorResolver the list of specific errors
     * @return the entity
     *   if record for specified entity are not found that returns null
     */
    default T update(ConnectionPool pool, Logger log, String sql, T entity, Map<String, String> errorResolver) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                setParametersForUpdatingRecord(statement, entity);
                if (statement.executeUpdate() == 0) {
                    entity = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(errorResolver, e, log);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(log);
        }
        return entity;
    }

}
