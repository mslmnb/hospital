package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.StaffDAO;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.model.handbk.Position;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcStaffDAOImpl implements StaffDAO {
    private static final Logger LOG = Logger.getLogger(JdbcStaffDAOImpl.class);

    private static final String SELECT_ALL = "SELECT staff.id, staff.name, " +
            "staff.additional_name, staff.surname, " +
            "staff.position_item_id FROM staff";

    private final ConnectionPool pool;

    public JdbcStaffDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Staff create(Staff staff) {
        return null;
    }

    @Override
    public Staff update(User staff) {
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
    public List<Staff> getAll() {
        Connection con = pool.getConnection();
        List<Staff> resultList = new ArrayList<>();
        if (con != null) {
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    resultList.add(getStaff(resultSet));
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
            pool.freeConnection(con);
        } else {
            LOG.error("There is no database connection.");
        }
        return resultList;
    }

    private Staff getStaff(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String additional_name = resultSet.getString("additional_name");
        String surname = resultSet.getString("surname");
        Position position = new Position(resultSet.getInt("position_item_id"), null);
        return new Staff(id, name, additional_name, surname, position);
    }
}

