package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.TranslationDAO;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.Translation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class of jdbc operation for {@code Translation} entity
 */

public class JdbcTranslationJdbcImpl implements TranslationDAO, CommonJdbcOperationsForBaseEntity<Translation> {
    private static final Logger LOG = Logger.getLogger(JdbcTranslationJdbcImpl.class);

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

    public JdbcTranslationJdbcImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Translation create(Translation translation) {
        return create(pool, LOG, INSERT_INTO, translation, errorResolver);
    }

    @Override
    public Translation update(Translation translation) {
        return update(pool, LOG, UPDATE, translation, errorResolver);    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public Translation get(int id) {
        return get(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public List<Translation> getAll(Integer handbkItemId) {
        Integer[] intArgs = {handbkItemId};
        return getAll(pool, SELECT_ALL, LOG, intArgs);
    }

    @Override
    public Translation getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int handbkItemId = resultSet.getInt(HANDBK_ITEM_ID_FIELDNAME);
        HandbkItem handbkItem = new HandbkItem(handbkItemId);
        String locale = resultSet.getString(LOCALE_FIELDNAME);
        String tranlation = resultSet.getString(TRANSLATION_FIELDNAME);
        return new Translation(id, handbkItem, locale, tranlation);
    }

    @Override
    public void setParametersForCreatingRecord(PreparedStatement statement,
                                               Translation translation) throws SQLException {
        statement.setInt(1, translation.getHandbkItem().getId());
        statement.setString(2, translation.getLang().getLocale());
        statement.setString(3, translation.getItemTranslation());
    }

    @Override
    public void setParametersForUpdatingRecord(PreparedStatement statement,
                                               Translation translation) throws SQLException {
        statement.setString(1, translation.getLang().getLocale());
        statement.setString(2, translation.getItemTranslation());
        statement.setInt(3, translation.getId());
    }
}
