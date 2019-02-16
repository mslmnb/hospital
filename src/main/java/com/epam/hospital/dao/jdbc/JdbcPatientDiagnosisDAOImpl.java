package com.epam.hospital.dao.jdbc;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientDiagnosisDAO;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.handbk.Diagnosis;
import com.epam.hospital.model.handbk.DiagnosisType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.util.DaoUtil.deleteFromTable;
import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public class JdbcPatientDiagnosisDAOImpl implements PatientDiagnosisDAO {
    private static final Logger LOG = Logger.getLogger(JdbcPatientDiagnosisDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
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
                                                 "hit.locale = ? AND  hit_type.locale = ? AND patient_id = ?";
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
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, patientDiagnosis.getPatient().getId());
                statement.setDate(2, Date.valueOf(patientDiagnosis.getDate()));
                statement.setInt(3, patientDiagnosis.getDiagnosis().getId());
                statement.setInt(4, patientDiagnosis.getDiagnosisType().getId());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    patientDiagnosis.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patientDiagnosis;
    }

    @Override
    public PatientDiagnosis update(PatientDiagnosis patientDiagnosis) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setDate(1, Date.valueOf(patientDiagnosis.getDate()));
                statement.setInt(2, patientDiagnosis.getDiagnosis().getId());
                statement.setInt(3, patientDiagnosis.getDiagnosisType().getId());
                statement.setInt(4, patientDiagnosis.getId());
                if (statement.executeUpdate()==0) {
                    patientDiagnosis = null;
                };
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);        }
        return patientDiagnosis;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id);
    }

    @Override
    public PatientDiagnosis get(int id) {
        Connection con = pool.getConnection();
        PatientDiagnosis patientDiagnosis = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        patientDiagnosis = getPatientDiagnssWithLazyDiagnssAndDiagnssType(resultSet);
                        break;
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patientDiagnosis;
    }

    @Override
    public List<PatientDiagnosis> getAll(int patientId, String locale) {
        Connection con = pool.getConnection();
        List<PatientDiagnosis> results = new ArrayList<>();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
                statement.setString(1, locale);
                statement.setString(2, locale);
                statement.setInt(3, patientId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(getPatientDiagnosis(resultSet));
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

    private PatientDiagnosis getPatientDiagnssWithLazyDiagnssAndDiagnssType(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        int patientId = resultSet.getInt(PATIENT_ID_FIELDNAME);
        LocalDate date = new Date(resultSet.getDate(DATE_FIELDNAME).getTime()).toLocalDate();
        Integer diagnosisTypeId = resultSet.getInt(DIAGNOSIS_TYPE_ITEM_ID_FIELDNAME);
        DiagnosisType diagnosisType = new DiagnosisType(diagnosisTypeId);
        Integer diagnosisId = resultSet.getInt(DIAGNOSIS_ITEM_ID_FIELDNAME);
        Diagnosis diagnosis = new Diagnosis(diagnosisId);

        return new PatientDiagnosis(id, new Patient(patientId), date, diagnosisType, diagnosis);
    }

    private PatientDiagnosis getPatientDiagnosis(ResultSet resultSet) throws SQLException {
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

}