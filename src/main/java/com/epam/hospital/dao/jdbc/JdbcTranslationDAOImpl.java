package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.TranslationDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.Translation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.*;

public class JdbcTranslationDAOImpl implements TranslationDAO {
    private static final Logger LOG = Logger.getLogger(JdbcTranslationDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String HANDBK_ITEM_ID_FIELDNAME = "handbk_item_id";
    private static final String LOCALE_FIELDNAME = "locale";
    private static final String TRANSLATION_FIELDNAME = "translation";

    private static final String ITEM_AND_LOCALE_UNIQUE_IDX = "translate_unique_item_locale_idx";
    private static final String NOT_UNIQUE_ITEM_AND_LOCALE = "notUniqueItemAndLocale";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_ALL = "SELECT id, handbk_item_id, locale, translation " +
                                             "FROM handbk_item_translations " +
                                             "WHERE handbk_item_id = ?";

    private static final String SELECT_BY_ID = "SELECT id, handbk_item_id, locale, translation " +
                                               "FROM handbk_item_translations " +
                                               "WHERE id = ?";
    private static final String INSERT_INTO = "INSERT INTO handbk_item_translations " +
                                                  "(handbk_item_id, locale, translation) " +
                                              "VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE handbk_item_translations " +
                                         "SET locale = ?, translation = ? " +
                                         "WHERE id = ?";
    private static final String TABLE_NAME = "handbk_item_translations";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(ITEM_AND_LOCALE_UNIQUE_IDX, NOT_UNIQUE_ITEM_AND_LOCALE);
    }

    private final ConnectionPool pool;

    public JdbcTranslationDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Translation create(Translation translation) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, translation.getHandbkItem().getId());
                statement.setString(2, translation.getLang().getLocale());
                statement.setString(3, translation.getItemTranslation());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    translation.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(errorResolver, e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return translation;
    }

    @Override
    public Translation update(Translation translation) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setString(1, translation.getLang().getLocale());
                statement.setString(2, translation.getItemTranslation());
                statement.setInt(3, translation.getId());
                if (statement.executeUpdate() == 0) {
                    translation = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(errorResolver, e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return translation;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public Translation get(int id) {
        Connection con = pool.getConnection();
        Translation user = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = getTranslationWithLazyHandbk(resultSet);
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
        return user;

    }

    @Override
    public List<Translation> getAll(Integer handbkItemId) {
        Connection con = pool.getConnection();
        List<Translation> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setInt(1, handbkItemId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getTranslationWithLazyHandbk(resultSet));
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

    private Translation getTranslationWithLazyHandbk(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int handbkItemId = resultSet.getInt(HANDBK_ITEM_ID_FIELDNAME);
        HandbkItem handbkItem = new HandbkItem(handbkItemId);
        String locale = resultSet.getString(LOCALE_FIELDNAME);
        String tranlation = resultSet.getString(TRANSLATION_FIELDNAME);
        return new Translation(id, handbkItem, locale, tranlation);
    }
}
