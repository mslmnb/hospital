package com.epam.hospital.model;

import org.json.JSONObject;

import static com.epam.hospital.service.LangServiceImpl.ID_PARAMETER;
import static com.epam.hospital.service.LangServiceImpl.NAME_PARAMETER;

/**
 *  The class represents a language.
 */

public class Lang implements HavingJsonView{

    /** a language
     * example: "en" (English), "ru" (English)
     */
    private final String locale;

    public Lang(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, locale);
        userJsonObj.put(NAME_PARAMETER, locale);
        return userJsonObj.toString();
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
