package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PrescriptionDao extends AbstractDao<Prescription> {
    private static final String SQL_FIND_BY_CUSTOMER_ID = "SELECT prescription_id, customers.name AS customer_name, customers.surname AS customer_surname, customers.email AS customer_email, customer_roles.role AS customer_role, customers.amount AS customer_amount,   doctors.user_id AS doctor_id, doctors.name AS doctor_name, doctors.surname AS doctor_surname, doctors.email AS doctor_email, doctor_roles.role AS doctor_role, drug_id, drug_name, drug_amount, description, need_prescription, dosage, price , prescriptions.amount, issue_date, end_date, prescription_status FROM webdb.prescriptions LEFT JOIN webdb.users AS customers ON customer_id=customers.user_id LEFT JOIN webdb.users AS doctors ON doctor_id=doctors.user_id LEFT JOIN webdb.roles AS customer_roles ON customers.role_id=customer_roles.role_id LEFT JOIN webdb.roles AS doctor_roles ON doctors.role_id=doctor_roles.role_id LEFT JOIN webdb.drugs ON prescription_drug_id=drug_id LEFT JOIN webdb.prescription_statuses ON prescriptions.prescription_status_id=prescription_statuses.prescription_status_id WHERE customer_id=?";
    private static final String SQL_FIND_BY_DOCTOR_ID_AND_STATUS_ID = "SELECT prescription_id,customers.user_id AS customer_id, customers.name AS customer_name, customers.surname AS customer_surname, customers.email AS customer_email, customer_roles.role AS customer_role, customers.amount AS customer_amount, doctors.name AS doctor_name, doctors.surname AS doctor_surname, doctors.email AS doctor_email, doctor_roles.role AS doctor_role, drug_id, drug_name, drug_amount, description, need_prescription, dosage, price , prescriptions.amount, issue_date, end_date, prescription_status FROM webdb.prescriptions LEFT JOIN webdb.users AS customers ON customer_id=customers.user_id LEFT JOIN webdb.users AS doctors ON doctor_id=doctors.user_id LEFT JOIN webdb.roles AS customer_roles ON customers.role_id=customer_roles.role_id LEFT JOIN webdb.roles AS doctor_roles ON doctors.role_id=doctor_roles.role_id LEFT JOIN webdb.drugs ON prescription_drug_id=drug_id LEFT JOIN webdb.prescription_statuses ON prescriptions.prescription_status_id=prescription_statuses.prescription_status_id WHERE doctor_id=? AND prescriptions.prescription_status_id=?";
    private static final String SQL_FIND_BY_ID = "SELECT prescription_id,customers.user_id AS customer_id, customers.name AS customer_name, customers.surname AS customer_surname, customers.email AS customer_email, customer_roles.role AS customer_role, customers.amount AS customer_amount,   doctors.user_id AS doctor_id, doctors.name AS doctor_name, doctors.surname AS doctor_surname, doctors.email AS doctor_email, doctor_roles.role AS doctor_role, drug_id, drug_name, drug_amount, description, need_prescription, dosage, price , prescriptions.amount, issue_date, end_date, prescription_status FROM webdb.prescriptions LEFT JOIN webdb.users AS customers ON customer_id=customers.user_id LEFT JOIN webdb.users AS doctors ON doctor_id=doctors.user_id LEFT JOIN webdb.roles AS customer_roles ON customers.role_id=customer_roles.role_id LEFT JOIN webdb.roles AS doctor_roles ON doctors.role_id=doctor_roles.role_id LEFT JOIN webdb.drugs ON prescription_drug_id=drug_id LEFT JOIN webdb.prescription_statuses ON prescriptions.prescription_status_id=prescription_statuses.prescription_status_id WHERE prescription_id=?";
    private static final String SQL_EXIST_PRESCRIPTION_BY_DRUG_ID_AND_CUSTOMER_ID = "SELECT EXISTS (SELECT * FROM webdb.prescriptions WHERE prescription_drug_id = ? AND customer_id=?) AS exist";
    private static final String SQL_ADD_PRESCRIPTION = "INSERT INTO webdb.prescriptions(customer_id, doctor_id, prescription_drug_id, amount, issue_date, end_date, prescription_status_id) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE webdb.prescriptions SET customer_id=?, doctor_id=?, prescription_drug_id=?, amount=?, issue_date=?, end_date=?, prescription_status_id=? WHERE prescription_id=?";

    private static final String COLUMN_NAME_PRESCRIPTION_ID = "prescription_id";
    private static final String COLUMN_NAME_PRESCRIPTION_AMOUNT = "amount";
    private static final String COLUMN_NAME_PRESCRIPTION_ISSUE_DATE = "issue_date";
    private static final String COLUMN_NAME_PRESCRIPTION_END_DATE = "end_date";
    private static final String COLUMN_NAME_PRESCRIPTION_STATUS = "prescription_status";
    private static final String COLUMN_NAME_EXIST = "exist";
    private static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_NAME_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_NAME_CUSTOMER_SURNAME = "customer_surname";
    private static final String COLUMN_NAME_CUSTOMER_EMAIL = "customer_email";
    private static final String COLUMN_NAME_CUSTOMER_ROLE = "customer_role";
    private static final String COLUMN_NAME_CUSTOMER_AMOUNT = "customer_amount";

    private static final String COLUMN_NAME_DOCTOR_ID = "doctor_id";
    private static final String COLUMN_NAME_DOCTOR_NAME = "doctor_name";
    private static final String COLUMN_NAME_DOCTOR_SURNAME = "doctor_surname";
    private static final String COLUMN_NAME_DOCTOR_EMAIL = "doctor_email";
    private static final String COLUMN_NAME_DOCTOR_ROLE = "doctor_role";

    private static final String COLUMN_NAME_DRUG_ID = "drug_id";
    private static final String COLUMN_NAME_DRUG_NAME = "drug_name";
    private static final String COLUMN_NAME_DRUG_AMOUNT = "drug_amount";
    private static final String COLUMN_NAME_DRUG_DESCRIPTION = "description";
    private static final String COLUMN_NAME_DRUG_NEED_PRESCRIPTION = "need_prescription";
    private static final String COLUMN_NAME_DRUG_DOSAGE = "dosage";
    private static final String COLUMN_NAME_DRUG_PRICE = "price";

    private static final PrescriptionDao instance = new PrescriptionDao();

    private PrescriptionDao() {
    }

    public static PrescriptionDao getInstance() {
        return instance;
    }

    public List<Prescription> findAllByCustomerId(long customerId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_CUSTOMER_ID)) {
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptionList = new ArrayList<>();
            while (resultSet.next()) {
                long prescriptionId = resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ID);
                String customerName = resultSet.getString(COLUMN_NAME_CUSTOMER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_CUSTOMER_SURNAME);
                String customerEmail = resultSet.getString(COLUMN_NAME_CUSTOMER_EMAIL);
                String customerRoleString = resultSet.getString(COLUMN_NAME_CUSTOMER_ROLE);
                User.Role customerRole = User.Role.valueOf(customerRoleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_CUSTOMER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, customerEmail, customerRole, customerAmount);
                long doctorId = resultSet.getLong(COLUMN_NAME_DOCTOR_ID);
                String doctorName = resultSet.getString(COLUMN_NAME_DOCTOR_NAME);
                String doctorSurname = resultSet.getString(COLUMN_NAME_DOCTOR_SURNAME);
                String doctorEmail = resultSet.getString(COLUMN_NAME_DOCTOR_EMAIL);
                String doctorRoleString = resultSet.getString(COLUMN_NAME_DOCTOR_ROLE);
                User.Role doctorRole = User.Role.valueOf(doctorRoleString.toUpperCase());
                BigDecimal doctorAmount = BigDecimal.ZERO;
                User doctor = new User(doctorId, doctorName, doctorSurname, doctorEmail, doctorRole, doctorAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String drugDescription = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needDescription = resultSet.getBoolean(COLUMN_NAME_DRUG_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DRUG_DOSAGE);
                BigDecimal drugPrice = resultSet.getBigDecimal(COLUMN_NAME_DRUG_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, drugDescription, needDescription, dosage, drugPrice);
                int amount = resultSet.getInt(COLUMN_NAME_PRESCRIPTION_AMOUNT);
                Date issueDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    issueDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE));
                }
                Date endDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    endDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_END_DATE));
                }
                String stringStatus = resultSet.getString(COLUMN_NAME_PRESCRIPTION_STATUS);
                Prescription.Status status = Prescription.Status.valueOf(stringStatus.toUpperCase());
                Prescription prescription = new Prescription(prescriptionId, customer, doctor, drug, amount, issueDate, endDate, status);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Prescription> findByDoctorIdAndStatusId(long doctorId, int statusId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_DOCTOR_ID_AND_STATUS_ID)) {
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setInt(2, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptionList = new ArrayList<>();
            while (resultSet.next()) {
                long prescriptionId = resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ID);
                long customerId = resultSet.getLong(COLUMN_NAME_CUSTOMER_ID);
                String customerName = resultSet.getString(COLUMN_NAME_CUSTOMER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_CUSTOMER_SURNAME);
                String customerEmail = resultSet.getString(COLUMN_NAME_CUSTOMER_EMAIL);
                String customerRoleString = resultSet.getString(COLUMN_NAME_CUSTOMER_ROLE);
                User.Role customerRole = User.Role.valueOf(customerRoleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_CUSTOMER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, customerEmail, customerRole, customerAmount);
                String doctorName = resultSet.getString(COLUMN_NAME_DOCTOR_NAME);
                String doctorSurname = resultSet.getString(COLUMN_NAME_DOCTOR_SURNAME);
                String doctorEmail = resultSet.getString(COLUMN_NAME_DOCTOR_EMAIL);
                String doctorRoleString = resultSet.getString(COLUMN_NAME_DOCTOR_ROLE);
                User.Role doctorRole = User.Role.valueOf(doctorRoleString.toUpperCase());
                BigDecimal doctorAmount = BigDecimal.ZERO;
                User doctor = new User(doctorId, doctorName, doctorSurname, doctorEmail, doctorRole, doctorAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String drugDescription = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needDescription = resultSet.getBoolean(COLUMN_NAME_DRUG_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DRUG_DOSAGE);
                BigDecimal drugPrice = resultSet.getBigDecimal(COLUMN_NAME_DRUG_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, drugDescription, needDescription, dosage, drugPrice);
                int amount = resultSet.getInt(COLUMN_NAME_PRESCRIPTION_AMOUNT);
                Date issueDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    issueDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE));
                }
                Date endDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    endDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_END_DATE));
                }
                String stringStatus = resultSet.getString(COLUMN_NAME_PRESCRIPTION_STATUS);
                Prescription.Status status = Prescription.Status.valueOf(stringStatus.toUpperCase());
                Prescription prescription = new Prescription(prescriptionId, customer, doctor, drug, amount, issueDate, endDate, status);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Prescription> findById(long prescriptionId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, prescriptionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Prescription prescription = null;
            while (resultSet.next()) {
                long customerId = resultSet.getLong(COLUMN_NAME_CUSTOMER_ID);
                String customerName = resultSet.getString(COLUMN_NAME_CUSTOMER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_CUSTOMER_SURNAME);
                String customerEmail = resultSet.getString(COLUMN_NAME_CUSTOMER_EMAIL);
                String customerRoleString = resultSet.getString(COLUMN_NAME_CUSTOMER_ROLE);
                User.Role customerRole = User.Role.valueOf(customerRoleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_CUSTOMER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, customerEmail, customerRole, customerAmount);
                long doctorId = resultSet.getLong(COLUMN_NAME_DOCTOR_ID);
                String doctorName = resultSet.getString(COLUMN_NAME_DOCTOR_NAME);
                String doctorSurname = resultSet.getString(COLUMN_NAME_DOCTOR_SURNAME);
                String doctorEmail = resultSet.getString(COLUMN_NAME_DOCTOR_EMAIL);
                String doctorRoleString = resultSet.getString(COLUMN_NAME_DOCTOR_ROLE);
                User.Role doctorRole = User.Role.valueOf(doctorRoleString.toUpperCase());
                BigDecimal doctorAmount = BigDecimal.ZERO;
                User doctor = new User(doctorId, doctorName, doctorSurname, doctorEmail, doctorRole, doctorAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String drugDescription = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needDescription = resultSet.getBoolean(COLUMN_NAME_DRUG_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DRUG_DOSAGE);
                BigDecimal drugPrice = resultSet.getBigDecimal(COLUMN_NAME_DRUG_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, drugDescription, needDescription, dosage, drugPrice);
                int amount = resultSet.getInt(COLUMN_NAME_PRESCRIPTION_AMOUNT);
                Date issueDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    issueDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE));
                }
                Date endDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    endDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_END_DATE));
                }
                String stringStatus = resultSet.getString(COLUMN_NAME_PRESCRIPTION_STATUS);
                Prescription.Status status = Prescription.Status.valueOf(stringStatus.toUpperCase());
                prescription = new Prescription(prescriptionId, customer, doctor, drug, amount, issueDate, endDate, status);
            }
            return Optional.ofNullable(prescription);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean existPrescriptionByDrugIdAndCustomerId(long drugId, long customerId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_PRESCRIPTION_BY_DRUG_ID_AND_CUSTOMER_ID)) {
            preparedStatement.setLong(1, drugId);
            preparedStatement.setLong(2, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean result = resultSet.getBoolean(COLUMN_NAME_EXIST);
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void add(Prescription prescription) throws DaoException {
        User customer = prescription.getCustomer();
        long customerId = customer.getId();
        User doctor = prescription.getDoctor();
        long doctorId = doctor.getId();
        Drug drug = prescription.getDrug();
        long drugId = drug.getId();
        int amount = prescription.getAmount();
        Date issueDate = prescription.getIssueDate();
        long issueDateLong = issueDate.getTime();
        Date endDate = prescription.getEndDate();
        long endDateLong = endDate.getTime();
        int statusId = prescription.getStatus().ordinal() + 1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PRESCRIPTION)) {
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, doctorId);
            preparedStatement.setLong(3, drugId);
            preparedStatement.setInt(4, amount);
            preparedStatement.setLong(5, issueDateLong);
            preparedStatement.setLong(6, endDateLong);
            preparedStatement.setInt(7, statusId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Prescription prescription) throws DaoException {
        long prescriptionId = prescription.getId();
        User customer = prescription.getCustomer();
        long customerId = customer.getId();
        User doctor = prescription.getDoctor();
        long doctorId = doctor.getId();
        Drug drug = prescription.getDrug();
        long drugId = drug.getId();
        int prescriptionAmount = prescription.getAmount();
        Date issueDate = prescription.getIssueDate();
        Date endDate = prescription.getEndDate();
        long issueDateLong = issueDate.getTime();
        long endDateLong = endDate.getTime();
        Prescription.Status status = prescription.getStatus();
        int statusId = status.ordinal() + 1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, doctorId);
            preparedStatement.setLong(3, drugId);
            preparedStatement.setInt(4, prescriptionAmount);
            preparedStatement.setLong(5, issueDateLong);
            preparedStatement.setLong(6, endDateLong);
            preparedStatement.setInt(7, statusId);
            preparedStatement.setLong(8, prescriptionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
