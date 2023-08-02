package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class GetStudentsHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        String classroom = request.getParameter("classroom");
        if (Objects.equals(classroom, "-")) {
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseClass.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String students[] = null;

            try {
                ResultSet DBSearch = statement.executeQuery("SELECT students FROM classes WHERE classroom LIKE \"" + classroom + "\" AND organization LIKE \"" + session.getAttribute("Organization") + "\";");
                DBSearch.next();
                students = DBSearch.getString("students").split(", ");
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
