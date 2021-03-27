package com.epam.pharmacy.model.entity;

public class Order {
    private long orderId;
    private User customer;
    private Drug drug;
    private int amount;
    private Prescription prescription;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) {
            return false;
        }
        if (drug != null ? !drug.equals(order.drug) : order.drug != null) {
            return false;
        }
        if (prescription != null ? !prescription.equals(order.prescription) : order.prescription != null) {
            return false;
        }
        if (amount != order.amount) {
            return false;
        }
        return order.orderId == orderId;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 11 * amount;
        result += 7 * orderId;
        result += 5 * customer.hashCode();
        result += 3 * drug.hashCode();
        result += prescription.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", customer=").append(customer);
        sb.append(", drug=").append(drug);
        sb.append(", amount=").append(amount);
        sb.append(", prescription=").append(prescription);
        sb.append('}');
        return sb.toString();
    }
}
