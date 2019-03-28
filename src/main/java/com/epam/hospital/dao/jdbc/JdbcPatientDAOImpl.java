package com.epam.hospital.dao.jdbc;

import com.epam.hospital.model.Patient;
import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.PatientDAO;
import com.epam.hospital.model.handbk.Diagnosis;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.util.DaoUtil.deleteFromTable;
import static com.epam.hospital.util.DaoUtil.logAndThrowForNoDbConnectionError;
import static com.epam.hospital.util.DaoUtil.logAndThrowForSQLException;

public class JdbcPatientDAOImpl implements PatientDAO {
    private static final Logger LOG = Logger.getLogger(JdbcPatientDAOImpl.class);

    private static final String ID_FIELDNAME = "id";
    private static final String NAME_FIELDNAME = "name";
    private static final String ADDITIONAL_NAME_FIELDNAME = "additional_name";
    private static final String SURNAME_FIELDNAME = "surname";
    private static final String BIRTHDAY_FIELDNAME = "birthday";
    private static final String PHONE_FIELDNAME = "phone";
    private static final String EMAIL_FIELDNAME = "email";
    private static final String ADMISSION_DATE_FIELDNAME = "admission_date";
    private static final String DISCHARGE_DATE_FIELDNAME = "discharge_date";
    private static final String FINAL_DIAGNOSIS_ID_FIELDNAME = "final_diagnosis_item_id";
    private static final String PRIMARY_INSPECTION_FIELDNAME = "primary_inspection";
    private static final String PRIMARY_COMPLAINTS_FIELDNAME = "primary_complaints";
    private static final String PRIMARY_DIAGNOSIS_ID_FIELDNAME = "primary_diagnosis_item_id";

    private static final String FOREIGN_KEY_IN_DIAGNOSISES = "diagnosis_register_patient_id_fkey";
    private static final String FOREIGN_KEY_IN_INSPECTIONS = "inspection_register_patient_id_fkey";
    private static final String FOREIGN_KEY_IN_PRESCRIPTIONS = "prescription_register_patient_id_fkey";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_DIAGNOSISES = "impossibleRemovingForDiagnosises";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_INSPECTIONS = "impossibleRemovingForInspections";
    private static final String IMPOSSIBLE_REMOVING_ERROR_FOR_PRESCRIPTIONS = "impossibleRemovingForPrescriptions";

    private static final Map<String, String> errorResolver;

    private static final String SELECT_ALL = "SELECT * FROM patient_register ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT * FROM patient_register " +
                                               "WHERE id = ? ";
    private static final String INSERT_INTO = "INSERT INTO patient_register " +
                                                  "(name,  additional_name,  surname, birthday, " +
                                                        "phone, email, admission_date)" +
                                              "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE patient_register " +
                                         "SET name = ?, additional_name = ?, surname = ?, birthday = ?, " +
                                             "phone = ?, email = ?, admission_date = ? " +
                                         "WHERE id = ?";
    private static final String UPDATE_DIAGNOSIS = "UPDATE patient_register " +
                                                   "SET primary_inspection = ?, primary_complaints = ?, " +
                                                       "primary_diagnosis_item_id = ?, " +
                                                       "final_diagnosis_item_id = ?, discharge_date = ? " +
                                                   "WHERE id = ?";
    private static final String TABLE_NAME = "patient_register";

    static {
        errorResolver = new HashMap<>();
        errorResolver.put(FOREIGN_KEY_IN_DIAGNOSISES, IMPOSSIBLE_REMOVING_ERROR_FOR_DIAGNOSISES);
        errorResolver.put(FOREIGN_KEY_IN_INSPECTIONS, IMPOSSIBLE_REMOVING_ERROR_FOR_INSPECTIONS);
        errorResolver.put(FOREIGN_KEY_IN_PRESCRIPTIONS, IMPOSSIBLE_REMOVING_ERROR_FOR_PRESCRIPTIONS);
    }

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
                statement.setDate(7, Date.valueOf(patient.getAdmissionDate()));
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    int id = resultSet.getInt(ID_FIELDNAME);
                    patient.setId(id);
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patient;
    }

    @Override
    public Patient update(Patient patient) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
                statement.setString(1, patient.getName());
                statement.setString(2, patient.getAdditionalName());
                statement.setString(3, patient.getSurname());
                statement.setDate(4, Date.valueOf(patient.getBirthday()));
                statement.setString(5, patient.getPhone());
                statement.setString(6, patient.getEmail());
                statement.setDate(7, Date.valueOf(patient.getAdmissionDate()));
                statement.setInt(8, patient.getId());
                if (statement.executeUpdate() == 0) {
                    patient = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patient;
    }

    @Override
    public Patient updatePrimaryExamAndDischarge(Patient patient) {
        Connection con = pool.getConnection();
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(UPDATE_DIAGNOSIS)) {
                statement.setString(1, patient.getPrimaryInspection());
                statement.setString(2, patient.getPrimaryComplaints());
                if (patient.getPrimaryDiagnosis() != null) {
                    statement.setInt(3, patient.getPrimaryDiagnosis().getId());
                } else {
                    statement.setNull(3, Types.INTEGER);
                }
                if (patient.getFinalDiagnosis() != null) {
                    statement.setInt(4, patient.getFinalDiagnosis().getId());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                if (patient.getDischargeDate() != null) {
                    statement.setDate(5, Date.valueOf(patient.getDischargeDate()));
                } else {
                    statement.setNull(5, Types.DATE);
                }
                statement.setInt(6, patient.getId());
                if (statement.executeUpdate() == 0) {
                    patient = null;
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patient;
    }

    @Override
    public boolean delete(int id) {
        return deleteFromTable(TABLE_NAME, LOG, pool, id, errorResolver);
    }

    @Override
    public Patient get(int id) {
        Connection con = pool.getConnection();
        Patient patient = null;
        if (con != null) {
            try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        patient = getPatient(resultSet);
                    }
                }
            } catch (SQLException e) {
                logAndThrowForSQLException(e, LOG);
            }
            pool.freeConnection(con);
        } else {
            logAndThrowForNoDbConnectionError(LOG);
        }
        return patient;
    }

    @Override
    public List<Patient> getAll() {
        Connection con = pool.getConnection();
        List<Patient> results = new ArrayList<>();
        if (con != null) {
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    results.add(getPatient(resultSet));
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

    private Patient getPatientWithLazyFinalDiagnosis(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_FIELDNAME);
        String name = resultSet.getString(NAME_FIELDNAME);
        String additionalName = resultSet.getString(ADDITIONAL_NAME_FIELDNAME);
        String surname = resultSet.getString(SURNAME_FIELDNAME);
        LocalDate birthDay = new Date(resultSet.getDate(BIRTHDAY_FIELDNAME).getTime()).toLocalDate();
        String phone = resultSet.getString(PHONE_FIELDNAME);
        String email = resultSet.getString(EMAIL_FIELDNAME);
        LocalDate admissionDate = new Date(resultSet.getDate(ADMISSION_DATE_FIELDNAME).getTime()).toLocalDate();
        Date dischargeDateAsSqlDate = resultSet.getDate(DISCHARGE_DATE_FIELDNAME);
        LocalDate dischargeDate = (dischargeDateAsSqlDate == null)
                                  ? null
                                  : new Date(dischargeDateAsSqlDate.getTime()).toLocalDate();
        return new Patient(id, name, additionalName, surname, birthDay, phone, email, admissionDate, dischargeDate);
    }

    private Patient getPatient(ResultSet resultSet) throws SQLException {
        Patient patient = getPatientWithLazyFinalDiagnosis(resultSet);
        Integer finalDiagnosisId = resultSet.getInt(FINAL_DIAGNOSIS_ID_FIELDNAME);
        Diagnosis finalDiagnosis = (finalDiagnosisId == 0) ? null : new Diagnosis(finalDiagnosisId);
        Integer primaryDiagnosisId = resultSet.getInt(PRIMARY_DIAGNOSIS_ID_FIELDNAME);
        Diagnosis primaryDiagnosis = (primaryDiagnosisId == 0) ? null : new Diagnosis(primaryDiagnosisId);
        String primaryInspection = resultSet.getString(PRIMARY_INSPECTION_FIELDNAME);
        String primaryComplaints = resultSet.getString(PRIMARY_COMPLAINTS_FIELDNAME);
        Date date = resultSet.getDate(DISCHARGE_DATE_FIELDNAME);
        LocalDate dischargeDate = (date == null) ? null : new Date(date.getTime()).toLocalDate();
        patient.setFinalDiagnosis(finalDiagnosis);
        patient.setPrimaryDiagnosis(primaryDiagnosis);
        patient.setPrimaryComplaints(primaryComplaints);
        patient.setPrimaryInspection(primaryInspection);
        patient.setDischargeDate(dischargeDate);
        return patient;
    }

}
