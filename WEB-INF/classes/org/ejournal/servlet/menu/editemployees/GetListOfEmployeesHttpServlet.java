package org.ejournal.servlet.menu.editemployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.UsersDAO;
import org.ejournal.dao.entities.UserEntity;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.util.*;
import java.util.stream.Stream;

public class GetListOfEmployeesHttpServlet extends HttpServlet {
    private UsersDAO usersDAO;

    public GetListOfEmployeesHttpServlet() throws SQLException {
        this.usersDAO = new UsersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ArrayList<UserEntity> employees;
        ArrayList<String> listOfEmployees = new ArrayList<>();
        ArrayList<String> listOfRoles = new ArrayList<>();
        ArrayList<String> listOfPrincipals = new ArrayList<>();

        try {
            employees = usersDAO.getEmployees((String) session.getAttribute("Organization"));
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

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(listOfEmployees.toArray(new String[0])).sorted(collator);
        listOfEmployees = new ArrayList<>(Arrays.asList(str.toArray(String[]::new)));
        
        str = Stream.of(listOfPrincipals.toArray(new String[0])).sorted(collator);
        listOfPrincipals = new ArrayList<>(Arrays.asList(str.toArray(String[]::new)));

        for (int i = listOfPrincipals.size()-1; i>=0; i--) {
            listOfEmployees.add(0, listOfPrincipals.get(i));
            listOfRoles.add(0, "principal");
        }

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
