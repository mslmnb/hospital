package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.HandbkItemDAO;
import com.epam.hospital.dao.CommonDaoOperationsForBaseEntity;
import com.epam.hospital.model.handbk.HandbkItem;
import com.epam.hospital.model.handbk.HandbkType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcHandbkItemDAOImpl implements HandbkItemDAO, CommonDaoOperationsForBaseEntity<HandbkItem> {
    private static final Logger LOG = Logger.getLogger(JdbcHandbkItemDAOImpl.class);

    private static final String NAME_FIELDNAME = "name";
    private static final String TYPE_FIELDNAME = "type";

    private static final String FOREIGN_KEY_IN_STAFF = "staff_position_item_id_fkey";
    private static final String FOREIGN_KEY_IN_DIAGNOSIS_REGISTER = "diagnosis_register_diagnosis_item_id_fkey";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_STAFF = "impossibleRemovingForStaff";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_DIAGNOSIS = "impossibleRemovingForDiagnosis";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_ALL_TRANSLATIONS = "SELECT hi.id, hit.translation AS name, type " +
                                                          "FROM handbk_items AS hi, handbk_item_translations AS hit " +
                                                          "WHERE hi.id = hit.handbk_item_id AND " +
                                                              "hit.locale = ? AND hi.type = ?";
    private static final String SELECT_ALL = "SELECT id, name, type " +
                                             "FROM handbk_items " +
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
        return create(pool, LOG, INSERT_INTO, handbkItem);
    }

    @Override
    public HandbkItem update(HandbkItem handbkItem) {
        return update(pool, LOG, UPDATE, handbkItem);
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public HandbkItem get(int id) {
        return get(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public List<HandbkItem> getAllTranslations(String locale, HandbkType type) {
        String[] strArgs = {locale, type.toString()};
        return getAll(pool, SELECT_ALL_TRANSLATIONS, LOG, strArgs);
    }

    @Override
    public List<HandbkItem> getAll(HandbkType type) {
        String[] strArgs = {type.toString()};
        return getAll(pool, SELECT_ALL, LOG, strArgs);
    }

    @Override
    public HandbkItem getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String handbkType = resultSet.getString(TYPE_FIELDNAME);
        return new HandbkItem(id, name, HandbkType.valueOf(handbkType));
    }

    @Override
    public void setParametersForCreatingObject(PreparedStatement statement, HandbkItem handbkItem) throws SQLException {
        setCommonParameters(statement, handbkItem);    }

    @Override
    public void setParametersForUpdatingObject(PreparedStatement statement, HandbkItem handbkItem) throws SQLException {
        setCommonParameters(statement, handbkItem);
        statement.setInt(3, handbkItem.getId());
    }

    private void setCommonParameters(PreparedStatement statement, HandbkItem handbkItem) throws SQLException{
        statement.setString(1, handbkItem.getName());
        statement.setString(2, handbkItem.getType().toString());

    }

}
