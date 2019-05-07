package com.epam.hospital.model;

/**
 * The interface for import data in json view. Used with classes of model where it's necessary.
 */
public interface HavingJsonView {
    /**
     * Gets data as json string
     * @return the json string
     */
    String getJsonString();
}
