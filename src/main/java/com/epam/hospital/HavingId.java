package com.epam.hospital;

public interface HavingId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return (getId() == null);
    }
}
