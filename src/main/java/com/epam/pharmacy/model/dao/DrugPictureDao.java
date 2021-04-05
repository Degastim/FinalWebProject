package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.DrugPicture;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrugPictureDao extends AbstractDao<DrugPicture> {
    private static final String SQL_ADD_DRUG_PICTURE = "INSERT INTO webdb.drug_pictures(drug_picture,picture_drug_id) VALUES (?,?)";
    private static final String SQL_DELETE_DRUG_PICTURE = "DELETE FROM webdb.drug_pictures WHERE drug_picture_id=?";
    private static final String SQL_CHANGE_AUTOINCREMENT = "ALTER TABLE webdb.drug_pictures AUTO_INCREMENT = ?";

    private static final DrugPictureDao instance = new DrugPictureDao();

    private DrugPictureDao() {
    }

    public static DrugPictureDao getInstance() {
        return instance;
    }

    @Override
    public void add(DrugPicture drugPicture) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DRUG_PICTURE)) {
            String drugPictureString = drugPicture.getDrugPicture();
            long pictureDrugId = drugPicture.getId();
            preparedStatement.setString(1, drugPictureString);
            preparedStatement.setLong(2, pictureDrugId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public void delete(long drugPictureId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_PICTURE)) {
            preparedStatement.setLong(1, drugPictureId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public void changeAutoincrement(long drugPictureId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_AUTOINCREMENT)) {
            preparedStatement.setLong(1, drugPictureId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
