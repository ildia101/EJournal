package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.OrganizationDAO;
import org.ejournal.dao.UserDAO;

public class UpdateYourOrganizationHttpServlet extends HttpServlet {
    private OrganizationDAO organizationDAO;
    private UserDAO userDAO;

    public UpdateYourOrganizationHttpServlet() {
        this.organizationDAO = new OrganizationDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String code = request.getParameter("code");

        try {
            int organizationID = organizationDAO.getOrganizationIdByName(code);
            if(organizationID!=-1){
                userDAO.updateUserOrganization((String) session.getAttribute("Email"), organizationID);
                session.setAttribute("Organization", organizationID);

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
