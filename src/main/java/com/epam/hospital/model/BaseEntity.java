package com.epam.hospital.model;

/**
 * The class of the entity with an id property. Used as a base class for entity
 * needing this property.
 */
public abstract class BaseEntity implements HavingJsonView{
    private Integer id;

    BaseEntity() {
    }

    BaseEntity(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return (getId() == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return getJsonString().equals(that.getJsonString());
    }

    @Override
    public int hashCode() {
        return getJsonString().hashCode();
    }
}