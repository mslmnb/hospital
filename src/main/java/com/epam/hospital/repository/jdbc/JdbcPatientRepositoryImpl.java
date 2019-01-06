package com.epam.hospital.repository.jdbc;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.to.PatientTo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcPatientRepositoryImpl implements PatientRepository {
    private final ConnectionPool pool = ConnectionPool.getInstance(
            "org.postgresql.Driver",
            "jdbc:postgresql://127.0.0.1:5432/hospital",
            "user", "password", 5);

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
                String sql = "SELECT * FROM patient_register";
                ResultSet resultSet = statement.executeQuery(sql);
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
}
