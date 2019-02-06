package com.epam.hospital.dao.jdbc;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.UserDAO;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.*;

public class JdbcUserDAOImpl implements UserDAO {
    private static final Logger LOG = Logger.getLogger(JdbcUserDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String STAFF_ID_FIELDNAME = "staff_id";
    private static final String NAME_FIELDNAME = "name";
    private static final String ADDITIONAL_NAME_FIELDNAME = "additional_name";
    private static final String SURNAME_FIELDNAME = "surname";
    private static final String ROLE_FIELDNAME = "role";
    private static final String LOGIN_FIELDNAME = "login";
    private static final String PASSWORD_FIELDNAME = "password";

    private static final String LOGIN_UNIQUE_IDX = "users_unique_login_idx";
    private static final String ROLE_UNIQUE_IDX = "users_unique_staff_role_idx";
    private static final String NOT_UNIQUE_LOGIN = "notUniqueLogin";
    private static final String NOT_UNIQUE_STAFF_AND_ROLE = "notUniqueStaffAndRole";
    private static final Map<String, String> errorResolver;

    private static final String SELECT_BY_LOGIN = "SELECT users.id, users.staff_id, users.password, users.role, " +
            "staff.name, staff.additional_name, staff.surname " +
            "FROM users " +
            "LEFT JOIN staff ON users.staff_id = staff.id " +
            "WHERE users.login = ? ";
    private static final String SELECT_ALL = "SELECT users.id, users.staff_id, users.password, users.role, " +
            "users.login, staff.name, staff.additional_name, staff.surname " +
            "FROM users " +
            "LEFT JOIN staff ON users.staff_id = staff.id ";
    private static final String SELECT_BY_ID = "SELECT id, staff_id, role, login " +
            "FROM users WHERE users.id = ? ";
    private static final String INSERT_INTO = "INSERT INTO users" +
            "(staff_id,  login,  password, role) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    private static final String USERS_TABLE_NAME = "users";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(LOGIN_UNIQUE_IDX, NOT_UNIQUE_LOGIN);
        errorResolver.put(ROLE_UNIQUE_IDX, NOT_UNIQUE_STAFF_AND_ROLE);
    }

    private final ConnectionPool pool;

    public JdbcUserDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public User create(User user) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, user.getStaffId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getRole().toString());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    user.setId(id);
                }
            } catch (SQLException e) {
                String errorMsg = e.getMessage();
                LOG.error(errorMsg);
                CheckResult checkResult = AnalyzerSQLException(errorResolver, e);
                throw new AppException(checkResult);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return user;
    }

    @Override
    public User updatePassword(User user) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE_PASSWORD)) {
                statement.setString(1, user.getPassword());
                statement.setInt(2, user.getId());
                if (statement.executeUpdate() == 0) {
                    user = null;
                }
                ;
            } catch (SQLException e) {
                logAndThrowForUnknowError(LOG, e);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(USERS_TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public User get(int id) {
        Connection con = pool.getConnection();
        User user = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = getUserWithLazyStaff(resultSet);
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
        return user;
    }

    @Override
    public User getByLogin(String login) {
        Connection con = pool.getConnection();
        User user = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = getUser(resultSet, login);
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
        return user;
    }

    @Override
    public List<User> getAll() {
        Connection con = pool.getConnection();
        List<User> resultList = new ArrayList<>();
        if (con != null) {
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    resultList.add(getUser(resultSet, null));
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

    private User getUser(ResultSet resultSet, String login) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int staff_id = resultSet.getInt(STAFF_ID_FIELDNAME);
        String password = resultSet.getString(PASSWORD_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String additional_name = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
        String surname = resultSet.getString(SURNAME_FIELDNAME);
        Role role = Role.valueOf(resultSet.getString(ROLE_FIELDNAME));
        Staff staff = new Staff(staff_id, name, additional_name, surname);
        if (login == null) {
            login = resultSet.getString(LOGIN_FIELDNAME);
        }
        return new User(id, staff, login, password, role);
    }

    private User getUserWithLazyStaff(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int staff_id = resultSet.getInt(STAFF_ID_FIELDNAME);
        Role role = Role.valueOf(resultSet.getString(ROLE_FIELDNAME));
        Staff staff = new Staff(staff_id, null, null, null);
        String login = resultSet.getString(LOGIN_FIELDNAME);
        return new User(id, staff, login, null, role);
    }

}
