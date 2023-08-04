package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class GetStudentsHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public GetStudentsHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classroom = request.getParameter("classroom");
        if (Objects.equals(classroom, "-")) {
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseClass.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String students[];
            try {
                students = classesDAO.getClassStudents((String) session.getAttribute("Organization"), classroom);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }

            session.setAttribute("Classroom", classroom);
            session.setAttribute("StudentsFromThisClass", students);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseStudent.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
