package com.epam.hospital.repository.jdbc;

import com.epam.hospital.model.Role;
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
    private static final String SELECT_BY_LOGIN = "SELECT users.*, user_roles.role FROM users " +
                                                     "LEFT JOIN user_roles ON users.staff_id = user_roles.staff_id " +
                                                      "WHERE login = ?";
    private static final String SELECT_ALL = "SELECT users.*, user_roles.role FROM users ";

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
    public User getByLogin(String login) {
        Connection con = pool.getConnection();
        User user = null;
        if (con != null) {
            try {
                PreparedStatement statement = con.prepareStatement(SELECT_BY_LOGIN);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                Set<Role> roles = new HashSet<>();
                while (resultSet.next()) {
                    if (user == null) {
                        int staff_id = resultSet.getInt("staff_id");
                        String password = resultSet.getString("password");
                        LocalDate registered = new java.sql.Date(resultSet.getDate("registered").getTime()).toLocalDate();
                        boolean enabled = resultSet.getBoolean("enabled");
                        user = new User(staff_id, login, password, enabled, registered);
                    }
                    Role role = Role.valueOf(resultSet.getString("role"));
                    roles.add(role);
                }
                if (user!=null) {
                    user.setRoles(roles);
                }
            } catch (SQLException e) {
                // logger
                user = null;
            }
            pool.freeConnection(con);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Connection con = pool.getConnection();
        List<User> resultList = new ArrayList<>();
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_ALL);
                while (resultSet.next()) {
                    int staff_id = resultSet.getInt("staff_id");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    LocalDate registered = new java.sql.Date(resultSet.getDate("registered").getTime()).toLocalDate();
                    boolean enabled = resultSet.getBoolean("enabled");
                    // добавить роли???
                    resultList.add(new User(staff_id, login, password, enabled, registered));
                }
            } catch (SQLException e) {
                //nothing
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
