package com.epam.pharmacy.model.entity;

/**
 * {@code Entity} class represents an {@code DrugOrder}.
 *
 * @author Yauheni Tsitou.
 */
public class DrugOrder extends Entity {

    /**
     * {@link User} value containing customer.
     */
    private User customer;

    /**
     * {@link Drug} value containing drug.
     */
    private Drug drug;

    /**
     * Int value containing the number of drugs for which the order was made.
     */
    private int drugsNumber;

    /**
     * Status value containing drug order status.
     */
    private Status status;

    /**
     * Enumeration of the drug order statuses.
     *
     * @author Yauheni Tsitou.
     */
    public enum Status {
        PROCESSING,
        APPROVED,
        REJECTED
    }

    /**
     * Constructs an {@link DrugOrder} object with given drug id, name, amount, description, dosage, price,pictureList
     *
     * @param customer    customer {@link User} value.
     * @param drug        drug {@link Drug} value.
     * @param drugsNumber drug's number int value.
     * @param status      status {@link DrugOrder.Status} value.
     */
    public DrugOrder(User customer, Drug drug, int drugsNumber, DrugOrder.Status status) {
        this.customer = customer;
        this.drug = drug;
        this.drugsNumber = drugsNumber;
        this.status = status;
    }

    /**
     * Constructs an {@link DrugOrder} object with given drug id, name, amount, description, dosage, price,pictureList
     *
     * @param drugOrderId drug's id long value
     * @param customer    String object of drug's name.
     * @param drug        drug's amount int value.
     * @param drugsNumber String object of drug's description.
     * @param status      drug's dosage int value.
     */
    public DrugOrder(long drugOrderId, User customer, Drug drug, int drugsNumber, DrugOrder.Status status) {
        super(drugOrderId);
        this.customer = customer;
        this.drug = drug;
        this.drugsNumber = drugsNumber;
        this.status = status;
    }

    /**
     * Getter method of drug order's customer.
     *
     * @return customer {@link User} value.
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Setter method of drug order's customer.
     *
     * @param customer customer {@link User} value.
     */
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * Getter method of drug order's drug.
     *
     * @return drug {@link Drug} value.
     */
    public Drug getDrug() {
        return drug;
    }

    /**
     * Setter method of drug order's drug.
     *
     * @param drug rug {@link Drug} value.
     */
    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    /**
     * Getter method of drug order's drug quantity.
     *
     * @return drug order's drug quantity int value.
     */
    public int getDrugsNumber() {
        return drugsNumber;
    }

    /**
     * Setter method of drug order's drug quantity.
     *
     * @param drugsNumber drug order's drug quantity int value.
     */
    public void setDrugsNumber(int drugsNumber) {
        this.drugsNumber = drugsNumber;
    }

    /**
     * Getter method of drug's name.
     *
     * @return drug order's status {@link Status} value.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter method of drug's name.
     *
     * @param status drug order's status {@link Status} value.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        DrugOrder order = (DrugOrder) o;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) {
            return false;
        }
        if (drug != null ? !drug.equals(order.drug) : order.drug != null) {
            return false;
        }
        if (status != order.status) {
            return false;
        }
        return drugsNumber == order.drugsNumber;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 7 * drugsNumber;
        result += 5 * customer.hashCode();
        result += 3 * drug.hashCode();
        result += 2 * status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DrugOrder{");
        sb.append("customer=").append(customer);
        sb.append(", drug=").append(drug);
        sb.append(", drugsNumber=").append(drugsNumber);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
