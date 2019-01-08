package com.epam.hospital.web;

import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.jdbc.JdbcPatientRepositoryImpl;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.PatientServiceImpl;
import com.epam.hospital.web.patient.PatientController;
import com.epam.hospital.web.patient.PatientRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PatientAjaxServlet extends HttpServlet {
    private PatientController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        PatientRepository repository = new JdbcPatientRepositoryImpl();
        PatientService service = new PatientServiceImpl(repository);
        controller = new PatientRestController(service);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int i = 0;
        //request.getRequestDispatcher("/patients.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("patients", controller.getAllTo());
        request.getRequestDispatcher("/patients.jsp").forward(request, response);
    }

}
