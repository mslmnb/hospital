package com.epam.hospital.repository.jdbc;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.PatientRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcPatientRepositoryImpl implements PatientRepository {
    private static final String SELECT_ALL = "SELECT * FROM patient_register";
    private static final String INSERT_INTO = "INSERT INTO patient_register " +
            "(name,  additional_name,  surname, birth_day, phone, email) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private final ConnectionPool pool;

    public JdbcPatientRepositoryImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Patient save(Patient patient) {
        Connection con = pool.getConnection();
        if (con != null) {
            try {
                if (patient.isNew()) {
                    PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, patient.getName());
                    statement.setString(2, patient.getAdditionalName());
                    statement.setString(3, patient.getSurname());
                    statement.setDate(4, Date.valueOf(patient.getBirthday()));
                    statement.setString(5, patient.getPhone());
                    statement.setString(6, patient.getEmail());
                    int i = statement.executeUpdate();
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    patient.setId(resultSet.getInt("id"));
                } else {
                    // update
                }
            } catch (SQLException e) {
                patient = null;
                // logger
            }

            pool.freeConnection(con);
        }
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
                    LocalDate birthDay = new Date(resultSet.getDate("birth_day").getTime()).toLocalDate();
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
        return pool == null;
    }
}
