package com.epam.pharmacy.model.entity;

import java.math.BigDecimal;

public class User extends Entity {
    private String name;
    private String surname;
    private String email;
    private Role role;
    private BigDecimal amount;

    public enum Role {
        CUSTOMER,
        DOCTOR,
        PHARMACIST;
    }

    public User() {
    }

    public User(long userId) {
        super(userId);
    }

    public User(String name, String surname, String email, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    public User(long userId, String name, String surname, String email, Role role, BigDecimal amount) {
        super(userId);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.amount = amount;
    }

    public User(String name, String surname, Role role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
    }


    public User(long userId, String name, String surname) {
        super(userId);
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getAmount() {
        return amount;
    }

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
