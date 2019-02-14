package com.epam.hospital.model.handbk;

import com.epam.hospital.model.HavingJsonView;
import org.json.JSONObject;

import static com.epam.hospital.service.LangService.ID_PARAMETER;
import static com.epam.hospital.service.LangService.NAME_PARAMETER;

public class Lang implements HavingJsonView{
    private String locale;

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
}
