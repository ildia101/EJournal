package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class ChooseClassHttpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        ArrayList<String> classrooms = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (result.next()){
                classrooms.add(result.getString("classroom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String result[] = classrooms.toArray(new String[0]);
        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(result).sorted(collator);
        result = str.toArray(String[]::new);
        for (int i = 0; i < result.length - 1; i++) {
            for(int j = 0; j < result.length - i - 1; j++) {
                if(Integer.parseInt(result[j + 1].split("-")[0]) < Integer.parseInt(result[j].split("-")[0])){
                    String temp = result[j];
                    result[j] = result[j+1];
                    result[j + 1] = temp;
                }
            }
        }

        session.setAttribute("Classes", result);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
        requestDispatcher.forward(request, response);
    }
}
