package com.epam.hospital.repository.jdbc;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcUserRepositoryImpl implements UserRepository{
    private static final String SELECT_BY_LOGIN_WITHOUT_ROLES = "SELECT users.staff_id, users.password, " +
                                                                       "users.registered, users.enabled, staff.name, " +
                                                                       "staff.additional_name, staff.surname " +
                                                                   "FROM users "  +
                                                                   "LEFT JOIN staff ON users.staff_id = staff.id " +
                                                                   "WHERE users.login = ? ";
    private static final String SELECT_ALL_WITHOUT_ROLES = "SELECT users.*, user_roles.role FROM users ";
    private static final String SELECT_ROLES = "SELECT role FROM user_roles WHERE staff_id = ?";

    private final ConnectionPool pool;

    public JdbcUserRepositoryImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int staff_id) {
        return false;
    }

    @Override
    public User get(int staff_id) {
        return null;
    }

    @Override
    public User getByLoginWithoutRoles(String login) {
        Connection con = pool.getConnection();
        User user = null;
        if (con != null) {
            try {
                PreparedStatement statement = con.prepareStatement(SELECT_BY_LOGIN_WITHOUT_ROLES);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                        int staff_id = resultSet.getInt("staff_id");
                        String password = resultSet.getString("password");
                        LocalDate registered = new java.sql.Date(resultSet.getDate("registered").getTime()).toLocalDate();
                        boolean enabled = resultSet.getBoolean("enabled");
                        String name = resultSet.getString("name");
                        String additional_name = resultSet.getString("additional_name");
                        String surname = resultSet.getString("surname");
                        Staff staff = new Staff(staff_id, name, additional_name, surname);
                        user = new User(staff, login, password, enabled, registered);
                        break;
                }
            } catch (SQLException e) {
                int i = 0;
                // logger
            }
            pool.freeConnection(con);
        }
        return user;
    }

    @Override
    public Set<Role> getRoles(int staff_id) {
        Connection con = pool.getConnection();
        Set<Role> roles = new HashSet<>();
        try {
            PreparedStatement statement = con.prepareStatement(SELECT_ROLES);
            statement.setInt(1, staff_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                roles.add(role);
            }
        } catch (SQLException e) {
            int i = 0;
            // logger
        }
        return roles;
    }

    @Override
    public List<User> getAllWithoutRoles() {
        Connection con = pool.getConnection();
        List<User> resultList = new ArrayList<>();
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_ALL_WITHOUT_ROLES);
                while (resultSet.next()) {
                    int staff_id = resultSet.getInt("staff_id");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    LocalDate registered = new java.sql.Date(resultSet.getDate("registered").getTime()).toLocalDate();
                    boolean enabled = resultSet.getBoolean("enabled");
                    resultList.add(new User(new Staff(staff_id), login, password, enabled, registered));
                }
            } catch (SQLException e) {
                //logger,
            }
            pool.freeConnection(con);
        }

        return resultList;
    }

    @Override
    public boolean connectionPoolIsNull() {
        return pool==null;
    }
}
