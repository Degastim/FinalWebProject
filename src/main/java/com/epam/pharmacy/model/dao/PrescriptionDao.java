package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PrescriptionDao extends AbstractDao<Prescription> {
    private static final String SQL_SELECT_Prescription_BY_CUSTOMER_ID_WITH_DOCTOR_NAME_AND_DOCTOR_SURNAME_AND_DRUG_NAME = "SELECT prescription_id, name,surname, drug_name, prescriptions.amount, issue_date, end_date,prescription_status FROM webdb.prescriptions LEFT JOIN webdb.users ON webdb.prescriptions.doctor_id=webdb.users.user_id LEFT JOIN webdb.drugs ON webdb.prescriptions.prescription_drug_id=webdb.drugs.drug_id LEFT JOIN webdb.prescription_statuses ON webdb.prescriptions.prescription_status_id= webdb.prescription_statuses.prescription_status_id where customer_id=?";
    private static final String SQL_UPDATE_STATUS_BY_ID = "UPDATE webdb.prescriptions SET prescription_status_id=? where prescription_id=?";
    private static final String SQL_FIND_PRESCRIPTION_ID_AND_CUSTOMER_NAME_AND_CUSTOMER_NAME_AND_DRUG_NAME_AND_AMOUNT_BY_DOCTOR_ID_AND_STATUS_ID = "SELECT prescription_id,name,surname,drug_name,prescriptions.amount FROM webdb.prescriptions LEFT JOIN webdb.users ON webdb.prescriptions.customer_id=webdb.users.user_id LEFT JOIN webdb.drugs ON webdb.prescriptions.prescription_drug_id=webdb.drugs.drug_id where doctor_id=? AND prescription_status_id=?";
    private static final String SQL_SELECT_PRESCRIPTION_BY_ID_WITHOUT_DOCTOR = "SELECT name,name,surname,email,drug_name,prescriptions.amount,issue_date,end_date,prescription_status FROM webdb.prescriptions LEFT JOIN webdb.users ON customer_id=user_id LEFT JOIN webdb.roles ON users.role_id=roles.role_id LEFT JOIN webdb.drugs ON prescription_drug_id=drug_id LEFT JOIN webdb.prescription_statuses ON prescriptions.prescription_status_id=prescription_statuses.prescription_status_id WHERE prescription_id=?";
    private static final String SQL_UPDATE_ISSUE_DATE_AND_END_DATE_AND_STATUS_BY_ID = "UPDATE webdb.prescriptions SET issue_date=?,  end_date=?, prescription_status_id=? WHERE prescription_id=?";
    private static final String SQL_EXIST_PRESCRIPTION_BY_DRUG_ID = "SELECT EXISTS (SELECT * FROM webdb.prescriptions WHERE prescription_drug_id = ?) AS exist";
    private static final String SQL_ADD_PRESCRIPTION = "INSERT INTO webdb.prescriptions(customer_id, doctor_id, prescription_drug_id, amount, issue_date, end_date, prescription_status_id) VALUES (?,?,?,?,?,?,?)";

    private static final String COLUMN_NAME_PRESCRIPTION_ID = "prescription_id";
    private static final String COLUMN_NAME_USER_NAME = "name";
    private static final String COLUMN_NAME_USER_SURNAME = "surname";
    private static final String COLUMN_NAME_DRUG_NAME = "drug_name";
    private static final String COLUMN_NAME_AMOUNT = "amount";
    private static final String COLUMN_NAME_PRESCRIPTION_ISSUE_DATE = "issue_date";
    private static final String COLUMN_NAME_PRESCRIPTION_END_DATE = "end_date";
    private static final String COLUMN_NAME_PRESCRIPTION_STATUS = "prescription_status";
    private static final String COLUMN_NAME_USER_EMAIL = "email";
    private static final String COLUMN_NAME_EXIST = "exist";

    private static final PrescriptionDao instance = new PrescriptionDao();

    private PrescriptionDao() {
    }

    public static PrescriptionDao getInstance() {
        return instance;
    }

    public List<Prescription> findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(long customerId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_Prescription_BY_CUSTOMER_ID_WITH_DOCTOR_NAME_AND_DOCTOR_SURNAME_AND_DRUG_NAME)) {
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptionList = new ArrayList<>();
            while (resultSet.next()) {
                long prescriptionId = resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ID);
                String doctorName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String doctorSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                User doctor = new User(doctorName, doctorSurname, User.Role.DOCTOR);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                Drug drug = new Drug(drugName);
                int amount = resultSet.getInt(COLUMN_NAME_AMOUNT);
                Date issueDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    issueDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE));
                }
                Date endDate = null;
                if (resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE) != 0) {
                    endDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_END_DATE));
                }
                User customer = new User();
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

    public void updateStatusById(int statusId, long prescriptionId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID)) {
            preparedStatement.setInt(1, statusId);
            preparedStatement.setLong(2, prescriptionId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Prescription> findPrescriptionIdAndCustomerNameAndCustomerSurNameAndDrugNameAndAmountByDoctorIdAndStatusId(long doctorId, int statusId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRESCRIPTION_ID_AND_CUSTOMER_NAME_AND_CUSTOMER_NAME_AND_DRUG_NAME_AND_AMOUNT_BY_DOCTOR_ID_AND_STATUS_ID)) {
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setInt(2, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptionList = new ArrayList<>();
            while (resultSet.next()) {
                long prescriptionId = resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ID);
                String customerName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                User customer = new User(customerName, customerSurname, User.Role.DOCTOR);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                Drug drug = new Drug(drugName);
                int amount = resultSet.getInt(COLUMN_NAME_AMOUNT);
                Prescription prescription = new Prescription();
                prescription.setId(prescriptionId);
                prescription.setCustomer(customer);
                prescription.setDrug(drug);
                prescription.setAmount(amount);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PRESCRIPTION_BY_ID_WITHOUT_DOCTOR)) {
            preparedStatement.setLong(1, prescriptionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Prescription prescription = null;
            if (resultSet.next()) {
                prescription = new Prescription();
                prescription.setId(prescriptionId);
                String customerName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String customerEmail = resultSet.getString(COLUMN_NAME_USER_EMAIL);
                User customer = new User(customerName, customerSurname, customerEmail, User.Role.DOCTOR);
                prescription.setCustomer(customer);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                Drug drug = new Drug(drugName);
                prescription.setDrug(drug);
                int amount = resultSet.getInt(COLUMN_NAME_AMOUNT);
                prescription.setAmount(amount);
                Date issueDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_ISSUE_DATE));
                prescription.setIssueDate(issueDate);
                Date endDate = new Date(resultSet.getLong(COLUMN_NAME_PRESCRIPTION_END_DATE));
                prescription.setEndDate(endDate);
                String statusString = resultSet.getString(COLUMN_NAME_PRESCRIPTION_STATUS);
                Prescription.Status status = Prescription.Status.valueOf(statusString.toUpperCase());
                prescription.setStatus(status);
            }
            return Optional.ofNullable(prescription);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void updateIssueDateAndEndDateAndStatusIdById(long prescriptionId, long issueDate, long endDate, int statusId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ISSUE_DATE_AND_END_DATE_AND_STATUS_BY_ID)) {
            preparedStatement.setLong(1, issueDate);
            preparedStatement.setLong(2, endDate);
            preparedStatement.setInt(3, statusId);
            preparedStatement.setLong(4, prescriptionId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean existPrescriptionByDrugId(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_PRESCRIPTION_BY_DRUG_ID)) {
            preparedStatement.setLong(1, drugId);
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
        long issueDateLong = 0;
        if (issueDate != null) {
            issueDateLong = issueDate.getTime();
        }
        Date endDate = prescription.getEndDate();
        long endDateLong = 0;
        if (endDate != null) {
            endDateLong = endDate.getTime();
        }
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
}
