package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugPicture;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrugDao extends AbstractDao<Drug> {
    private static final String SQL_FIND_ALL_DRUG = "SELECT drug_id, drug_name, drug_amount, description, need_prescription, dosage, price FROM webdb.drugs";
    private static final String SQL_FIND_BY_ID = "SELECT drug_name,drug_amount,description,need_prescription,dosage,price,drug_picture_id,drug_picture FROM webdb.drugs LEFT JOIN webdb.drug_pictures ON webdb.drugs.drug_id=webdb.drug_pictures.picture_drug_id WHERE drug_id=?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM webdb.drugs where drug_id=?";
    private static final String SQL_CHANGE_AUTOINCREMENT = "ALTER TABLE webdb.drugs AUTO_INCREMENT = ?";
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
    private static final DrugDao instance = new DrugDao();

    private DrugDao() {
    }

    public static DrugDao getInstance() {
        return instance;
    }

    public List<Drug> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_DRUG)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Drug> drugList = new ArrayList<>();
            while (resultSet.next()) {
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, description, dosage, price);
                drugList.add(drug);
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Drug> findById(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            DrugPicture drugPicture = null;
            if (resultSet.next()) {
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                List<DrugPicture> pictureList = new ArrayList<>();
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
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

    public void delete(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);) {
            preparedStatement.setLong(1, drugId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void changeAutoincrement(long drugId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_AUTOINCREMENT)) {
            preparedStatement.setLong(1, drugId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void add(Drug drug) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DRUG)) {
            String drugName = drug.getDrugName();
            int drugAmount = drug.getDrugAmount();
            int dosage = drug.getDosage();
            String description = drug.getDescription();
            boolean needPrescription = drug.isNeedPrescription();
            BigDecimal price = drug.getPrice();
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, description);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setInt(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Drug drug) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DRUG)) {
            long drugId = drug.getId();
            String drugName = drug.getDrugName();
            int drugAmount = drug.getDrugAmount();
            int dosage = drug.getDosage();
            String description = drug.getDescription();
            boolean needPrescription = drug.isNeedPrescription();
            BigDecimal price = drug.getPrice();
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, drugAmount);
            preparedStatement.setString(3, description);
            preparedStatement.setBoolean(4, needPrescription);
            preparedStatement.setInt(5, dosage);
            preparedStatement.setBigDecimal(6, price);
            preparedStatement.setLong(7, drugId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean existByDrugNameAndDosage(String drugName, int dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
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

    public Optional<Boolean> checkNeedPrescriptionByDrugNameAndDosage(String drugName, int dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_NEED_PRESCRIPTION_BY_DRUG_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
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

    public Optional<Drug> findByNameAndDosage(String drugName, int dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
            ResultSet resultSet = preparedStatement.executeQuery();
            Drug drug = null;
            DrugPicture drugPicture = null;
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

    public Optional<Integer> findDrugIdByDrugNameAndDosage(String drugName, int dosage) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_DRUG_ID_BY_DRUG_NAME_AND_DOSAGE)) {
            preparedStatement.setString(1, drugName);
            preparedStatement.setInt(2, dosage);
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

    public List<Drug> findDrugByNeedPrescription(boolean value) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NEED_PRESCRIPTION)) {
            preparedStatement.setBoolean(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Drug> drugList = new ArrayList<>();
            while (resultSet.next()) {
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ID);
                long drugPictureId=resultSet.getLong(COLUMN_NAME_DRUG_PICTURE_ID);
                String picture = resultSet.getString(COLUMN_NAME_DRUG_PICTURE);
                DrugPicture drugPicture=new DrugPicture(drugPictureId,picture);
                List<DrugPicture> imageList = null;
                if (drugList.size() < drugId) {
                    String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                    int amount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                    String description = resultSet.getString(COLUMN_NAME_DRUG_DESCRIPTION);
                    int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                    BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                    imageList = new ArrayList<>();
                    imageList.add(drugPicture);
                    Drug drug = new Drug(drugId, drugName, amount, description, value, dosage, price, imageList);
                    drugList.add(drug);
                } else {
                    imageList.add(drugPicture);
                }
            }
            return drugList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

