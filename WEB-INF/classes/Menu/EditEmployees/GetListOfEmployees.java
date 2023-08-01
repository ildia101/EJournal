package Menu.EditEmployees;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.util.*;
import java.util.stream.Stream;

public class GetListOfEmployees extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");
        List<String> listOfEmployees = new ArrayList<>();
        List<String> listOfRoles = new ArrayList<>();
        List<String> listOfPrincipals = new ArrayList<>();

        try {
            ResultSet DBSearch = statement.executeQuery("SELECT name FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (DBSearch.next()) {
                listOfEmployees.add(DBSearch.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(listOfEmployees.toArray(new String[0])).sorted(collator);
        listOfEmployees = new ArrayList<>(Arrays.asList(str.toArray(String[]::new)));

        try {
            ResultSet DBSearch;
            for (int i = 0; i < listOfEmployees.size(); i++) {
                DBSearch = statement.executeQuery("SELECT role FROM users WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND name LIKE \"" + listOfEmployees.get(i) + "\";");
                DBSearch.next();
                if(Objects.equals(DBSearch.getString("role"), "principal")){
                    listOfPrincipals.add(listOfEmployees.get(i));
                } else {
                    listOfRoles.add(DBSearch.getString("role"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < listOfPrincipals.size(); i++) {
            listOfEmployees.remove(listOfPrincipals.get(i));
        }
        
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
