package com.epam.hospital.model.handbk;

import static com.epam.hospital.model.handbk.HandbkType.POSITION;

/**
 *  The class represents an item of handbook about positions.
 */

public class Position extends HandbkItem {

    public Position(Integer id, String name) {
        super(id, name, POSITION);
    }

    public Position(Integer id) {
        super(id, null, POSITION);
    }
}
