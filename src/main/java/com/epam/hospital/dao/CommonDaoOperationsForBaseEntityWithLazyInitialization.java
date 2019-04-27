package com.epam.hospital.dao;

import com.epam.hospital.dao.CommonDaoOperationsForBaseEntity;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.model.BaseEntity;
import org.apache.log4j.Logger;

import java.sql.*;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public interface CommonDaoOperationsForBaseEntityWithLazyInitialization<T extends BaseEntity>
                                                           extends CommonDaoOperationsForBaseEntity<T> {

    <T> T getLazyObject(ResultSet resultSet) throws SQLException;

    default <T> T getWithLazyInitialization(ConnectionPool pool, String sql, Logger log, int id) {
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
