package org.ejournal.servlet.menu.editemployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SaveChangesHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        int counter = 0;
        try {
            ResultSet DBSearch = statement.executeQuery("SELECT name FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (DBSearch.next()){
                counter++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(request.getParameter("SaveChanges")!=null){
            try {
                ResultSet DBSearch;
                boolean changeRoleOfThisEmployee;
                for (int i = 0; i < counter; i++) {
                    changeRoleOfThisEmployee = true;
                    DBSearch = statement.executeQuery("SELECT role FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND name LIKE \"" + request.getParameter("employee" + i) + "\";");
                    DBSearch.next();
                    String role = DBSearch.getString("role");
                    if(Objects.equals(role, "principal")) {
                        String newRole = request.getParameter("JobTitle" + i);
                        if (!role.equals(newRole)) {
                            ArrayList<String> listOfRoles = new ArrayList<>();
                            DBSearch = statement.executeQuery("SELECT role FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
                            while (DBSearch.next()) {
                                listOfRoles.add(DBSearch.getString("role"));
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

                    if(changeRoleOfThisEmployee) {
                        statement.executeUpdate("UPDATE users SET role = '" + request.getParameter("JobTitle" + i) + "' WHERE name = '" + request.getParameter("employee" + i) + "' AND organization LIKE '" + session.getAttribute("Organization") + "';");
                    } else {
                        request.setAttribute("SomeInfo", true);
                        request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/Employees");
                        requestDispatcher.forward(request, response);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditEmployees/UpdatedEmployees.html");
            requestDispatcher.forward(request, response);
        } else {
            for (int i = 0; i < counter; i++) {
                if(request.getParameter("Delete" + i)!=null){
                    try {
                        boolean deleteThisEmployee = true;

                        ResultSet DBSearch = statement.executeQuery("SELECT role FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND name LIKE \"" + request.getParameter("employee" + i) + "\";");
                        DBSearch.next();
                        String role = DBSearch.getString("role");
                        if(Objects.equals(role, "principal")){
                            ArrayList<String> listOfRoles = new ArrayList<>();
                            for (int j = 0; j < counter; j++) {
                                DBSearch = statement.executeQuery("SELECT role FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND name LIKE \"" + request.getParameter("employee" + j) + "\";");
                                DBSearch.next();
                                listOfRoles.add(DBSearch.getString("role"));
                            }
                            listOfRoles.remove(role);

                            if(listOfRoles.size()==0){
                                deleteThisEmployee = false;
                            } else {
                                for (int j = 0; j < listOfRoles.size(); j++) {
                                    if(Objects.equals(listOfRoles.get(j), "principal")){
                                        deleteThisEmployee = true;
                                        break;
                                    } else {
                                        deleteThisEmployee = false;
                                    }
                                }
                            }
                        }

                        if(deleteThisEmployee) {
                            statement.executeUpdate("UPDATE users SET organization = 'null' WHERE name = '" + request.getParameter("employee" + i) + "' AND organization LIKE '" + session.getAttribute("Organization") + "';");
                        } else {
                            request.setAttribute("SomeInfo", true);
                            request.setAttribute("Info", "Призначте іншого директора перед видаленням цього");

                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/Employees");
                            requestDispatcher.forward(request, response);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
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
