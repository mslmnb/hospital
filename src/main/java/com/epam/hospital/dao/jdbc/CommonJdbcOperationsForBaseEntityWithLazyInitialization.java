package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.model.BaseEntity;
import org.apache.log4j.Logger;

import java.sql.*;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

/**
 * The interface of common jdbc operations for lazy initialized entities having key.
 * @param <T> the runtime type of class
 */

public interface CommonJdbcOperationsForBaseEntityWithLazyInitialization<T extends BaseEntity>
                                                           extends CommonJdbcOperationsForBaseEntity<T> {

    /**
     * Gets the lazy initialized entity of T-class from specified result set
     * @param resultSet the result set
     * @return the lazy initialized object of T-class
     * @throws SQLException if a database access error occurs or this method is
     *         called on a closed result set
     */
    T getLazyObject(ResultSet resultSet) throws SQLException;

    /**
     * Gets the lazy initialized entity of T-class using the sql request by specified key
     * @param pool the connection pool
     * @param sql the sql request
     * @param log the logger
     * @param id the key of entity
     * @return the entity of T-class
     */
    default T getWithLazyInitialization(ConnectionPool pool, String sql, Logger log, int id) {
        Connection con = pool.getConnection();
        T obj = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        obj = getLazyObject(resultSet);
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

}
