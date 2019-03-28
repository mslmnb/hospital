package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientInspectionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientInspection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.deleteFromTable;
import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public class JdbcPatientInspectionDAOImpl implements PatientInspectionDAO {
    private static final Logger LOG = Logger.getLogger(JdbcPatientInspectionDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String PATIENT_ID_FIELDNAME = "patient_id";
    private static final String DATE_FIELDNAME = "date";
    private static final String INSPECTION_FIELDNAME = "inspection";
    private static final String COMPLAINTS_FIELDNAME = "complaints";

    private static final String SELECT_ALL = "SELECT * FROM inspection_register WHERE patient_id = ? ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT * FROM inspection_register WHERE id = ? ";
    private static final String INSERT_INTO = "INSERT INTO inspection_register " +
                                                  "(patient_id,  date, inspection, complaints) " +
                                              "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE inspection_register " +
                                         "SET date = ?, inspection = ?, complaints = ? " +
                                         "WHERE id = ?";
    private static final String TABLE_NAME = "inspection_register";

    private final ConnectionPool pool;

    public JdbcPatientInspectionDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public PatientInspection create(PatientInspection patientInspection) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, patientInspection.getPatient().getId());
                statement.setDate(2, Date.valueOf(patientInspection.getDate()));
                statement.setString(3, patientInspection.getInspection());
                statement.setString(4, patientInspection.getComplaints());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    patientInspection.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patientInspection;
    }

    @Override
    public PatientInspection update(PatientInspection patientInspection) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setDate(1, Date.valueOf(patientInspection.getDate()));
                statement.setString(2, patientInspection.getInspection());
                statement.setString(3, patientInspection.getComplaints());
                statement.setInt(4, patientInspection.getId());
                if (statement.executeUpdate() == 0) {
                    patientInspection = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patientInspection;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public PatientInspection get(int id) {
        Connection con = pool.getConnection();
        PatientInspection patientInspection = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        patientInspection = getPatientInspection(resultSet);
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patientInspection;
    }

    @Override
    public List<PatientInspection> getAll(int patientId) {
        Connection con = pool.getConnection();
        List<PatientInspection> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setInt(1, patientId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getPatientInspection(resultSet));
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

    private PatientInspection getPatientInspection(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        LocalDate date = new Date(resultSet.getDate(DATE_FIELDNAME).getTime()).toLocalDate();
        String inspection = resultSet.getString(INSPECTION_FIELDNAME);
        String complaints = resultSet.getString(COMPLAINTS_FIELDNAME);
        return new PatientInspection(id, new Patient(patientId), date, inspection, complaints);
    }
}
