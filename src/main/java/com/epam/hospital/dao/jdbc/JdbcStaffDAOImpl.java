package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.StaffDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.handbk.Position;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.*;

public class JdbcStaffDAOImpl implements StaffDAO {
    private static final Logger LOG = Logger.getLogger(JdbcStaffDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";
    private static final String ADDITIONAL_NAME_FIELDNAME = "additional_name";
    private static final String SURNAME_FIELDNAME = "surname";
    private static final String POSITION_ITEM_ID_FIELDNAME = "position_item_id";
    private static final String POSITION_FIELDNAME = "position";

    private static final String FOREIGN_KEY_IN_USERS = "users_staff_id_fkey";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_USERS = "impossibleRemovingForUsers";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_ALL = "SELECT s.id, s.name, s.additional_name, s.surname, " +
                                                 "s.position_item_id, hit.translation As position " +
                                             "FROM staff AS s, handbk_items AS hi, handbk_item_translations AS hit " +
                                             "WHERE s.position_item_id = hi.id AND " +
                                                 "hi.id = hit.handbk_item_id AND hit.locale = ? " +
                                             "ORDER BY s.id";
    private static final String SELECT_BY_ID = "SELECT id, name,  additional_name, surname, position_item_id " +
                                               "FROM staff " +
                                               "WHERE id = ? ";
    private static final String INSERT_INTO = "INSERT INTO staff" +
                                                  "(name,  additional_name, surname, position_item_id) " +
                                              "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE staff " +
                                         "SET name = ?, additional_name = ?, surname = ?, position_item_id = ?  " +
                                         "WHERE id = ?";
    private static final String TABLE_NAME = "staff";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(FOREIGN_KEY_IN_USERS, IMPOSSIBLE_REMOVING_ERROR_FOR_USERS);
    }

    private final ConnectionPool pool;

    public JdbcStaffDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Staff create(Staff staff) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, staff.getName());
                statement.setString(2, staff.getAdditionalName());
                statement.setString(3, staff.getSurname());
                statement.setInt(4, staff.getPosition().getId());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    staff.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return staff;
    }

    @Override
    public Staff update(Staff staff) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setString(1, staff.getName());
                statement.setString(2, staff.getAdditionalName());
                statement.setString(3, staff.getSurname());
                statement.setInt(4, staff.getPosition().getId());
                statement.setInt(5, staff.getId());
                if (statement.executeUpdate()==0) {
                    staff = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);        }
        return staff;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public Staff get(int id) {
        Connection con = pool.getConnection();
        Staff staff = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        staff = getStaffWithLazyPosition(resultSet);
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return staff;
    }

    @Override
    public List<Staff> getAll(String locale) {
        Connection con = pool.getConnection();
        List<Staff> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setString(1, locale);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getStaff(resultSet));
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

    private Staff getStaff(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String additional_name = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
        String surname = resultSet.getString(SURNAME_FIELDNAME);
        Integer positionId = resultSet.getInt(POSITION_ITEM_ID_FIELDNAME);
        String positionName = resultSet.getString(POSITION_FIELDNAME);
        Position position = new Position(positionId, positionName);
        return new Staff(id, name, additional_name, surname, position);
    }

    private Staff getStaffWithLazyPosition(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String additional_name = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
        String surname = resultSet.getString(SURNAME_FIELDNAME);
        Integer positionId = resultSet.getInt(POSITION_ITEM_ID_FIELDNAME);
        Position position = new Position(positionId);
        return new Staff(id, name, additional_name, surname, position);
    }


}

