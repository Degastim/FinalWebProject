package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.DrugPicture;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Dao DrugPicture Database Class.
 */
public class DrugPictureDao extends AbstractDao<DrugPicture> {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_DRUG_PICTURE = "INSERT INTO webdb.drug_pictures(drug_picture,picture_drug_id) VALUES (?,?)";
    private static final String SQL_DELETE_DRUG_PICTURE = "DELETE FROM webdb.drug_pictures WHERE drug_picture_id=?";

    @Override
    public void add(DrugPicture drugPicture) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DRUG_PICTURE)) {
            String drugPictureString = drugPicture.getDrugPicture();
            long pictureDrugId = drugPicture.getId();
            preparedStatement.setString(1, drugPictureString);
            preparedStatement.setLong(2, pictureDrugId);
            preparedStatement.execute();
            logger.log(Level.INFO, "Picture has been added");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Removes a picture from the database.
     *
     * @param drugPictureId long value drug picture id.
     * @throws DaoException if the database throws SQLException.
     */
    public void delete(long drugPictureId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_PICTURE)) {
            preparedStatement.setLong(1, drugPictureId);
            preparedStatement.execute();
            logger.log(Level.INFO, "The picture has been deleted ");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
