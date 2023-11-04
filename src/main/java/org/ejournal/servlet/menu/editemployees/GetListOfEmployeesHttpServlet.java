package org.ejournal.servlet.menu.editemployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.UserDAO;
import org.ejournal.dao.entities.UserEntity;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class GetListOfEmployeesHttpServlet extends HttpServlet {
    private UserDAO userDAO;

    public GetListOfEmployeesHttpServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ArrayList<UserEntity> employees;
        ArrayList<String> listOfEmployees = new ArrayList<>();
        ArrayList<String> listOfRoles = new ArrayList<>();
        ArrayList<String> listOfPrincipals = new ArrayList<>();

        try {
            employees = new ArrayList<>(Arrays.asList(userDAO.getEmployees((int) session.getAttribute("Organization"))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < employees.size(); i++) {
            UserEntity thisEmployee = employees.get(i);
            if(Objects.equals(thisEmployee.getRole(), "principal")){
                listOfPrincipals.add(thisEmployee.getName());
            } else {
                listOfEmployees.add(thisEmployee.getName());
                listOfRoles.add(thisEmployee.getRole());
            }
        }

        for (int i = listOfPrincipals.size()-1; i>=0; i--) {
            listOfEmployees.add(0, listOfPrincipals.get(i));
            listOfRoles.add(0, "principal");
        }

        session.setAttribute("Employees", employees);
        request.setAttribute("ListOfEmployees", listOfEmployees);
        request.setAttribute("ListOfRoles", listOfRoles);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditEmployees/ListOfEmployees.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasInfo = (boolean) request.getAttribute("SomeInfo");
        String info = (String) request.getAttribute("Info");
        if (hasInfo) {
            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", info);
        }
        doGet(request, response);
    }
}
