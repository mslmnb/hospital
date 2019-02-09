package com.epam.hospital.model.handbk;

import com.epam.hospital.model.BaseEntity;
import com.epam.hospital.model.HavingJsonView;
import org.json.JSONObject;

import static com.epam.hospital.service.TranslationService.*;

public class Translation extends BaseEntity implements HavingJsonView {
    HandbkItem handbkItem;
    Lang lang;
    String itemTranslation;

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
