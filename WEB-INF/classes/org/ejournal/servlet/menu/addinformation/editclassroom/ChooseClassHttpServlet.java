package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import java.io.IOException;
import java.sql.*;

public class ChooseClassHttpServlet extends HttpServlet {
    private ClassDAO classDAO;

    public ChooseClassHttpServlet() {
        this.classDAO = new ClassDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classrooms[];

        try {
            classrooms = classDAO.getClassNames((int) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("Classes", classrooms);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
        requestDispatcher.forward(request, response);
    }
}
