package com.epam.hospital.util;

import com.epam.hospital.model.Patient;
import com.epam.hospital.to.PatientTo;

import java.util.List;

public class PatientUtil {
    public static PatientTo createTo(Patient patient) {
        return new PatientTo(patient.getId(), patient.getName(), patient.getSurName());
    }
}
