package org.ejournal.servlet.signin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.ejournal.dao.OrganizationDAO;
import org.ejournal.dao.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Objects;

public class RegisterUserHttpServlet extends HttpServlet {
    private OrganizationDAO organizationDAO;
    private UserDAO userDAO;

    public RegisterUserHttpServlet() {
        this.organizationDAO = new OrganizationDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        HashMap<String, String> savedInfo = new HashMap<>();
        savedInfo.put("Email", email);

        try {
            String role = request.getParameter("role");

            savedInfo.put("Name", name);
            if(Objects.equals(role, "none")){
                request.setAttribute("InvalidData", "������ ���� ������");
                returnError(request, response, savedInfo);
            } else if(name.isEmpty()){
                request.setAttribute("InvalidData", "���� � ϲ� �������");
                returnError(request, response, savedInfo);
            } else if(email.isEmpty()){
                request.setAttribute("InvalidData", "���� � ������� ���������� ����� �������");
                returnError(request, response, savedInfo);
            } else if(password.isEmpty()){
                request.setAttribute("InvalidData", "���� � ������� �������");
                returnError(request, response, savedInfo);
            } else {
                if(Objects.equals(role, "principal")) {
                    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                    StringBuilder code = new StringBuilder();
                    for (int i = 0; i < 6; i++) {
                        code.append(chars.charAt((int) (Math.random() * 62)));
                    }

                    organizationDAO.createOrganization(code.toString());
                    int organizationID = organizationDAO.getOrganizationIdByName(code.toString());
                    userDAO.createUser(organizationID, role, name, email, password);

                    request.setAttribute("Code", code);

                    request.setAttribute("EnterCodePage", true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("GetCode.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute("Role", role);
                    session.setAttribute("Name", name);
                    session.setAttribute("Email", email);
                    session.setAttribute("Password", password);

                    request.setAttribute("EnterCodePage", true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("InputYourCode.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e){
            request.setAttribute("InvalidData", "���������� �� ����� ������� ���������� ����� ��� ��� �������������");
            returnError(request, response, savedInfo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void returnError(HttpServletRequest request, HttpServletResponse response, HashMap<String, String> info) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        request.setAttribute("SavedInfo", info);
        request.setAttribute("Error", true);
        requestDispatcher.forward(request, response);
    }
}