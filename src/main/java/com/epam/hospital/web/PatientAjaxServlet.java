package com.epam.hospital.web;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.jdbc.JdbcPatientRepositoryImpl;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.PatientServiceImpl;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.web.patient.PatientController;
import com.epam.hospital.web.patient.PatientRestController;
import org.apache.logging.log4j.util.StringBuilders;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        response.setContentType("application/json; charset=UTF-8");  //// перенести как окнстанту в JsonUtil class
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(getJsonString(controller.getAllTo()));
        //writer.print("[{id:1, name: \"Sokolov\", surName: \"Petrovich\"}, {id:2, name: \"Sokolov\", surName: \"Petrovich\"}]");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<PatientTo> patients = controller.getAllTo();

        response.setContentType("application/json; charset=UTF-8");  //// перенести как окнстанту в JsonUtil class
        response.setCharacterEncoding("UTF-8");
//        PrintWriter writer = response.getWriter();
//        writer.print(getJsonString(controller.getAllTo()));
//        writer.flush();
        request.setAttribute("data", getJsonString(controller.getAllTo()));
        request.getRequestDispatcher("/patients.jsp").forward(request, response);
    }

    public String getJsonString(List<PatientTo> patients) {  // перенести в JsonUtil class
        StringBuilder result = new StringBuilder("[ ");
        for (PatientTo patientTo : patients) {
            result.append(patientTo.toString() + ", ");
        }
        result = result.deleteCharAt(result.length()-2).append("]");
        return result.toString();
    }

}
