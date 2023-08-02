package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Register2ndStepHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement)session.getAttribute("DBAccess");

        String code = request.getParameter("code");
        boolean RealOrganization = false;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT organization FROM users;");
            while (resultSet.next()){
                if(Objects.equals(resultSet.getString("organization"), code)){
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

                    statement.executeUpdate("INSERT INTO users VALUES('" + code + "', '" + role + "', '" + name + "', '" + email + "', '" + password + "')");

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
