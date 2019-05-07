package com.epam.hospital.model.handbk;

import static com.epam.hospital.model.handbk.HandbkType.DIAGNOSIS_TYPE;

/**
 *  The class represents an item of handbook about diagnosis' type.
 */

public class DiagnosisType extends HandbkItem {
    public DiagnosisType(Integer id, String name) {
        super(id, name, DIAGNOSIS_TYPE);
    }
    public DiagnosisType(Integer id) {
        super(id, null, DIAGNOSIS_TYPE);
    }

}
