package com.epam.pharmacy.model.entity;

import java.math.BigDecimal;

public class User {
    private long userId;
    private String name;
    private String surname;
    private String email;
    private UserRole role;
    private BigDecimal amount;

    public enum UserRole {
        CUSTOMER,
        DOCTOR,
        PHARMACIST;
    }

    public User() {
    }

    public User(String name, String surname, String email, UserRole role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    public User(long userId, String name, String surname, String email, UserRole role, BigDecimal amount) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.amount = amount;
    }

    public User(String name, String surname, UserRole role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
    }



    public User(long userId, String name, String surname) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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
        if (amount != null ? !amount.equals(user.amount) : user.amount != null) {
            return false;
        }
        return user.userId == userId;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 31 * (name != null ? name.hashCode() : 0);
        result += 29 * (surname != null ? surname.hashCode() : 0);
        result += 23 * (email != null ? email.hashCode() : 0);
        result += 19 * (role != null ? role.hashCode() : 0);
        result += Long.hashCode(userId);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
