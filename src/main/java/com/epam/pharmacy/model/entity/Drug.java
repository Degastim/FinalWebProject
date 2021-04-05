package com.epam.pharmacy.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Drug extends Entity {
    private String drugName;
    private int drugAmount;
    private int dosage;
    private String description;
    private boolean needPrescription;
    private BigDecimal price;
    private List<DrugPicture> drugPictureList;

    public Drug(long drugId, String drugName, int drugAmount, String description, int dosage, BigDecimal price) {
        super(drugId);
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.dosage = dosage;
        this.price = price;
    }

    public Drug(String drugName) {
        this.drugName = drugName;
    }

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

    public Drug(long drugId, String drugName, int drugAmount, String description, boolean needPrescription, int dosage, BigDecimal price) {
        super(drugId);
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
    }

    public Drug(String drugName, int drugAmount, String description, boolean needPrescription, int dosage, BigDecimal price) {
        this.drugName = drugName;
        this.drugAmount = drugAmount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
    }

    public Drug(int drugId) {
        super(drugId);
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getDrugAmount() {
        return drugAmount;
    }

    public void setDrugAmount(int drugAmount) {
        this.drugAmount = drugAmount;
    }

    public List<DrugPicture> getDrugPictureList() {
        return new ArrayList<>(drugPictureList);
    }

    public void setDrugPictureList(List<DrugPicture> drugPictureList) {
        this.drugPictureList = drugPictureList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public BigDecimal getPrice() {
        return price;
    }

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
