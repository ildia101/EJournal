package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.OrganizationDAO;
import org.ejournal.dao.UserDAO;

public class Register2ndStepHttpServlet extends HttpServlet {
    private OrganizationDAO organizationDAO;
    private UserDAO userDAO;

    public Register2ndStepHttpServlet() {
        this.organizationDAO = new OrganizationDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String code = request.getParameter("code");

        try {
            Integer organizationID = organizationDAO.getOrganizationIdByName(code);
            if(organizationID!=null){
                try {
                    registerUser(request, response, session, organizationID);
                } catch (SQLIntegrityConstraintViolationException exception){
                    request.setAttribute("Error", true);
                    request.setAttribute("InvalidData", "Користувач із такою адресою електронної пошти вже був зареєстрований");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request, response);
                }
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

    private void registerUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer organizationID) throws SQLException, ServletException, IOException {
        String role = (String) session.getAttribute("Role");
        String name = (String) session.getAttribute("Name");
        String email = (String) session.getAttribute("Email");
        String password = (String) session.getAttribute("Password");

        userDAO.createUser(organizationID, role, name, email, password);

        session.removeAttribute("Role");
        session.removeAttribute("Name");
        session.removeAttribute("Email");
        session.removeAttribute("Password");

        request.setAttribute("YouAreRegisteredUser", true);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("RegisteredUser.jsp");
        requestDispatcher.forward(request, response);
    }
}
