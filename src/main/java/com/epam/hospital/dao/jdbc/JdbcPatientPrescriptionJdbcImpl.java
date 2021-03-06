package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientPrescriptionDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientPrescription;
import com.epam.hospital.model.handbk.PrescriptionType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The class of jdbc operation for {@code PatientPrescription} entity
 */

public class JdbcPatientPrescriptionJdbcImpl implements PatientPrescriptionDAO,
        CommonJdbcOperationsForBaseEntityWithLazyInitialization<PatientPrescription> {
    private static final Logger LOG = Logger.getLogger(JdbcPatientPrescriptionJdbcImpl.class);

    private static final String PATIENT_ID_FIELDNAME = "patient_id";
    private static final String PRESCRPTN_TYPE_ID_FIELDNAME = "prescrptn_type_id";
    private static final String PRESCRPTN_TYPE_FIELDNAME = "prescrptn_type";
    private static final String APPLICATION_DATE_FIELDNAME = "application_date";
    private static final String DESCRIPTION_FIELDNAME = "description";
    private static final String EXECUTION_DATE_FIELDNAME = "execution_date";
    private static final String RESULT_FIELDNAME = "result";

    private static final String SELECT_ALL = "SELECT pr.id, pr.patient_id, " +
                                                 "pr.prescrptn_type_item_id AS prescrptn_type_id, " +
                                                 "pr.application_date, pr.description, pr.execution_date, " +
                                                 "pr.result, hit_type.translation AS prescrptn_type " +
                                             "FROM prescription_register AS pr, " +
                                                 "handbk_items as hi_type, handbk_item_translations as hit_type " +
                                             "WHERE pr.prescrptn_type_item_id =  hi_type.id  AND " +
                                                 "hi_type.id = hit_type.handbk_item_id AND " +
                                                 "hit_type.locale = ? AND patient_id = ? " +
                                             "ORDER BY pr.id";
    private static final String SELECT_BY_ID = "SELECT id, patient_id, prescrptn_type_item_id AS prescrptn_type_id, " +
                                                   "application_date, description, execution_date, result " +
                                                "FROM prescription_register WHERE id = ? ";
    private static final String INSERT_INTO = "INSERT INTO prescription_register " +
                                                  "(patient_id,  application_date, " +
                                                      "prescrptn_type_item_id, description) " +
                                              "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE prescription_register " +
                                         "SET application_date = ?, prescrptn_type_item_id = ?, description = ?, " +
                                             "execution_date = ?, result = ? " +
                                         "WHERE id = ?";
    private static final String TABLE_NAME = "prescription_register";

    private final ConnectionPool pool;

    public JdbcPatientPrescriptionJdbcImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public PatientPrescription create(PatientPrescription patientPrescription) {
        return create(pool, LOG, INSERT_INTO, patientPrescription);
    }

    @Override
    public PatientPrescription update(PatientPrescription patientPrescription) {
        return update(pool, LOG, UPDATE, patientPrescription);
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public PatientPrescription get(int id) {
        return getWithLazyInitialization(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public List<PatientPrescription> getAll(String locale, int patientId) {
        String[] strArgs = {locale};
        Integer[] intArgs = {patientId};
        return getAll(pool, SELECT_ALL, LOG, strArgs, intArgs);
    }

    @Override
    public PatientPrescription getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        int prescrptnTypeId = resultSet.getInt(PRESCRPTN_TYPE_ID_FIELDNAME);
        String prescrptnTypeName = resultSet.getString(PRESCRPTN_TYPE_FIELDNAME);
        PrescriptionType prescrptnType = new PrescriptionType(prescrptnTypeId, prescrptnTypeName);
        LocalDate applicationDate = new Date(resultSet.getDate(APPLICATION_DATE_FIELDNAME).getTime()).toLocalDate();
        String description = resultSet.getString(DESCRIPTION_FIELDNAME);
        Date date = resultSet.getDate(EXECUTION_DATE_FIELDNAME);
        LocalDate executionDate = (date == null) ? null : new Date(date.getTime()).toLocalDate();
        String result = resultSet.getString(RESULT_FIELDNAME);
        return new PatientPrescription(id, new Patient(patientId), applicationDate,
                prescrptnType, description, executionDate, result);
    }

    @Override
    public PatientPrescription getLazyObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        int prescrptnTypeId = resultSet.getInt(PRESCRPTN_TYPE_ID_FIELDNAME);
        PrescriptionType prescrptnType = new PrescriptionType(prescrptnTypeId);
        LocalDate applicationDate = new Date(resultSet.getDate(APPLICATION_DATE_FIELDNAME).getTime()).toLocalDate();
        String description = resultSet.getString(DESCRIPTION_FIELDNAME);
        Date date = resultSet.getDate(EXECUTION_DATE_FIELDNAME);
        LocalDate executionDate = (date == null) ? null : new Date(date.getTime()).toLocalDate();
        String result = resultSet.getString(RESULT_FIELDNAME);
        return new PatientPrescription(id, new Patient(patientId), applicationDate,
                prescrptnType, description, executionDate, result);
    }

    @Override
    public void setParametersForCreatingRecord(PreparedStatement statement,
                                               PatientPrescription patientPrescription) throws SQLException {
        statement.setInt(1, patientPrescription.getPatient().getId());
        statement.setDate(2, Date.valueOf(patientPrescription.getApplicationDate()));
        statement.setInt(3, patientPrescription.getType().getId());
        statement.setString(4, patientPrescription.getDescription());
    }

    @Override
    public void setParametersForUpdatingRecord(PreparedStatement statement,
                                               PatientPrescription patientPrescription) throws SQLException {
        statement.setDate(1, Date.valueOf(patientPrescription.getApplicationDate()));
        statement.setInt(2, patientPrescription.getType().getId());
        statement.setString(3, patientPrescription.getDescription());
        Date executionDate = patientPrescription.getExecutionDate() == null
                ? null : Date.valueOf(patientPrescription.getExecutionDate());
        statement.setDate(4, executionDate);
        statement.setString(5, patientPrescription.getResult());
        statement.setInt(6, patientPrescription.getId());
    }
}
