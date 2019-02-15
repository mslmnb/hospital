package com.epam.hospital.model.handbk;

public class PrescriptionType extends HandbkItem {
    public PrescriptionType(Integer id, String name) {
        super(id, name, HandbkType.PRESCRPTN_TYPE);
    }
    public PrescriptionType(Integer id) {
        super(id, null, HandbkType.PRESCRPTN_TYPE);
    }

}
