package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientInspectionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientInspection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The class of jdbc operation for {@code PatientInspection} entity
 */

public class JdbcPatientInspectionJdbcImpl implements PatientInspectionDAO, CommonJdbcOperationsForBaseEntity<PatientInspection> {
    private static final Logger LOG = Logger.getLogger(JdbcPatientInspectionJdbcImpl.class);

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

    public JdbcPatientInspectionJdbcImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public PatientInspection create(PatientInspection patientInspection) {
        return create(pool, LOG, INSERT_INTO, patientInspection);
    }

    @Override
    public PatientInspection update(PatientInspection patientInspection) {
        return update(pool, LOG, UPDATE, patientInspection);
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public PatientInspection get(int id) {
        return get(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public List<PatientInspection> getAll(int patientId) {
        Integer[] intArgs = {patientId};
        return getAll(pool, SELECT_ALL, LOG, intArgs);
    }

    @Override
    public PatientInspection getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        LocalDate date = new Date(resultSet.getDate(DATE_FIELDNAME).getTime()).toLocalDate();
        String inspection = resultSet.getString(INSPECTION_FIELDNAME);
        String complaints = resultSet.getString(COMPLAINTS_FIELDNAME);
        return new PatientInspection(id, new Patient(patientId), date, inspection, complaints);
    }

    @Override
    public void setParametersForCreatingRecord(PreparedStatement statement,
                                               PatientInspection patientInspection) throws SQLException {
        statement.setInt(1, patientInspection.getPatient().getId());
        statement.setDate(2, Date.valueOf(patientInspection.getDate()));
        statement.setString(3, patientInspection.getInspection());
        statement.setString(4, patientInspection.getComplaints());
    }

    @Override
    public void setParametersForUpdatingRecord(PreparedStatement statement, PatientInspection patientInspection) throws SQLException {
        statement.setDate(1, Date.valueOf(patientInspection.getDate()));
        statement.setString(2, patientInspection.getInspection());
        statement.setString(3, patientInspection.getComplaints());
        statement.setInt(4, patientInspection.getId());

    }
}
