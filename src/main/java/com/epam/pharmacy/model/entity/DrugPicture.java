package com.epam.pharmacy.model.entity;

/**
 * {@link Entity} class represents an {@link DrugPicture}.
 *
 * @author Yauheni Tsitou.
 */
public class DrugPicture extends Entity {

    /**
     * String value containing string representation of a drug picture.
     */
    private String drugPicture;

    /**
     * Constructs an {@link DrugPicture} object with given drugPicture id, picture.
     *
     * @param pictureDrugId
     * @param drugPicture
     */
    public DrugPicture(long pictureDrugId, String drugPicture) {
        super(pictureDrugId);
        this.drugPicture = drugPicture;
    }

    /**
     * Getter method of drug picture.
     *
     * @return drug picture String value.
     */
    public String getDrugPicture() {
        return drugPicture;
    }

    /**
     * Setter method of drug picture.
     *
     * @param drugPicture drug picture String value.
     */
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
