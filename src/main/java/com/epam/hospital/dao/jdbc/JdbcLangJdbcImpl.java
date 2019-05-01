package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.LangDAO;
import com.epam.hospital.model.Lang;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The class of jdbc operation for {@code Lang} entity
 */

public class JdbcLangJdbcImpl implements LangDAO, CommonJdbcOperations<Lang> {
    private static final Logger LOG = Logger.getLogger(JdbcLangJdbcImpl.class);

    private static final String LOCALE_FIELDNAME = "locale";
    private static final String SELECT_ALL = "SELECT locale FROM lang";

    private final ConnectionPool pool;

    public JdbcLangJdbcImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Lang> getAll() {
        return getAll(pool, SELECT_ALL, LOG);
    }

    @Override
    public Lang getObject(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(LOCALE_FIELDNAME);
        return new Lang(name);
    }
}

