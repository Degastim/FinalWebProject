package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugPicture;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrugDao extends AbstractDao<Drug> {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_DRUG = "SELECT drug_id, drug_name, drug_amount, description, need_prescription, dosage, price,drug_picture_id,drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON drug_id=picture_drug_id";
    private static final String SQL_FIND_BY_ID = "SELECT drug_name,drug_amount,description,need_prescription,dosage,price,drug_picture_id,drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON webdb.drugs.drug_id=webdb.drug_pictures.picture_drug_id WHERE drug_id=?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM webdb.drugs where drug_id=?";
    private static final String SQL_ADD_DRUG = "INSERT INTO webdb.drugs(drug_name, drug_amount, description, need_prescription, dosage, price) VALUES(?,?,?,?,?,?)";
    private static final String SQL_UPDATE_DRUG = "UPDATE webdb.drugs SET drug_name = ?,drug_amount=?,description=?,need_prescription=?,dosage=?,price=? WHERE drug_id = ?";
    private static final String SQL_EXIST_BY_NAME_AND_DOSAGE = "SELECT EXISTS (SELECT * FROM webdb.drugs WHERE drug_name =? AND dosage=?) AS exist";
    private static final String SQL_SELECT_NEED_PRESCRIPTION_BY_DRUG_NAME_AND_DOSAGE = "SELECT need_prescription FROM webdb.drugs WHERE drug_name=? AND dosage=?";
    private static final String SQL_SELECT_BY_NAME_AND_DOSAGE = "SELECT drug_id,drug_amount,description,need_prescription,price,drug_picture_id,drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON webdb.drugs.drug_id=webdb.drug_pictures.picture_drug_id WHERE drug_name=? AND dosage=?";
    private static final String SQL_FIND_DRUG_ID_BY_DRUG_NAME_AND_DOSAGE = "SELECT drug_id FROM webdb.drugs WHERE drug_name=? AND dosage=?";
    private static final String SQL_FIND_BY_NEED_PRESCRIPTION = "SELECT drug_id,drug_name,drug_amount,description,dosage,price,drug_picture_id,drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON webdb.drugs.drug_id=webdb.drug_pictures.picture_drug_id WHERE need_prescription=?";

    private static final String COLUMN_NAME_DRUG_ID = "drug_id";
    private static final String COLUMN_NAME_DRUG_NAME = "drug_name";
    private static final String COLUMN_NAME_DRUG_AMOUNT = "drug_amount";
    private static final String COLUMN_NAME_DRUG_DESCRIPTION = "description";
    private static final String COLUMN_NAME_DRUG_PICTURE = "drug_picture";
    private static final String COLUMN_NAME_NEED_PRESCRIPTION = "need_prescription";
    private static final String COLUMN_NAME_DOSAGE = "dosage";
    private static final String COLUMN_NAME_PRICE = "price";
    private static final String COLUMN_NAME_EXIST = "exist";
    private static final String COLUMN_NAME_DRUG_PICTURE_ID = "drug_picture_id";

    /**
     * Searches the database for all drugs
     *
     * @return {@link List} object which contains drugs.
     * @throws DaoException if the database throws SQLException.
     */
    public List<Drug> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_DRUG)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            List<Drug> drugList = new ArrayList<>();
            long previousDrugId = 0;
            List<DrugPicture> pictureList = null;
            while (resultSet.next()) {
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                if (previousDrugId != drugId) {
                    String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                    int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                    boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                    String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                    double dosage = resultSet.getDouble(COLUMN_NAME_DOSAGE);
                    BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                    pictureList = new ArrayList<>();
                    drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price, pictureList);
                    drugList.add(drug);
                    previousDrugId = drugId;
                }
                String drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                if (drugPictureString != null) {
                    long drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                    DrugPicture drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                    drug.setDrugPictureList(pictureList);
                }
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Searches the database by drug ID
     *
     * @param drugId drug ID by which it will search in the database
     * @return {@link Optional} object which contains drug.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Drug> findById(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            DrugPicture drugPicture;
            if (resultSet.next()) {
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                List<DrugPicture> pictureList = new ArrayList<>();
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                double dosage = resultSet.getDouble(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                String drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                long drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                if (drugPictureString != null) {
                    drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                }
                while (resultSet.next()) {
                    drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                    drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                    drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                }
                drug = new Drug(drugId, drugName, amount, description, needPrescription, dosage, price, pictureList);
            }
            return Optional.ofNullable(drug);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Removes a drug from the database
     *
     * @param drugId long value ID of the drug to be deleted
     * @throws DaoException if the database throws SQLException.
     */
    public void delete(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);) {
            preparedStatement.setLong(1, drugId);
            preparedStatement.execute();
            logger.log(Level.INFO, "The drug with id " + drugId + " has been deleted");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Adds a drug to the database.
     *
     * @param drug {@link Drug} object which will be added to the database.
     * @throws DaoException if the database throws SQLException.
     */
    @Override
    public void add(Drug drug) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DRUG)) {
            String drugName = drug.getDrugName();
            int drugAmount = drug.getDrugAmount();
            double dosage = drug.getDosage();
            String description = drug.getDescription();
            boolean needPrescription = drug.isNeedPrescription();
            BigDecimal price = drug.getPrice();
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, description);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setDouble(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.execute();
            logger.log(Level.INFO, "Adding a drug" + drug + " to the database");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Changes the drug in the database.
     *
     * @param drug {@link Drug} object to which will be changed.
     * @throws DaoException if the database throws SQLException.
     */
    public void update(Drug drug) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DRUG)) {
            long drugId = drug.getId();
            String drugName = drug.getDrugName();
            int drugAmount = drug.getDrugAmount();
            double dosage = drug.getDosage();
            String description = drug.getDescription();
            boolean needPrescription = drug.isNeedPrescription();
            BigDecimal price = drug.getPrice();
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, description);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setDouble(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.setLong(7, drugId);
            preparedStatement.execute();
            logger.log(Level.INFO, "Changing a drug in the database");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Checks if there is a drug by the name of the drug and its dosage.
     *
     * @param drugName String object by which the search will be performed.
     * @param dosage   int value by which the search will be performed.
     * @return boolean value true if there is a drug.
     * @throws DaoException if the database throws SQLException.
     */
    public boolean existByDrugNameAndDosage(String drugName, double dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setDouble(2, dosage);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(COLUMN_NAME_EXIST);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Search for a needPrescription by drugName and dosage in the database.
     *
     * @param drugName String object by which the search will be performed.
     * @param dosage   int value by which the search will be performed.
     * @return {@link Optional} object which contains drug.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Boolean> findNeedPrescriptionByDrugNameAndDosage(String drugName, double dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_NEED_PRESCRIPTION_BY_DRUG_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setDouble(2, dosage);
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

    /**
     * Search for a drug by drugName and dosage in the database
     *
     * @param drugName String object by which the search will be performed
     * @param dosage   int value by which the search will be performed
     * @return {@link Optional} object which contains drug.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Drug> findByNameAndDosage(String drugName, double dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setDouble(2, dosage);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            DrugPicture drugPicture;
            if (resultSet.next()) {
                List<DrugPicture> pictureList = new ArrayList<>();
                int drugId = resultSet.getInt(COLUMN_NAME_DRUG_ID);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                String drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                long drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                if (drugPictureString != null) {
                    drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                }
                while (resultSet.next()) {
                    drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                    drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                    drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                }
                drug = new Drug(drugId, drugName, amount, description, needPrescription, dosage, price, pictureList);
            }
            return Optional.ofNullable(drug);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Search for a drug ID by drugName and dosage in the database
     *
     * @param drugName String object by which the search will be performed
     * @param dosage   int value by which the search will be performed
     * @return {@link Optional} object which contains drug.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Integer> findDrugIdByDrugNameAndDosage(String drugName, double dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_DRUG_ID_BY_DRUG_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setDouble(2, dosage);
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

    /**
     * Search for a drug by needPrescription in the database.
     *
     * @param value boolean value  by which the search will be performed.
     * @return {@link List} object which contains drugs.
     * @throws DaoException if the database throws SQLException.
     */
    public List<Drug> findByNeedPrescription(boolean value) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NEED_PRESCRIPTION)) {
            preparedStatement.setBoolean(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            List<Drug> drugList = new ArrayList<>();
            long previousDrugId = 0;
            List<DrugPicture> pictureList = null;
            while (resultSet.next()) {
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                if (previousDrugId != drugId) {
                    String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                    int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                    String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                    double dosage = resultSet.getDouble(COLUMN_NAME_DOSAGE);
                    BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                    pictureList = new ArrayList<>();
                    drug = new Drug(drugId, drugName, drugAmount, description, value, dosage, price, pictureList);
                    drugList.add(drug);
                    previousDrugId = drugId;
                }
                String drugPictureString = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                if (drugPictureString != null) {
                    long drugPictureId = resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                    DrugPicture drugPicture = new DrugPicture(drugPictureId, drugPictureString);
                    pictureList.add(drugPicture);
                    drug.setDrugPictureList(pictureList);
                }
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

