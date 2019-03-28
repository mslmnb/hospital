package com.epam.hospital.model;

import com.epam.hospital.model.handbk.HandbkItem;
import org.json.JSONObject;

import static com.epam.hospital.service.TranslationServiceImpl.*;

public class Translation extends BaseEntity implements HavingJsonView {
    private HandbkItem handbkItem;
    private Lang lang;
    private String itemTranslation;

    public Translation(Integer id, HandbkItem handbkItem, String locale, String itemTranslation) {
        super(id);
        this.handbkItem = handbkItem;
        this.lang = new Lang(locale);
        this.itemTranslation = itemTranslation;
    }

    public HandbkItem getHandbkItem() {
        return handbkItem;
    }

    public Lang getLang() {
        return lang;
    }

    public String getItemTranslation() {
        return itemTranslation;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(HANDBK_ITEM_ID_PARAMETER, handbkItem.getId());
        userJsonObj.put(LOCALE_PARAMETER, lang.getLocale());
        userJsonObj.put(TRANSLATION_PARAMETER, itemTranslation);
        return userJsonObj.toString();
    }
}
