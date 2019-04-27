package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.CommonDaoOperationsForBaseEntityWithLazyInitialization;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientDiagnosisDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.handbk.Diagnosis;
import com.epam.hospital.model.handbk.DiagnosisType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class JdbcPatientDiagnosisDAOImpl implements PatientDiagnosisDAO,
                                             CommonDaoOperationsForBaseEntityWithLazyInitialization<PatientDiagnosis> {
    private static final Logger LOG = Logger.getLogger(JdbcPatientDiagnosisDAOImpl.class);

    private static final String PATIENT_ID_FIELDNAME = "patient_id";
    private static final String DATE_FIELDNAME = "date";
    private static final String DIAGNOSIS_ITEM_ID_FIELDNAME = "diagnosis_item_id";
    private static final String DIAGNOSIS_TYPE_ITEM_ID_FIELDNAME = "diagnosis_type_item_id";
    private static final String DIAGNOSIS_FIELDNAME = "diagnosis";
    private static final String DIAGNOSIS_TYPE_FIELDNAME = "diagnosis_type";
    private static final String DIAGNOSIS_TYPE_ID_FIELDNAME = "diagnosis_type_id";
    private static final String DIAGNOSIS_ID_FIELDNAME = "diagnosis_id";

    private static final String SELECT_ALL = "SELECT dr.id, dr.patient_id, dr.date, " +
                                                 "dr.diagnosis_item_id as diagnosis_id, " +
                                                 "dr.diagnosis_type_item_id AS diagnosis_type_id, " +
                                                 "hit.translation as diagnosis, " +
                                                 "hit_type.translation AS diagnosis_type " +
                                             "FROM diagnosis_register AS dr, handbk_items as hi, " +
                                                 "handbk_item_translations as hit, " +
                                                 "handbk_items as hi_type, handbk_item_translations as hit_type " +
                                             "WHERE dr.diagnosis_item_id = hi.id AND " +
                                                 "dr.diagnosis_type_item_id =  hi_type.id  AND " +
                                                 "hi.id = hit.handbk_item_id AND " +
                                                 "hi_type.id = hit_type.handbk_item_id AND " +
                                                 "hit.locale = ? AND  hit_type.locale = ? AND patient_id = ? " +
                                             "ORDER BY dr.id";
    private static final String SELECT_BY_ID = "SELECT * FROM diagnosis_register WHERE id = ? ";
    private static final String INSERT_INTO = "INSERT INTO diagnosis_register" +
                                                  "(patient_id,  date, diagnosis_item_id, diagnosis_type_item_id) " +
                                              "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE diagnosis_register SET date = ?, diagnosis_item_id = ?, " +
                                             "diagnosis_type_item_id = ? " +
                                         "WHERE id = ?";
    private static final String TABLE_NAME = "diagnosis_register";

    private final ConnectionPool pool;

    public JdbcPatientDiagnosisDAOImpl(ConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public PatientDiagnosis create(PatientDiagnosis patientDiagnosis) {
        return create(pool, LOG, INSERT_INTO, patientDiagnosis);
    }

    @Override
    public PatientDiagnosis update(PatientDiagnosis patientDiagnosis) {
        return update(pool, LOG, UPDATE, patientDiagnosis);
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public PatientDiagnosis get(int id) {
        return getWithLazyInitialization(pool, SELECT_BY_ID, LOG, id);
    }

    @Override
    public List<PatientDiagnosis> getAll(int patientId, String locale) {
        String[] strArgs = {locale, locale};
        Integer[] intArgs = {patientId};
        return getAll(pool, SELECT_ALL, LOG, strArgs, intArgs);
    }

    @Override
    public PatientDiagnosis getObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        LocalDate date = new Date(resultSet.getDate(DATE_FIELDNAME).getTime()).toLocalDate();
        Integer diagnosisTypeId = resultSet.getInt(DIAGNOSIS_TYPE_ID_FIELDNAME);
        String diagnosisTypeName = resultSet.getString(DIAGNOSIS_TYPE_FIELDNAME);
        DiagnosisType diagnosisType = new DiagnosisType(diagnosisTypeId, diagnosisTypeName);
        Integer diagnosisId = resultSet.getInt(DIAGNOSIS_ID_FIELDNAME);
        String diagnosisName = resultSet.getString(DIAGNOSIS_FIELDNAME);
        Diagnosis diagnosis = new Diagnosis(diagnosisId, diagnosisName);

        return new PatientDiagnosis(id, new Patient(patientId), date, diagnosisType, diagnosis);
    }

    @Override
    public PatientDiagnosis getLazyObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        LocalDate date = new Date(resultSet.getDate(DATE_FIELDNAME).getTime()).toLocalDate();
        Integer diagnosisTypeId = resultSet.getInt(DIAGNOSIS_TYPE_ITEM_ID_FIELDNAME);
        DiagnosisType diagnosisType = new DiagnosisType(diagnosisTypeId);
        Integer diagnosisId = resultSet.getInt(DIAGNOSIS_ITEM_ID_FIELDNAME);
        Diagnosis diagnosis = new Diagnosis(diagnosisId);

        return new PatientDiagnosis(id, new Patient(patientId), date, diagnosisType, diagnosis);
    }

    @Override
    public void setParametersForCreatingObject(PreparedStatement statement,
                                               PatientDiagnosis patientDiagnosis) throws SQLException {
        statement.setInt(1, patientDiagnosis.getPatient().getId());
        statement.setDate(2, Date.valueOf(patientDiagnosis.getDate()));
        statement.setInt(3, patientDiagnosis.getDiagnosis().getId());
        statement.setInt(4, patientDiagnosis.getDiagnosisType().getId());
    }

    @Override
    public void setParametersForUpdatingObject(PreparedStatement statement,
                                               PatientDiagnosis patientDiagnosis) throws SQLException {
        statement.setDate(1, Date.valueOf(patientDiagnosis.getDate()));
        statement.setInt(2, patientDiagnosis.getDiagnosis().getId());
        statement.setInt(3, patientDiagnosis.getDiagnosisType().getId());
        statement.setInt(4, patientDiagnosis.getId());
    }
}
