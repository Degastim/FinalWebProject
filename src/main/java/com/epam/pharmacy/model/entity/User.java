package com.epam.pharmacy.model.entity;

import java.math.BigDecimal;

/**
 * {@link Entity} class represents an {@link User}.
 *
 * @author Yauheni Tsitou.
 */
public class User extends Entity {
    /**
     * String value containing the name of this user.
     */
    private String name;
    /**
     * String value containing the surname of this user.
     */
    private String surname;
    /**
     * String value containing the email of this user.
     */
    private String email;
    /**
     * {@link Role} object containing the role of this user.
     */
    private Role role;
    /**
     * {@link BigDecimal} object containing the the amount of money in the account of this user.
     */
    private BigDecimal amount;

    /**
     * Enumeration of the user role.
     *
     * @author Yauheni Tsitou.
     */
    public enum Role {
        CUSTOMER,
        DOCTOR,
        PHARMACIST;
    }

    /**
     * Constructs an {@link User} object with given user ID.
     *
     * @param userId long value of user's ID.
     */
    public User(long userId) {
        super(userId);
    }

    /**
     * Constructs an {@link User} object with given user name,surname,email and role.
     *
     * @param name    String object of user's name.
     * @param surname String object of user's surname.
     * @param email   String object of user's email.
     * @param role    {@link Role} object of user's role.
     */
    public User(String name, String surname, String email, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    /**
     * Constructs an {@link User} object with given user ID,name,surname,email, role  and amount.
     *
     * @param userId  long value of user's ID.
     * @param name    String object of user's name.
     * @param surname String object of user's surname.
     * @param email   String object of user's email.
     * @param role    {@link Role} object of user's role.
     * @param amount  {@link BigDecimal} object of amount of money in the account of this user.
     */
    public User(long userId, String name, String surname, String email, Role role, BigDecimal amount) {
        super(userId);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.amount = amount;
    }

    /**
     * Getter method of user's name.
     *
     * @return user's name String object.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of user's name.
     *
     * @param name user's name String object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of user's surname.
     *
     * @return user's surname String object.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter method of user's surname.
     *
     * @param surname user's surname String object.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter method of user's email.
     *
     * @return user's email String object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method of user's email.
     *
     * @param email user's email String object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method of user's role.
     *
     * @return user's role {@link Role} object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter method of user's role.
     *
     * @param role user's role {@link Role} object.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Getter method of user's amount.
     *
     * @return user's amount {@link BigDecimal} object.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Setter method of user's amount.
     *
     * @param amount user's amount {@link BigDecimal} object.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (role != null ? !role.equals(user.role) : user.role != null) {
            return false;
        }
        return amount != null ? !amount.equals(user.amount) : user.amount != null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 7 * (name != null ? name.hashCode() : 0);
        result += 5 * (surname != null ? surname.hashCode() : 0);
        result += 3 * (email != null ? email.hashCode() : 0);
        result += 2 * (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
