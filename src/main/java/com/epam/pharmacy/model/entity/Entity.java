package com.epam.pharmacy.model.entity;

/**
 * Abstract entity class. Class {@code Entity} is the root of the entity class hierarchy.
 *
 * @author Yauheni Tsitou.
 */
public class Entity {
    /**
     * Constructs an Entity object.
     */
    public Entity() {
    }

    /**
     * Constructs an Entity object with a given ID.
     *
     * @param id int value of entity's ID.
     */
    public Entity(long id) {
        this.id = id;
    }

    /**
     *Entity identification number.
     */
    private long id;

    /**
     * Getter method of entity's ID.
     *
     * @return entity's ID long value.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method of entity's ID.
     *
     * @param id entity's ID int value.
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
