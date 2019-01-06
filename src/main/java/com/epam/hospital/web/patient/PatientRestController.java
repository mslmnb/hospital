package com.epam.hospital.web.patient;

import com.epam.hospital.service.PatientService;

public class PatientRestController extends AbstractPatientController {

    public PatientRestController(PatientService service) {
        super(service);
    }
}
