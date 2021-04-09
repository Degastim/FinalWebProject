package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.DrugPicture;

/**
 * Interface provides actions on {@link DrugPicture}.
 *
 * @author Yauheni Tsitou.
 */
public interface DrugPictureService {

    /**
     * The method adds a string representation of an image to the database
     *
     * @param drugPicture   String object string representation of a picture
     * @param pictureDrugId ID of the drug to which the picture belongs
     * @throws ServiceException if an error occurs while processing.
     */
    void add(String drugPicture, long pictureDrugId) throws ServiceException;

    /**
     * The method will remove the drug from which the given ID is
     *
     * @param drugPictureId long value drug picture ID.
     * @throws ServiceException if an error occurs while processing.
     */
    void delete(long drugPictureId) throws ServiceException;
}
