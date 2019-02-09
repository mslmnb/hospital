package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.model.handbk.Lang;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public class JdbcLangDAOImpl implements LangDAO{
    private static final Logger LOG = Logger.getLogger(JdbcLangDAOImpl.class);

    private static final String LOACLE_FIELDNAME = "locale";
    private static final String SELECT_ALL = "SELECT locale FROM lang";

    private final ConnectionPool pool;

    public JdbcLangDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Lang> getAll() {
        Connection con = pool.getConnection();
        List<Lang> results = new ArrayList<>();
        if (con != null) {
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    results.add(getLang(resultSet));
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return results;
    }

    private Lang getLang(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(LOACLE_FIELDNAME);
        return new Lang(name);
    }
}

