package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.UsersDAO;

public class Register2ndStepHttpServlet extends HttpServlet {
    private UsersDAO usersDAO;

    public Register2ndStepHttpServlet() throws SQLException {
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
                try {
                    String role = (String)session.getAttribute("Role");
                    String name = (String)session.getAttribute("Name");
                    String email = (String)session.getAttribute("Email");
                    String password = (String)session.getAttribute("Password");

                    usersDAO.createUser(code, role, name, email, password);

                    session.removeAttribute("Role");
                    session.removeAttribute("Name");
                    session.removeAttribute("Email");
                    session.removeAttribute("Password");

                    request.setAttribute("YouAreRegisteredUser", true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("RegisteredUser.jsp");
                    requestDispatcher.forward(request, response);
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
}
