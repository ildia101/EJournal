package org.ejournal.servlet.menu.editemployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.UserDAO;
import org.ejournal.dao.entities.UserEntity;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SaveChangesHttpServlet extends HttpServlet {
    private UserDAO userDAO;

    public SaveChangesHttpServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<UserEntity> employees = (ArrayList<UserEntity>) session.getAttribute("Employees");

        if (request.getParameter("SaveChanges") != null) {
            boolean changeRoleOfThisEmployee;
            for (int i = 0; i < employees.size(); i++) {
                changeRoleOfThisEmployee = true;
                UserEntity thisEmployee = findEmployee(request, employees, i);
                String role = thisEmployee.getRole();
                if (Objects.equals(role, "principal")) {
                    String newRole = request.getParameter("JobTitle" + i);
                    if (!role.equals(newRole)) {
                        changeRoleOfThisEmployee = checkForAnotherPrincipal(employees, role);
                    }
                }

                if (changeRoleOfThisEmployee) {
                    try {
                        userDAO.updateUserRole(thisEmployee.getEmail(), request.getParameter("JobTitle" + i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    request.setAttribute("SomeInfo", true);
                    request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/Menu/Employees");
                    requestDispatcher.forward(request, response);
                }
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditEmployees/UpdatedEmployees.jsp");
            requestDispatcher.forward(request, response);
        } else {
            for (int i = 0; i < employees.size(); i++) {
                if (request.getParameter("Delete" + i) != null) {
                    boolean deleteThisEmployee = true;

                    UserEntity thisEmployee = findEmployee(request, employees, i);
                    String role = thisEmployee.getRole();
                    if (Objects.equals(role, "principal")) {
                        deleteThisEmployee = checkForAnotherPrincipal(employees, role);
                    }

                    if (deleteThisEmployee) {
                        UserEntity employee = findEmployee(request, employees, i);
                        try {
                            userDAO.updateUserOrganization(employee.getEmail(), -1);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        request.setAttribute("SomeInfo", true);
                        request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/Menu/Employees");
                        requestDispatcher.forward(request, response);
                    }
                    break;

                }
            }

            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Співпрацівника видалено успішно");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/Menu/Employees");
            requestDispatcher.forward(request, response);
        }
    }

    private static UserEntity findEmployee(HttpServletRequest request, ArrayList<UserEntity> employees, int employeeNumber) {
        UserEntity thisEmployee = null;
        for (int j = 0; j < employees.size(); j++) {
            thisEmployee = employees.get(j);
            if (Objects.equals(thisEmployee.getName(), request.getParameter("employee" + employeeNumber))) {
                break;
            }
        }
        return thisEmployee;
    }

    private static boolean checkForAnotherPrincipal(ArrayList<UserEntity> employees, String role) {
        boolean doSmth = true;
        ArrayList<String> listOfRoles = new ArrayList<>();
        for (int j = 0; j < employees.size(); j++) {
            listOfRoles.add(employees.get(j).getRole());
        }
        listOfRoles.remove(role);

        if (listOfRoles.size() == 0) {
            doSmth = false;
        } else {
            for (int j = 0; j < listOfRoles.size(); j++) {
                if (Objects.equals(listOfRoles.get(j), "principal")) {
                    doSmth = true;
                    break;
                } else {
                    doSmth = false;
                }
            }
        }
        return doSmth;
    }
}
