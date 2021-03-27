package com.epam.Pharmacy.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Drug {
    private int drugId;
    private String drugName;
    private int amount;
    private int dosage;
    private String description;
    boolean needPrescription;
    BigDecimal price;
    private List<String> images;

    public Drug(int drugId, String drugName, int amount, String description, int dosage) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.dosage = dosage;
    }

    public Drug(int drugId, String drugName, int amount, String description, int dosage, BigDecimal price) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.dosage = dosage;
        this.price = price;
    }

    public Drug(int drugId, String drugName, int amount, String description, boolean needPrescription, List<String> imageList) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.images = new ArrayList<>(imageList);
    }

    public Drug(int drugId, String drugName, int amount, String description, boolean needPrescription) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.needPrescription = needPrescription;
    }

    public Drug(String drugName) {
        this.drugName = drugName;
    }


    public Drug(int drugId, String drugName) {
        this.drugId = drugId;
        this.drugName = drugName;
    }

    public Drug(int drugId, String drugName, int amount, String description, boolean needPrescription, int dosage, List<String> images) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.images = new ArrayList<>(images);
    }

    public Drug(int drugId, String drugName, int amount, String description, boolean needPrescription, int dosage, BigDecimal price, List<String> images) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
        this.images = new ArrayList<>(images);
    }

    public Drug(int drugId, String drugName, int amount, String description, boolean needPrescription, int dosage, BigDecimal price) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.amount = amount;
        this.description = description;
        this.needPrescription = needPrescription;
        this.dosage = dosage;
        this.price = price;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Drug drug = (Drug) o;
        if (drugName != null ? !drugName.equals(drug.drugName) : drug.drugName != null) {
            return false;
        }
        if (amount != drug.amount) {
            return false;
        }
        if (images != null ? !images.equals(drug.images) : drug.images != null) {
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
        if (price != drug.price) {
            return false;
        }
        return drugId == drug.drugId;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 31 * (drugName != null ? drugName.hashCode() : 0);
        result += 29 * amount;
        result += 23 * (images != null ? images.hashCode() : 0);
        result += 19 * (description != null ? description.hashCode() : 0);
        result += 17 * Boolean.hashCode(needPrescription);
        result += 11 * drugId;
        result += 7 * price.hashCode();
        result += dosage;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Drug{");
        sb.append("drugId=").append(drugId);
        sb.append(", drugName='").append(drugName).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", dosage=").append(dosage);
        sb.append(", description='").append(description).append('\'');
        sb.append(", needPrescription=").append(needPrescription);
        sb.append(", price=").append(price);
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}
