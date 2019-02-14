package com.epam.hospital.model.handbk;

import static com.epam.hospital.model.handbk.HandbkType.DIAGNOSIS;

public class Diagnosis extends HandbkItem {
    public Diagnosis(Integer id, String name) {
        super(id, name, DIAGNOSIS);
    }

    public Diagnosis(Integer id) {
        super(id, null, DIAGNOSIS);
    }
}
