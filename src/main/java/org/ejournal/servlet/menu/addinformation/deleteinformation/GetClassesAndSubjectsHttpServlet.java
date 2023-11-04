package org.ejournal.servlet.menu.addinformation.deleteinformation;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import org.ejournal.dao.SubjectDAO;

public class GetClassesAndSubjectsHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private SubjectDAO subjectDAO;

    public GetClassesAndSubjectsHttpServlet() {
        this.classDAO = new ClassDAO();
        this.subjectDAO = new SubjectDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classesList[];
        String subjectsList[];

        try {
            classesList = classDAO.getClassNames((int) session.getAttribute("Organization"));
            subjectsList = subjectDAO.getSubjectNames((int) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("Classes", classesList);
        session.setAttribute("Subjects", subjectsList);

        RequestDispatcher requestDispatcher = null;
        String requestPathArr[] = request.getRequestURI().split("/");

        switch (requestPathArr[requestPathArr.length-1]) {
            case "ChooseClass":
                requestDispatcher = request.getRequestDispatcher("DeleteClass/ChooseClass.jsp");
                break;
            case "ChooseSubject":
                requestDispatcher = request.getRequestDispatcher("DeleteSubject/ChooseSubject.jsp");
                break;
            case "ChooseClassGrades":
                requestDispatcher = request.getRequestDispatcher("DeleteMarks/ChooseClass.jsp");
                break;
        }

        requestDispatcher.forward(request, response);
    }
}
