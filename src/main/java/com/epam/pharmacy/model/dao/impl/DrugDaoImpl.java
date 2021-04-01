package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrugDaoImpl implements DrugDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_DRUG_BY_ID_AND_IMAGES = "SELECT webdb.drugs.drug_id,webdb.drugs.drug_name,webdb.drugs.amount,webdb.drugs.description,webdb.drugs.need_prescription,webdb.drugs.dosage,drugs.price, webdb.drug_pictures.drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON webdb.drugs.drug_id=webdb.drug_pictures.picture_drug_id where webdb.drugs.drug_id=?";
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM webdb.drugs";
    private static final String SQL_SELECT_ALL_DRUG = "SELECT * FROM webdb.drugs";
    private static final String SQL_UPDATE_DRUG = "UPDATE webdb.drugs SET drug_name=?,amount=?,description=?,need_prescription=?,dosage=?,price=? WHERE drug_id=?";
    private static final String SQL_DELETE_DRUG_BY_ID = "DELETE FROM webdb.drugs where drug_id=?";
    private static final String SQL_ADD_DRUG = "INSERT INTO webdb.drugs(drug_name, amount, description, need_prescription,dosage,price) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_DRUGS_BY_NEED_PRESCRIPTION = "SELECT drug_id,drug_name FROM webdb.drugs where need_prescription=?";
    private static final String SQL_SELECT_NEED_PRESCRIPTION_BY_DRUG_NAME = "SELECT need_prescription FROM webdb.drugs where drug_name=?";
    private static final String SQL_SELECT_DRUG_ID_BY_DRUG_NAME = "SELECT webdb.drugs.drug_id FROM webdb.drugs where drug_name=?";
    private static final String SQL_SELECT_DRUG_BY_ID = "SELECT webdb.drugs.drug_id,webdb.drugs.drug_name,webdb.drugs.amount,webdb.drugs.description,webdb.drugs.need_prescription FROM webdb.drugs where webdb.drugs.drug_id=?";
    private static final String SQL_EXIST_BY_NAME_AND_DOSAGE = "SELECT EXISTS (SELECT * FROM webdb.drugs WHERE drug_name = ? AND dosage=?) AS exist";
    private static final String SQL_FIND_DRUG_ID_BY_DRUG_NAME = "SELECT webdb.drugs.drug_id FROM webdb.drugs WHERE webdb.drugs.drug_name=?";
    private static final String SQL_FIND_AMOUNT_BY_DRUG_ID = "SELECT amount FROM webdb.drugs WHERE drug_name=?";
    private static final String SQL_UPDATE_AMOUNT_BY_DRUG_ID = "UPDATE webdb.drugs SET amount=? WHERE drug_id=?";
    private static final String SQL_SELECT_BY_NAME_AND_DOSAGE = "SELECT * FROM webdb.drugs WHERE drug_name=? AND dosage=?";
    private static final String SQL_CHANGE_AUTOINCREMENT = "ALTER TABLE webdb.drugs AUTO_INCREMENT = ?";

    private static final String COLUMN_NAME_DRUG_ID = "drug_id";
    private static final String COLUMN_NAME_DRUG_NAME = "drug_name";
    private static final String COLUMN_NAME_DRUG_AMOUNT = "amount";
    private static final String COLUMN_NAME_DRUG_DESCRIPTION = "description";
    private static final String COLUMN_NAME_DRUG_PICTURE = "drug_picture";
    private static final String COLUMN_NAME_COUNT = "COUNT(*)";
    private static final String COLUMN_NAME_NEED_PRESCRIPTION = "need_prescription";
    private static final String COLUMN_NAME_DOSAGE = "dosage";
    private static final String COLUMN_NAME_EXIST = "exist";
    private static final String COLUMN_NAME_PRICE = "price";
    private static final DrugDao instance = new DrugDaoImpl();

    private DrugDaoImpl() {
    }

    public static DrugDao getInstance() {
        return instance;
    }

    @Override
    public Optional<Drug> findByIdWithImages(int id) throws DaoException {
        logger.log(Level.DEBUG, "Search for drugs by their id");
        Optional<Drug> result;
        Drug drug = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DRUG_BY_ID_AND_IMAGES)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                List<String> imageList = new ArrayList<>();
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                String image_path = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                if (image_path != null) {
                    imageList.add(image_path);
                }
                while (resultSet.next()) {
                    image_path = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                    imageList.add(image_path);
                }
                drug = new Drug(drugId, drugName, amount, description, needPrescription, dosage, price, imageList);
            }
            result = Optional.ofNullable(drug);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public int showDrugsNumber() throws DaoException {
        logger.log(Level.DEBUG, "Finding the number of drugs");
        int result = 0;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(COLUMN_NAME_COUNT);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Drug> findAllDrugs() throws DaoException {
        logger.log(Level.DEBUG, "Search for all drugs");
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_DRUG)) {
            List<Drug> drugList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                Drug drug = new Drug(drugId, drugName, amount, description, dosage, price);
                drugList.add(drug);
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateDrug(int drugId, String drugName, int drugAmount, String drugDescription, boolean needPrescription, BigDecimal price, int dosage) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DRUG)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, drugDescription);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setInt(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.setInt(7, drugId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(int drugId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        PreparedStatement changeIncrementPreparedStatement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_BY_ID);
            statement = connection.createStatement();
            changeIncrementPreparedStatement = connection.prepareStatement(SQL_CHANGE_AUTOINCREMENT);
            preparedStatement.setInt(1, drugId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(SQL_COUNT);
            resultSet.next();
            int rowNumber = resultSet.getInt(COLUMN_NAME_COUNT);
            changeIncrementPreparedStatement.setInt(1, rowNumber);
            changeIncrementPreparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Rollback failed");
            }
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "AutoCommit change failed");
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing statement ");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing statement ");
            }
            try {
                if (changeIncrementPreparedStatement != null) {
                    changeIncrementPreparedStatement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing statement ");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing connection");
            }
        }
    }

    @Override
    public void add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, int dosage, BigDecimal price) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DRUG)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, drugDescription);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setInt(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Drug> findDrugNameAndIdWithNeedPrescription(boolean value) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRUGS_BY_NEED_PRESCRIPTION)) {
            preparedStatement.setBoolean(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Drug> drugList = new ArrayList<>();
            while (resultSet.next()) {
                int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                Drug drug = new Drug(drugId, drugName);
                drugList.add(drug);
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Boolean> findNeedPrescriptionByDrugName(String drugName) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_NEED_PRESCRIPTION_BY_DRUG_NAME)) {
            preparedStatement.setString(1, drugName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Boolean result = null;
            if (resultSet.next()) {
                result = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int findDrugByDrugName(String drugName) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRUG_ID_BY_DRUG_NAME)) {
            preparedStatement.setString(1, drugName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
            return drugId;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Drug> findById(int drugId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRUG_BY_ID)) {
            preparedStatement.setInt(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug result = null;
            if (resultSet.next()) {
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                result = new Drug(drugId, drugName, amount, description, needPrescription);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean existDrugByNameAndDosage(String drugName, int dosage) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean result = resultSet.getBoolean(COLUMN_NAME_EXIST);
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Integer> findDrugIdByDrugName(String drugName) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_DRUG_ID_BY_DRUG_NAME)) {
            preparedStatement.setString(1, drugName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer drugId = null;
            if (resultSet.next()) {
                drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
            }
            return Optional.ofNullable(drugId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Integer> findAmountById(int drugId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_AMOUNT_BY_DRUG_ID)) {
            Integer result = null;
            preparedStatement.setInt(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAmountById(int drugId, int drugAmount) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AMOUNT_BY_DRUG_ID)) {
            preparedStatement.setInt(1, drugAmount);
            preparedStatement.setInt(2, drugId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Drug> findByNameAndDosage(String drugName, int dosage) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            if (resultSet.next()) {
                int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
            }
            return Optional.ofNullable(drug);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

