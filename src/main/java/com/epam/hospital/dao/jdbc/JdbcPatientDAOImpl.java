package com.epam.hospital.dao.jdbc;

import com.epam.hospital.model.Patient;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientDAO;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.exception.AppException.NO_DB_CONNECTION_ERROR;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class JdbcPatientDAOImpl implements PatientDAO {
    private static final Logger LOG = Logger.getLogger(JdbcPatientDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";
    private static final String ADDITIONAL_NAME_FIELDNAME = "additional_name";
    private static final String SURNAME_FIELDNAME = "surname";
    private static final String BIRTHDAY_FIELDNAME = "birthday";
    private static final String PHONE_FIELDNAME = "phone";
    private static final String EMAIL_FIELDNAME = "email";

    private static final String SELECT_ALL = "SELECT * FROM patient_register";
    private static final String INSERT_INTO = "INSERT INTO patient_register " +
            "(name,  additional_name,  surname, birthday, phone, email) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private final ConnectionPool pool;

    public JdbcPatientDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Patient create(Patient patient) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, patient.getName());
                    statement.setString(2, patient.getAdditionalName());
                    statement.setString(3, patient.getSurname());
                    statement.setDate(4, Date.valueOf(patient.getBirthday()));
                    statement.setString(5, patient.getPhone());
                    statement.setString(6, patient.getEmail());
                    statement.executeUpdate();
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        resultSet.next();
                        int id = resultSet.getInt(ID_FIELDNAME);
                        patient.setId(id);
                    }
            } catch (SQLException e) {
                patient = null;
                LOG.error(e.getMessage());
                throw new AppException(new CheckResult(UNKNOWN_ERROR));
            }
            pool.freeConnection(con);
        } else {
            LOG.error("There is no database connection.");
            throw new AppException(new CheckResult(NO_DB_CONNECTION_ERROR));
        }
        return patient;
    }

    @Override
    public Patient update(Patient user) {
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
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(ID_FIELDNAME);
                    String name = resultSet.getString(NAME_FIELDNAME);
                    String additionalName = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
                    String surname = resultSet.getString(SURNAME_FIELDNAME);
                    LocalDate birthDay = new Date(resultSet.getDate(BIRTHDAY_FIELDNAME).getTime()).toLocalDate();
                    String phone = resultSet.getString(PHONE_FIELDNAME);
                    String email = resultSet.getString(EMAIL_FIELDNAME);
                    resultList.add(new Patient(id, name, additionalName, surname, birthDay, phone, email));
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage());
                throw new AppException(new CheckResult(UNKNOWN_ERROR));
            }
            pool.freeConnection(con);
        } else {
            LOG.error("There is no database connection.");
            throw new AppException(new CheckResult(NO_DB_CONNECTION_ERROR));
        }
        return resultList;
    }

    @Override
    public boolean connectionPoolIsNull() {
        return pool == null;
    }
}
