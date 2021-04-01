package com.epam.pharmacy.model.entity;

import java.util.Date;

public class Prescription {
    private long prescriptionId;
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
        this.prescriptionId = prescriptionId;
        this.customer = customer;
        this.doctor = doctor;
        this.drug = drug;
        this.amount = amount;
        this.issueDate = issueDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
        if (amount != prescription.amount) {
            return false;
        }
        if (status != null ? !status.equals(prescription.status) : prescription.status != null) {
            return false;
        }
        return prescription.prescriptionId == prescriptionId;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 31 * (customer != null ? customer.hashCode() : 0);
        result += 29 * (doctor != null ? doctor.hashCode() : 0);
        result += 23 * (drug != null ? drug.hashCode() : 0);
        result += 19 * (issueDate != null ? issueDate.hashCode() : 0);
        result += 17 * (endDate != null ? endDate.hashCode() : 0);
        result += 13 * (status != null ? status.hashCode() : 0);
        result += 11 * Long.hashCode(prescriptionId);
        result += amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Prescription{");
        sb.append("prescriptionId=").append(prescriptionId);
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
