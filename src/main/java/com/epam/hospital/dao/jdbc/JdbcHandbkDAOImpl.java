package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.HandBkDAO;
import com.epam.hospital.model.handbk.Handbk;
import com.epam.hospital.model.handbk.HandbkType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.deleteFromTable;
import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForUnknowError;

public class JdbcHandbkDAOImpl implements HandBkDAO {
    private static final Logger LOG = Logger.getLogger(JdbcHandbkDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";
    private static final String TYPE_FIELDNAME = "type";

    private static final String SELECT_ALL_TRANSLATIONS = "SELECT handbk_items.id, " +
            "handbk_item_translate.translation AS name, type " +
            "FROM handbk_items, handbk_item_translate " +
            "WHERE handbk_items.id = handbk_item_translate.item_id AND " +
            "handbk_item_translate.lang = ? AND handbk_items.type = ?";
    private static final String SELECT_ALL = "SELECT id, name, type FROM handbk_items " +
            "WHERE handbk_items.type = ?";
    private static final String SELECT_BY_ID = "SELECT id, name,  type FROM handbk_items WHERE id = ? ";

    private static final String INSERT_INTO = "INSERT INTO handbk_items (name, type) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE handbk_items SET name = ?, type = ? WHERE id = ?";
    private static final String STAFF_TABLE_NAME = "handbk_items";

    private final ConnectionPool pool;

    public JdbcHandbkDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Handbk create(Handbk handbk) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, handbk.getName());
                statement.setString(2, handbk.getType().toString());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    handbk.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForUnknowError(LOG, e);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbk;
    }

    @Override
    public Handbk update(Handbk handbk) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setString(1, handbk.getName());
                statement.setString(2, handbk.getType().toString());
                statement.setInt(3, handbk.getId());
                if (statement.executeUpdate() == 0) {
                    handbk = null;
                }
                ;
            } catch (SQLException e) {
                logAndThrowForUnknowError(LOG, e);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbk;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(STAFF_TABLE_NAME, LOG, pool, id);
    }

    @Override
    public Handbk get(int id) {
        Connection con = pool.getConnection();
        Handbk handbk = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        handbk = getHandbk(resultSet);
                        break;
                    }
                }
            } catch (SQLException e) {
                logAndThrowForUnknowError(LOG, e);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbk;
    }

    @Override
    public List<Handbk> getAllTranslations(String lang, HandbkType handbkType) {
        Connection con = pool.getConnection();
        List<Handbk> resultList = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL_TRANSLATIONS)) {
                statement.setString(1, lang);
                statement.setString(2, handbkType.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        resultList.add(getHandbk(resultSet));
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

    @Override
    public List<Handbk> getAll(HandbkType handbkType) {
        Connection con = pool.getConnection();
        List<Handbk> resultList = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setString(1, handbkType.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        resultList.add(getHandbk(resultSet));
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

    private Handbk getHandbk(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String handbkType = resultSet.getString(TYPE_FIELDNAME);
        return new Handbk(id, name, HandbkType.valueOf(handbkType));
    }
}
