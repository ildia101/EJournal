package org.ejournal.servlet.menu.editemployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.UsersDAO;
import org.ejournal.dao.entities.UserEntity;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SaveChangesHttpServlet extends HttpServlet {
    private UsersDAO usersDAO;

    public SaveChangesHttpServlet() throws SQLException {
        this.usersDAO = new UsersDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<UserEntity> employees = null;
        try {
            employees = usersDAO.getEmployees((String) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (request.getParameter("SaveChanges") != null) {
            boolean changeRoleOfThisEmployee;
            for (int i = 0; i < employees.size(); i++) {
                changeRoleOfThisEmployee = true;
                UserEntity thisEmployee = null;
                for (int j = 0; j < employees.size(); j++) {
                    thisEmployee = employees.get(j);
                    if (Objects.equals(thisEmployee.getName(), request.getParameter("employee" + i))) {
                        break;
                    }
                }
                String role = thisEmployee.getRole();
                if (Objects.equals(role, "principal")) {
                    String newRole = request.getParameter("JobTitle" + i);
                    if (!role.equals(newRole)) {
                        ArrayList<String> listOfRoles = new ArrayList<>();
                        for (int j = 0; j < employees.size(); j++) {
                            listOfRoles.add(employees.get(j).getRole());
                        }
                        listOfRoles.remove(role);

                        for (int j = 0; j < listOfRoles.size(); j++) {
                            if (Objects.equals(listOfRoles.get(j), "principal")) {
                                changeRoleOfThisEmployee = true;
                                break;
                            } else {
                                changeRoleOfThisEmployee = false;
                            }
                        }
                    }
                }

                if (changeRoleOfThisEmployee) {
                    try {
                        usersDAO.updateUserRole((String) session.getAttribute("Organization"), request.getParameter("employee" + i), request.getParameter("JobTitle" + i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    request.setAttribute("SomeInfo", true);
                    request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/Employees");
                    requestDispatcher.forward(request, response);
                }
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditEmployees/UpdatedEmployees.html");
            requestDispatcher.forward(request, response);
        } else {
            for (int i = 0; i < employees.size(); i++) {
                if (request.getParameter("Delete" + i) != null) {
                    boolean deleteThisEmployee = true;

                    UserEntity thisEmployee = null;
                    for (int j = 0; j < employees.size(); j++) {
                        thisEmployee = employees.get(j);
                        if (Objects.equals(thisEmployee.getName(), request.getParameter("employee" + i))) {
                            break;
                        }
                    }
                    String role = thisEmployee.getRole();
                    if (Objects.equals(role, "principal")) {
                        ArrayList<String> listOfRoles = new ArrayList<>();
                        for (int j = 0; j < employees.size(); j++) {
                            listOfRoles.add(employees.get(j).getRole());
                        }
                        listOfRoles.remove(role);

                        if (listOfRoles.size() == 0) {
                            deleteThisEmployee = false;
                        } else {
                            for (int j = 0; j < listOfRoles.size(); j++) {
                                if (Objects.equals(listOfRoles.get(j), "principal")) {
                                    deleteThisEmployee = true;
                                    break;
                                } else {
                                    deleteThisEmployee = false;
                                }
                            }
                        }
                    }

                    if (deleteThisEmployee) {
                        for (int j = 0; j < employees.size(); j++) {
                            UserEntity employee = employees.get(j);
                            if(Objects.equals(employee.getName(), request.getParameter("employee" + i))){
                                try {
                                    usersDAO.updateUserOrganization(employee.getEmail(), "null", (String) session.getAttribute("Organization"));
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            }
                        }
                    } else {
                        request.setAttribute("SomeInfo", true);
                        request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/Employees");
                        requestDispatcher.forward(request, response);
                    }

                    break;
                }
            }

            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Співпрацівника видалено успішно");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/Employees");
            requestDispatcher.forward(request, response);
        }

    }
}
