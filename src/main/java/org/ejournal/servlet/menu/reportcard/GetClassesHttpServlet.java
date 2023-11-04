package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import java.io.IOException;
import java.sql.SQLException;

public class GetClassesHttpServlet extends HttpServlet {
    private ClassDAO classDAO;

    public GetClassesHttpServlet() {
        this.classDAO = new ClassDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classes[];
        try {
            classes = classDAO.getClassNames((int) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.setAttribute("Classes", classes);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseClass.jsp");
        requestDispatcher.forward(request, response);
    }
}
