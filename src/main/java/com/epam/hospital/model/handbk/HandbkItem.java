package com.epam.hospital.model.handbk;

import com.epam.hospital.model.NamedEntity;
import org.json.JSONObject;

import static com.epam.hospital.service.HandbkItemServiceImpl.*;

/**
 * The class represents an item of a handbook.
 */
public class HandbkItem extends NamedEntity {

    /**
     * the type of handbook
     */
    private HandbkType type;

    public HandbkItem(Integer id, String name, HandbkType type) {
        super(id, name);
        this.type = type;
    }

    public HandbkItem(Integer id) {
        super(id, null);
    }

    public HandbkType getType() {
        return type;
    }

    @Override
    public String getJsonString() {
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(NAME_PARAMETER, getName());
        return userJsonObj.toString();
    }
}
