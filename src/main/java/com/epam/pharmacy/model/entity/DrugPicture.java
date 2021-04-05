package com.epam.pharmacy.model.entity;

public class DrugPicture extends Entity {
    private String drugPicture;

    public DrugPicture(long pictureDrugId, String drugPicture) {
        super(pictureDrugId);
        this.drugPicture = drugPicture;
    }

    public String getDrugPicture() {
        return drugPicture;
    }

    public void setDrugPicture(String drugPicture) {
        this.drugPicture = drugPicture;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        DrugPicture that = (DrugPicture) o;
        return drugPicture != null ? !drugPicture.equals(that.drugPicture) : that.drugPicture != null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 2 * drugPicture.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DrugPicture{");
        sb.append("drugPictureId='").append(getId()).append('\'');
        sb.append("drugPicture='").append(drugPicture).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
