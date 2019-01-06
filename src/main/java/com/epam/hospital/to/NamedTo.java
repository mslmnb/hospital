package com.epam.hospital.to;

public class NamedTo extends BaseTo {
    private String name;

    protected NamedTo() {
    }

    protected NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
