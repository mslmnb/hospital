package com.epam.hospital.dao.jdbc;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.UserDAO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.epam.hospital.util.DaoUtil.*;

/**
 * The class of jdbc operation for {@code User} entity
 */

public class JdbcUserJdbcImpl implements UserDAO, CommonJdbcOperationsForBaseEntityWithLazyInitialization<User> {
    private static final Logger LOG = Logger.getLogger(JdbcUserJdbcImpl.class);

    private static final String STAFF_ID_FIELDNAME = "staff_id";
    private static final String NAME_FIELDNAME = "name";
    private static final String ADDITIONAL_NAME_FIELDNAME = "additional_name";
    private static final String SURNAME_FIELDNAME = "surname";
    private static final String ROLE_FIELDNAME = "role";
    private static final String LOGIN_FIELDNAME = "login";
    private static final String PASSWORD_FIELDNAME = "password";

    private static final String LOGIN_UNIQUE_IDX = "users_unique_login_idx";
    private static final String STAFF_AND_ROLE_UNIQUE_IDX = "users_unique_staff_role_idx";
    private static final String NOT_UNIQUE_LOGIN = "notUniqueLogin";
    private static final String NOT_UNIQUE_STAFF_AND_ROLE = "notUniqueStaffAndRole";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_BY_LOGIN = "SELECT users.id, users.staff_id, users.password, users.role, " +
                                                      "users.login, staff.name, staff.additional_name, staff.surname " +
                                                  "FROM users " +
                                                  "LEFT JOIN staff ON users.staff_id = staff.id " +
                                                   "WHERE users.login = ? ";
    private static final String SELECT_ALL = "SELECT users.id, users.staff_id, users.password, users.role, " +
                                                 "users.login, staff.name, staff.additional_name, staff.surname " +
                                             "FROM users " +
                                             "LEFT JOIN staff ON users.staff_id = staff.id ";
    private static final String SELECT_BY_ID = "SELECT id, staff_id, role, login " +
                                               "FROM users " +
                                               "WHERE users.id = ? ";
    private static final String INSERT_INTO = "INSERT INTO users (staff_id,  login,  password, role) " +
                                              "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    private static final String TABLE_NAME = "users";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(LOGIN_UNIQUE_IDX, NOT_UNIQUE_LOGIN);
        errorResolver.put(STAFF_AND_ROLE_UNIQUE_IDX, NOT_UNIQUE_STAFF_AND_ROLE);
    }

    private final ConnectionPool pool;

    public JdbcUserJdbcImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public User create(User user) {
        return create(pool, LOG, INSERT_INTO, user, errorResolver);
    }

    @Override
    public User update(User user) {
        return update(pool, LOG, UPDATE_PASSWORD, user);
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public User get(int id) {
        return getWithLazyInitialization(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public User getByLogin(String login) {
        Connection con = pool.getConnection();
        User user = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = getObject(resultSet);
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
    public List<User> getAll() {
        return getAll(pool, SELECT_ALL, LOG);
    }

    @Override
    public User getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int staffId = resultSet.getInt(STAFF_ID_FIELDNAME);
        String password = resultSet.getString(PASSWORD_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String additionalName = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
        String surname = resultSet.getString(SURNAME_FIELDNAME);
        String login = resultSet.getString(LOGIN_FIELDNAME);
        Role role = Role.valueOf(resultSet.getString(ROLE_FIELDNAME));
        Staff staff = new Staff(staffId, name, additionalName, surname);
        return new User(id, staff, login, password, role);
    }

    @Override
    public User getLazyObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int staffId = resultSet.getInt(STAFF_ID_FIELDNAME);
        Role role = Role.valueOf(resultSet.getString(ROLE_FIELDNAME));
        Staff staff = new Staff(staffId, null, null, null);
        String login = resultSet.getString(LOGIN_FIELDNAME);
        return new User(id, staff, login, null, role);
    }

    @Override
    public void setParametersForCreatingRecord(PreparedStatement statement, User user) throws SQLException {
        statement.setInt(1, user.getStaffId());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole().toString());
    }

    @Override
    public void setParametersForUpdatingRecord(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getPassword());
        statement.setInt(2, user.getId());
    }
}
