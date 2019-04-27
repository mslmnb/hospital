package com.epam.hospital.dao;

import com.epam.hospital.model.BaseEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public interface CommonDaoOperationsForBaseEntity<T extends BaseEntity> extends CommonDaoOperations<T> {
    String ID_FIELDNAME = "id";

    void setParametersForCreatingObject(PreparedStatement statement, T entity) throws SQLException;

    void setParametersForUpdatingObject(PreparedStatement statement, T entity) throws SQLException;

    default T create(ConnectionPool pool, Logger log, String sql, T entity) {
        return create(pool, log, sql, entity, null);
    }

    default T create(ConnectionPool pool, Logger log, String sql, T entity, Map<String, String> errorResolver) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParametersForCreatingObject(statement, entity);
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

    default T update(ConnectionPool pool, Logger log, String sql, T entity) {
        return update(pool, log, sql, entity, null);
    }

    default T update(ConnectionPool pool, Logger log, String sql, T entity, Map<String, String> errorResolver) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                setParametersForUpdatingObject(statement, entity);
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
