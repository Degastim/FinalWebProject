package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugPicture;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on {@link DrugPicture}.
 *
 * @author Yauheni Tsitou.
 */
public interface DrugService {

    /**
     * The method searches for all drugs that are in the database
     *
     * @return List of drugs in the database
     * @throws ServiceException if an error occurs while processing.
     */
    List<Drug> findAllDrugs() throws ServiceException;

    /**
     * The method searches for the drug in the database along with its pictures.
     *
     * @param drugId long value ID of the drug the method is looking for
     * @return {@link Optional<Drug>} object of drug.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Drug> findByIdWithImages(long drugId) throws ServiceException;

    /**
     * Method that updates drug values in the database
     *
     * @param drugId           long value of drug's id.
     * @param drugName         String value of drug's name.
     * @param drugAmount       int value of drug's amount.
     * @param drugDescription  String object of drug's description.
     * @param needPrescription boolean value of drug's needPrescription.
     * @param price            {@link BigDecimal} object of drug's price.
     * @param dosage           int value of drug's dosage.
     * @return true if the drug has been updated
     * @throws ServiceException if an error occurs while processing.
     */
    boolean updateDrug(int drugId, String drugName, int drugAmount, String drugDescription, boolean needPrescription, BigDecimal price, double dosage) throws ServiceException;

    /**
     * The method that searches for drugs to be displayed on the jsp
     *
     * @param currentPaginationPage int value page for which drugs will be searched
     * @return {@link List} of drugs to display on the jsp.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Drug> findPaginationDrugs(int currentPaginationPage) throws ServiceException;

    /**
     * The method that will remove the drug from which the given id
     *
     * @param drugId long value drug id
     * @throws ServiceException if an error occurs while processing.
     */
    void deleteById(long drugId) throws ServiceException;

    /**
     * A method that adds a drug to the database if a drug with the same name and dosage does not exist
     *
     * @param drugName         String object of  drug name.
     * @param drugAmount       int value of drug name.
     * @param drugDescription  String value of drug description.
     * @param needPrescription boolean value of drug need prescription
     * @param dosage           int value of drug dosage
     * @param price            {@link BigDecimal} object of drug pricce
     * @return true if it was possible to add to the database
     * @throws ServiceException if an error occurs while processing.
     */
    boolean add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, double dosage, BigDecimal price) throws ServiceException;

    /**
     * A method that searches the database for drugs with the correct prescription requirements
     *
     * @param value boolean value of drug need prescription.
     * @return {@link List} of drugs with the required prescription requirement
     * @throws ServiceException if an error occurs while processing.
     */
    List<Drug> findDrugByNeedPrescription(boolean value) throws ServiceException;

    /**
     * The method checks whether a prescription is needed for a drug with the given name and dosage
     *
     * @param drugName String object of drug name.
     * @param dosage   int value of drug dosage.
     * @param value    boolean value of drug needPrescription.
     * @return result of checking.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean checkNeedPrescriptionByDrugNameAndDosage(String drugName, double dosage, boolean value) throws ServiceException;

    /**
     * Search for a drug by its name and dosage
     *
     * @param drugName String object of drug name
     * @param dosage   int value of drug dosage
     * @return {@link Optional<Drug>} оbject.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Integer> findDrugIdByDrugNameAndDosage(String drugName, double dosage) throws ServiceException;

    /**
     * The method searches for a drug by its name and dosage
     *
     * @param drugName String object of drug name
     * @param dosage   int value of drug dosage
     * @return {@link Optional<Drug>} оbject.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Drug> findDrugByDrugNameAndDosage(String drugName, double dosage) throws ServiceException;

    /**
     * Searches for the current page in pagination based on what the user has clicked
     *
     * @param paginationPage        String object which should contain a forward, backward or page number
     * @param currentPaginationPage int value current pagination pageю
     * @return int value new current pagination page
     * @throws ServiceException if an error occurs while processing.
     */
    int findCurrentPaginationPage(String paginationPage, int currentPaginationPage) throws ServiceException;

    /**
     * Counts the pagination start page to be displayed
     *
     * @param currentPaginationPage int value current pagination pageю
     * @return int value start pagination page
     */
    int countStartPaginationPage(int currentPaginationPage);

    /**
     * Counts the last pagination page to be displayed
     * @param currentPaginationPage int value current pagination pageю
     * @return int last pagination page
     */
    int countLastPaginationPage(int currentPaginationPage) throws ServiceException;
}
