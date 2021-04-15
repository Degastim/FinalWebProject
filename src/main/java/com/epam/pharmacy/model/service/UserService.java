package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on {@link User}.
 *
 * @author Yauheni Tsitou.
 */
public interface UserService {
    /**
     * Adding a user and his password to the database.
     *
     * @param user     {@link User} object which will be added to the database.
     * @param password String object user password for login.
     * @return true if user data was validated.
     * @throws ServiceException
     */
    boolean add(User user, String password) throws ServiceException;

    /**
     * Searches for a user by his email and password in the database.
     *
     * @param email    String object contains the user's search email.
     * @param password String object contains the user's search password.
     * @return {@link Optional} object which may contain the user.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * Updates the user's password in the database.
     *
     * @param user        {@link User} object to update in the database
     * @param newPassword String object password to add
     * @param oldPassword String object password for checking the similarity with the database
     * @return true if the oldPassword is the same as the password in the database and the password was validated.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean updateByPassword(User user, String newPassword, String oldPassword) throws ServiceException;

    /**
     * Searches for a user in the database by his role.
     *
     * @param role {@link User.Role} object user role
     * @return {@link List} object contains the users from the database.
     * @throws ServiceException if an error occurs while processing.
     */
    List<User> findByRole(User.Role role) throws ServiceException;

    /**
     * Validates data from the registration form and checks that the email is not in the database.
     *
     * @param name     String object contains the user name.
     * @param surname  String object contains user surname.
     * @param password String object contains user password.
     * @param email    String object contains user email.
     * @return true if the data was validated and there is no such email in the database
     * @throws ServiceException if an error occurs while processing.
     */
    boolean checkRegistrationForm(String name, String surname, String password, String email) throws ServiceException;

    /**
     * Updates the user's amount by adding values to it.
     *
     * @param user   {@link User} object contains the user whose account will be updated.
     * @param amount {@link BigDecimal} object contains  values to add to user account.
     * @throws ServiceException if an error occurs while processing.
     */
    void updateByAddAmount(User user, BigDecimal amount) throws ServiceException;

    /**
     * Searches for a user in the database by his id.
     *
     * @param userId long value user id in the database.
     * @return {@link Optional} object contains the user from the database.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<User> findById(long userId) throws ServiceException;
}
