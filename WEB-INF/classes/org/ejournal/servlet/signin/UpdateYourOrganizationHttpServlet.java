package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.UsersDAO;

public class UpdateYourOrganizationHttpServlet extends HttpServlet {
    private UsersDAO usersDAO;

    public UpdateYourOrganizationHttpServlet() throws SQLException {
        this.usersDAO = new UsersDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String code = request.getParameter("code");
        boolean RealOrganization = false;

        try {
            String allOrganizations[] = usersDAO.getAllOrganizations();
            for (int i = 0; i < allOrganizations.length; i++) {
                if(Objects.equals(allOrganizations[i], code)){
                    RealOrganization = true;
                    break;
                }
            }

            if(RealOrganization){
                usersDAO.updateUserOrganization((String) session.getAttribute("Email"), code, "null");
                session.setAttribute("Organization", code);

                session.removeAttribute("Email");

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("UserInSystem.jsp");
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute("InvalidData", "Такого коду закладу не існує");

                request.setAttribute("EnterCodePage", true);
                request.setAttribute("Error", true);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("InputYourCode.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
