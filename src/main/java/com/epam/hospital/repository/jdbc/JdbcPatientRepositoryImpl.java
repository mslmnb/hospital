package com.epam.hospital.repository.jdbc;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.PatientRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcPatientRepositoryImpl implements PatientRepository {
    private static final String SELECT_ALL = "SELECT * FROM patient_register";

    private final ConnectionPool pool;

    public JdbcPatientRepositoryImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Patient save(Patient user) {
        Connection con = pool.getConnection();


        pool.freeConnection(con);
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Patient get(int id) {
        return null;
    }

    @Override
    public List<Patient> getAll() {
        Connection con = pool.getConnection();
        List<Patient> resultList = new ArrayList<>();
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_ALL);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String additionalName = resultSet.getString("additional_name");
                    String surname = resultSet.getString("surname");
                    LocalDate birthDay = new java.sql.Date(resultSet.getDate("birth_day").getTime()).toLocalDate();
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    resultList.add(new Patient(id, name, additionalName, surname, birthDay, phone, email));
                }

            } catch (SQLException e) {
                e.printStackTrace();
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
