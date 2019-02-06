package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.HandBkDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForUnknowError;
import static com.epam.hospital.util.exception.AppException.NO_DB_CONNECTION_ERROR;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class JdbcHandbkDAOImpl implements HandBkDAO {
    private static final Logger LOG = Logger.getLogger(JdbcHandbkDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";

    private static final String SELECT_ALL = "SELECT handbk_items.id, lang_dictionary.word AS name " +
            "FROM handbk_items, lang_dictionary, handbk_item_translate " +
            "WHERE handbk_items.id = handbk_item_translate.item_id AND " +
            "handbk_item_translate.lang_dictionary_id = lang_dictionary.id AND " +
            "lang_dictionary.lang = ? AND handbk_items.handbk = ?";

    private final ConnectionPool pool;

    public JdbcHandbkDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Handbk create(Handbk handbk) {
        return null;
    }

    @Override
    public Staff create(Staff staff) {
        return null;
    }

    @Override
    public Staff update(Staff staff) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Handbk get(int id) {
        return null;
    }

    @Override
    public List<Handbk> getAll(String lang, HandbkType handbkType) {
        Connection con = pool.getConnection();
        List<Handbk> resultList = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setString(1, lang);
                statement.setString(2, handbkType.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(ID_FIELDNAME);
                        String name = resultSet.getString(NAME_FIELDNAME);
                        resultList.add(new Handbk(id, name, HandbkType.POSITION));
                    }
                }
            } catch (SQLException e) {
                logAndThrowForUnknowError(LOG, e);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return resultList;
    }
}
