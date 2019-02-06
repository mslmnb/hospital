package com.epam.hospital.model;

public class NamedEntity extends BaseEntity {

    private String name;

    protected NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}