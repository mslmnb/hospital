package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.HandbkItemDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.deleteFromTable;
import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public class JdbcHandbkItemDAOImpl implements HandbkItemDAO {
    private static final Logger LOG = Logger.getLogger(JdbcHandbkItemDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";
    private static final String TYPE_FIELDNAME = "type";

    private static final String FOREIGN_KEY_IN_STAFF = "staff_position_item_id_fkey";
    private static final String FOREIGN_KEY_IN_DIAGNOSIS_REGISTER = "diagnosis_register_diagnosis_item_id_fkey";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_STAFF = "impossibleRemovingForStaff";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_DIAGNOSIS = "impossibleRemovingForDiagnosis";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_ALL_TRANSLATIONS = "SELECT handbk_items.id, " +
            "handbk_item_translations.translation AS name, type " +
            "FROM handbk_items, handbk_item_translations " +
            "WHERE handbk_items.id = handbk_item_translations.handbk_item_id AND " +
            "handbk_item_translations.locale = ? AND handbk_items.type = ?";
    private static final String SELECT_ALL = "SELECT id, name, type FROM handbk_items " +
            "WHERE handbk_items.type = ?";
    private static final String SELECT_BY_ID = "SELECT id, name,  type FROM handbk_items WHERE id = ? ";

    private static final String INSERT_INTO = "INSERT INTO handbk_items (name, type) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE handbk_items SET name = ?, type = ? WHERE id = ?";
    private static final String TABLE_NAME = "handbk_items";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(FOREIGN_KEY_IN_STAFF, IMPOSSIBLE_REMOVING_ERROR_FOR_STAFF);
        errorResolver.put(FOREIGN_KEY_IN_DIAGNOSIS_REGISTER, IMPOSSIBLE_REMOVING_ERROR_FOR_DIAGNOSIS);
    }

    private final ConnectionPool pool;

    public JdbcHandbkItemDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public HandbkItem create(HandbkItem handbkItem) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, handbkItem.getName());
                statement.setString(2, handbkItem.getType().toString());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    handbkItem.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbkItem;
    }

    @Override
    public HandbkItem update(HandbkItem handbkItem) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setString(1, handbkItem.getName());
                statement.setString(2, handbkItem.getType().toString());
                statement.setInt(3, handbkItem.getId());
                if (statement.executeUpdate() == 0) {
                    handbkItem = null;
                }
                ;
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbkItem;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public HandbkItem get(int id) {
        Connection con = pool.getConnection();
        HandbkItem handbkItem = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        handbkItem = getHandbk(resultSet);
                        break;
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return handbkItem;
    }

    @Override
    public List<HandbkItem> getAllTranslations(String locale, HandbkType type) {
        Connection con = pool.getConnection();
        List<HandbkItem> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL_TRANSLATIONS)) {
                statement.setString(1, locale);
                statement.setString(2, type.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getHandbk(resultSet));
                    }
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

    @Override
    public List<HandbkItem> getAll(HandbkType type) {
        Connection con = pool.getConnection();
        List<HandbkItem> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setString(1, type.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getHandbk(resultSet));
                    }
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

    private HandbkItem getHandbk(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String handbkType = resultSet.getString(TYPE_FIELDNAME);
        return new HandbkItem(id, name, HandbkType.valueOf(handbkType));
    }
}
