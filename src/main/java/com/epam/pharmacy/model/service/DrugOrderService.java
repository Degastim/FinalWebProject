package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.entity.User;

import java.util.List;

/**
 * Interface provides actions on {@link DrugOrder}.
 *
 * @author Yauheni Tsitou.
 */
public interface DrugOrderService {

    /**
     * Make an order for the drug. Returns true if the user does not have enough money.
     *
     * @param customer   customer who places an order
     * @param drug       drug for which the order is made
     * @param drugAmount the quantity of the drug for which the order was made
     * @return true if it is possible to make an order
     * @throws ServiceException if an error occurs while processing.
     */
    boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException;

    /**
     * Search for an order in the database by customer ID.
     *
     * @param customerId ID of the customer who made the order.
     * @return {@link List} of orders made by this customer.
     * @throws ServiceException if an error occurs while processing.
     */
    List<DrugOrder> findByCustomerId(long customerId) throws ServiceException;

    /**
     * Search for an order in the database by order status.
     *
     * @param status order status.
     * @return {@link List} of orders that have this status.
     * @throws ServiceException if an error occurs while processing
     */
    List<DrugOrder> findByStatus(DrugOrder.Status status) throws ServiceException;

    /**
     * Changes order status by id.
     *
     * @param drugOrderId drug order id whose status will be changed.
     * @param status      the status to which the order status will be changed .
     * @throws ServiceException if an error occurs while processing.
     */
    void updateStatusById(long drugOrderId, DrugOrder.Status status) throws ServiceException;

    /**
     * Cancellation of a drug order. The money will be returned to the buyer and drugs for the purchase
     *
     * @param drugOrderId order ID for the drug that will be canceled
     * @return false if no drug is found.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean refuseDrugOrderService(long drugOrderId) throws ServiceException;
}
