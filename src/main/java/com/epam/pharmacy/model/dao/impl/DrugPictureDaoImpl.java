package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.DrugPictureDao;
import com.epam.pharmacy.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrugPictureDaoImpl implements DrugPictureDao {
    private static final String SQL_ADD_USERS = "INSERT INTO webdb.drug_pictures(drug_picture,picture_drug_id) VALUES (?,?)";

    private static final DrugPictureDao instance = new DrugPictureDaoImpl();

    private DrugPictureDaoImpl() {
    }

    public static DrugPictureDao getInstance() {
        return instance;
    }

    @Override
    public void add(String drugPicture, int pictureDrugId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USERS)) {
            preparedStatement.setString(1, drugPicture);
            preparedStatement.setInt(2, pictureDrugId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
