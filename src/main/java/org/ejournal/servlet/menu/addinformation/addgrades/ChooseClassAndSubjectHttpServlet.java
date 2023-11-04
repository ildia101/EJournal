package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import org.ejournal.dao.SubjectDAO;
import java.io.IOException;
import java.sql.*;

public class ChooseClassAndSubjectHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private SubjectDAO subjectDAO;

    public ChooseClassAndSubjectHttpServlet() {
        this.classDAO = new ClassDAO();
        this.subjectDAO = new SubjectDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classrooms[];
        String subjects[];

        session.setAttribute("NumberOfPage", 1);

        try {
            classrooms = classDAO.getClassNames((int) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("Classes", classrooms);

        try {
            subjects = subjectDAO.getSubjectNames((int) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("Subjects", subjects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/ChooseClassAndSubject.jsp");
        requestDispatcher.forward(request, response);
    }
}
