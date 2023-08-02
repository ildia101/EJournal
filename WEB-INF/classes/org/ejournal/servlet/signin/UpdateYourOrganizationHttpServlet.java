package org.ejournal.servlet.signin;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UpdateYourOrganizationHttpServlet extends HttpServlet {
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
                statement.executeUpdate("UPDATE users SET organization = \"" + code + "\" WHERE email = \"" + session.getAttribute("Email") + "\" AND organization = \"null\";");
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
