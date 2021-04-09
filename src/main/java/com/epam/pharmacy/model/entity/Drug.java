package com.epam.pharmacy.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Entity} class represents an {@link Drug}.
 *
 * @author Yauheni Tsitou.
 */
public class Drug extends Entity {

    /**
     * String value containing the name of this drug.
     */
    private String drugName;

    /**
     * Int value containing the amount of this drug.
     */
    private int drugAmount;

    /**
     * Int value containing dosage of this drug.
     */
    private int dosage;

    /**
     * String value containing the description of this drug.
     */
    private String description;

    /**
     * boolean value indicating whether a prescription is needed for this drug.
     */
    private boolean needPrescription;

    /**
     * BigDecimal value containing the price of this drug.
     */
    private BigDecimal price;

    /**
     * List value containing links to objects pictures drugs.
     */
    private List<DrugPicture> drugPictureList;

    /**
     * Constructs an {@link Drug} object with given drug id, name, amount, description, dosage, price,pictureList
     *
     * @param drugId          drug's id long value
     * @param drugName        String object of drug's name.
     * @param drugAmount      drug's amount int value.
     * @param description     String object of drug's description.
     * @param dosage          drug's dosage int value.
     * @param price           drug's price BigDecimal value.
     * @param drugPictureList drug's pictures List value
     */
    public Drug(long drugId, String drugName, int drugAmount, String description, boolean needPrescription, int dosage, BigDecimal price, List<DrugPicture> drugPictureList) {
        super(drugId);
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
        this.drugPictureList = new ArrayList<>(drugPictureList);
    }

    /**
     * Constructs an {@link Drug} object with given drug id, name, amount, description,needPrescription, dosage, price.
     *
     * @param drugId           drug's id long value
     * @param drugName         String object of drug's name.
     * @param drugAmount       drug's amount int value.
     * @param description      String object of drug's description.
     * @param needPrescription drug's needPrescription boolean value.
     * @param dosage           drug's dosage int value.
     * @param price            drug's price BigDecimal value.
     */
    public Drug(long drugId, String drugName, int drugAmount, String description, boolean needPrescription, int dosage, BigDecimal price) {
        super(drugId);
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
    }

    /**
     * Constructs an {@link Drug} object with given name, amount, description,needPrescription, dosage, price.
     *
     * @param drugName         String object of drug's name.
     * @param drugAmount       drug's amount int value.
     * @param description      String object of drug's description.
     * @param needPrescription drug's needPrescription boolean value.
     * @param dosage           drug's dosage int value.
     * @param price            drug's price BigDecimal value.
     */
    public Drug(String drugName, int drugAmount, String description, boolean needPrescription, int dosage, BigDecimal price) {
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
    }

    /**
     * Constructs an {@link Drug} object with given drug id.
     *
     * @param drugId drug's id long value
     */
    public Drug(int drugId) {
        super(drugId);
    }

    /**
     * Getter method of drug's name.
     *
     * @return drug's name String value.
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * Setter method of drug's name.
     *
     * @param drugName drug's name String value.
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /**
     * Getter method of drug's amount.
     *
     * @return drug's amount int value.
     */
    public int getDrugAmount() {
        return drugAmount;
    }

    /**
     * Setter method of drug's amount.
     *
     * @param drugAmount drug's amount int value.
     */
    public void setDrugAmount(int drugAmount) {
        this.drugAmount = drugAmount;
    }

    /**
     * Getter method of drug image list.
     *
     * @return drug image list List value.
     */
    public List<DrugPicture> getDrugPictureList() {
        return new ArrayList<>(drugPictureList);
    }

    /**
     * Setter method of drug image list.
     *
     * @param drugPictureList drug image list List value.
     */
    public void setDrugPictureList(List<DrugPicture> drugPictureList) {
        this.drugPictureList = drugPictureList;
    }

    /**
     * Getter method of drug's description.
     *
     * @return drug's description String value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method of drug's description.
     *
     * @param description drug's description String value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method of drug's needPrescription.
     *
     * @return drug's needPrescription boolean value.
     */
    public boolean isNeedPrescription() {
        return needPrescription;
    }

    /**
     * Setter method of drug's needPrescription.
     *
     * @param needPrescription drug's needPrescription boolean value.
     */
    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    /**
     * Getter method of drug's dosage.
     *
     * @return drug's dosage int value.
     */
    public int getDosage() {
        return dosage;
    }

    /**
     * Setter method of drug's dosage.
     *
     * @param dosage drug's dosage int value.
     */
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    /**
     * Getter method of drug's price.
     *
     * @return drug's price BigDecimal value.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter method of drug's price.
     *
     * @param price drug's price BigDecimal value.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Drug drug = (Drug) o;
        if (drugName != null ? !drugName.equals(drug.drugName) : drug.drugName != null) {
            return false;
        }
        if (drugAmount != drug.drugAmount) {
            return false;
        }
        if (drugPictureList != null ? !drugPictureList.equals(drug.drugPictureList) : drug.drugPictureList != null) {
            return false;
        }
        if (description != null ? !description.equals(drug.description) : drug.description != null) {
            return false;
        }
        if (needPrescription != drug.needPrescription) {
            return false;
        }
        if (dosage != drug.dosage) {
            return false;
        }
        return price.equals(drug.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 19 * (drugName != null ? drugName.hashCode() : 0);
        result += 17 * drugAmount;
        result += 13 * (drugPictureList != null ? drugPictureList.hashCode() : 0);
        result += 11 * (description != null ? description.hashCode() : 0);
        result += 7 * Boolean.hashCode(needPrescription);
        result += 3 * price.hashCode();
        result += 2 * dosage;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Drug{");
        sb.append("drugId=").append(getId()).append('\'');
        sb.append(", drugName='").append(drugName).append('\'');
        sb.append(", amount=").append(drugAmount);
        sb.append(", dosage=").append(dosage);
        sb.append(", description='").append(description).append('\'');
        sb.append(", needPrescription=").append(needPrescription);
        sb.append(", price=").append(price);
        sb.append(", images=").append(drugPictureList);
        sb.append('}');
        return sb.toString();
    }
}
