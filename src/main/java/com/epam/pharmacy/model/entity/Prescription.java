package com.epam.pharmacy.model.entity;

import java.util.Date;

public class Prescription extends Entity {
    private User customer;
    private User doctor;
    private Drug drug;
    private int amount;
    private Date issueDate;
    private Date endDate;
    private Status status;

    public enum Status {
        PROCESSING,
        APPROVED,
        REJECTED
    }

    public Prescription() {
    }

    public Prescription(long prescriptionId, User customer, User doctor, Drug drug, int amount, Date issueDate, Date endDate, Status status) {
        super(prescriptionId);
        this.customer = customer;
        this.doctor = doctor;
        this.drug = drug;
        this.amount = amount;
        this.issueDate = issueDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Prescription(User customer, User doctor, Drug drug, int amount, Status status) {
        this.customer = customer;
        this.doctor = doctor;
        this.drug = drug;
        this.amount = amount;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Prescription prescription = (Prescription) o;
        if (customer != null ? !customer.equals(prescription.customer) : prescription.customer != null) {
            return false;
        }
        if (doctor != null ? !doctor.equals(prescription.doctor) : prescription.doctor != null) {
            return false;
        }
        if (drug != null ? !drug.equals(prescription.drug) : prescription.drug != null) {
            return false;
        }
        if (issueDate != null ? !issueDate.equals(prescription.issueDate) : prescription.issueDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(prescription.endDate) : prescription.endDate != null) {
            return false;
        }
        if (status != null ? !status.equals(prescription.status) : prescription.status != null) {
            return false;
        }
        return amount != prescription.amount;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 17 * (customer != null ? customer.hashCode() : 0);
        result += 13 * (doctor != null ? doctor.hashCode() : 0);
        result += 11 * (drug != null ? drug.hashCode() : 0);
        result += 7 * (issueDate != null ? issueDate.hashCode() : 0);
        result += 5 * (endDate != null ? endDate.hashCode() : 0);
        result += 3 * (status != null ? status.hashCode() : 0);
        result += 2 * amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Prescription{");
        sb.append("prescriptionId=").append(getId());
        sb.append(", customer=").append(customer);
        sb.append(", doctor=").append(doctor);
        sb.append(", drug=").append(drug);
        sb.append(", amount=").append(amount);
        sb.append(", issueDate=").append(issueDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
