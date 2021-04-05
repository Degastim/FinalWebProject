package com.epam.pharmacy.model.entity;

public class Order extends Entity {
    private User customer;
    private Drug drug;
    private int drugsNumber;
    private Status status;

    public enum Status {
        PROCESSING,
        APPROVED,
        REJECTED
    }

    public Order(User customer, Drug drug, int drugsNumber, Order.Status status) {
        this.customer = customer;
        this.drug = drug;
        this.drugsNumber = drugsNumber;
        this.status = status;
    }
    public Order(long drugOrderId, User customer, Drug drug, int drugsNumber, Order.Status status) {
        super(drugOrderId);
        this.customer = customer;
        this.drug = drug;
        this.drugsNumber = drugsNumber;
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getDrugsNumber() {
        return drugsNumber;
    }

    public void setDrugsNumber(int drugsNumber) {
        this.drugsNumber = drugsNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Order order = (Order) o;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) {
            return false;
        }
        if (drug != null ? !drug.equals(order.drug) : order.drug != null) {
            return false;
        }
        if (status != order.status) {
            return false;
        }
        return drugsNumber != order.drugsNumber;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 7 * drugsNumber;
        result += 5 * customer.hashCode();
        result += 3 * drug.hashCode();
        result += status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("customer=").append(customer);
        sb.append(", drug=").append(drug);
        sb.append(", amount=").append(drugsNumber);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
