package com.epam.pharmacy.model.entity;

import java.util.Date;

/**
 * {@link Entity} class represents an {@link Prescription}.
 *
 * @author Yauheni Tsitou.
 */
public class Prescription extends Entity {
    /**
     * {@link User} value containing customer.
     */
    private User customer;
    /**
     * {@link User} value containing doctor.
     */
    private User doctor;
    /**
     * {@link Drug} value containing drug.
     */
    private Drug drug;

    /**
     * Int value containing drug.
     */
    private int amount;

    /**
     * Date value containing the date of issue of the prescription.
     */
    private Date issueDate;

    /**
     * containing the expiration date of the prescription.
     */
    private Date endDate;

    /**
     * {@link Status} value containing the prescription of the recipe at the moment.
     */
    private Status status;

    /**
     * Enumeration of the prescription statuses.
     *
     * @author Yauheni Tsitou.
     */
    public enum Status {
        PROCESSING,
        APPROVED,
        REJECTED
    }

    /**
     * Constructs an {@link Prescription} object with given prescription customer, drug,amount, issueDate, endDate,status.
     *
     * @param customer  {@link User} object of prescription's customer.
     * @param doctor    {@link User} object of prescription's doctor.
     * @param drug      {@link Drug} object of prescription's drug.
     * @param amount    int value of prescription's amount.
     * @param issueDate {@link Date} object of prescription's date of issue of the prescription.
     * @param endDate   {@link Date} object of prescription's expiration date of the prescription.
     * @param status    {@link Status} object of prescription's status.
     */
    public Prescription(User customer, User doctor, Drug drug, int amount, Date issueDate, Date endDate, Status status) {
        this.customer = customer;
        this.doctor = doctor;
        this.drug = drug;
        this.amount = amount;
        this.issueDate = issueDate;
        this.endDate = endDate;
        this.status = status;
    }

    /**
     * Constructs an {@link Prescription} object with given prescription ID, customer, drug,amount, issueDate, endDate,status.
     *
     * @param prescriptionId long value of prescription's ID.
     * @param customer       {@link User} object of prescription's customer.
     * @param doctor         {@link User} object of prescription's doctor.
     * @param drug           {@link Drug} object of prescription's drug.
     * @param amount         int value of prescription's amount.
     * @param issueDate      {@link Date} object of prescription's date of issue of the prescription.
     * @param endDate        {@link Date} object of prescription's expiration date of the prescription.
     * @param status         {@link Status} object of prescription's status.
     */
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

    /**
     * Getter method of prescription's customer.
     *
     * @return prescription's customer {@link User} object.
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Setter method of prescription's customer.
     *
     * @param customer prescription's customer {@link User} object.
     */
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * Getter method of prescription's doctor.
     *
     * @return prescription's doctor {@link User} value.
     */
    public User getDoctor() {
        return doctor;
    }

    /**
     * Setter method of prescription's doctor.
     *
     * @param doctor prescription's doctor {@link User} object.
     */
    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    /**
     * Getter method of prescription's drug.
     *
     * @return prescription's drug {@link Drug} object.
     */
    public Drug getDrug() {
        return drug;
    }

    /**
     * Setter method of prescription's drug.
     *
     * @param drug prescription's drug {@link Drug} object.
     */
    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    /**
     * Getter method of prescription's amount.
     *
     * @return prescription's amount int value.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter method of  prescription's amount.
     *
     * @param amount prescription's amount int value.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Getter method of prescription's issueDate.
     *
     * @return prescription's issueDate {@link Date} object.
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * Setter method of  prescription's issueDate.
     *
     * @param issueDate prescription's issueDate {@link Date} object.
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Getter method of prescription's endDate.
     *
     * @return prescription's endDate {@link Date} object.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter method of  prescription's endDate
     *
     * @param endDate prescription's endDate {@link Date} object.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Setter method of  prescription's status.
     *
     * @param status prescription's status {@link Status} object.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter method of prescription's status.
     *
     * @return prescription's status {@link Status} object.
     */
    public Status getStatus() {
        return status;
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
        return amount == prescription.amount;
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
