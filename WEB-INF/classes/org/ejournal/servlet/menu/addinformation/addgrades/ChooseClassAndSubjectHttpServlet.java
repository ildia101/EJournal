package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class ChooseClassAndSubjectHttpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        ArrayList<String> classrooms = new ArrayList<>();
        ArrayList<String> subjects = new ArrayList<>();

        session.setAttribute("NumberOfPage", 1);

        try {
            ResultSet result = statement.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (result.next()){
                classrooms.add(result.getString("classroom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String resultClasses[] = classrooms.toArray(new String[0]);
        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(resultClasses).sorted(collator);
        resultClasses = str.toArray(String[]::new);
        for (int i = 0; i < resultClasses.length - 1; i++) {
            for(int j = 0; j < resultClasses.length - i - 1; j++) {
                if(Integer.parseInt(resultClasses[j + 1].split("-")[0]) < Integer.parseInt(resultClasses[j].split("-")[0])){
                    String temp = resultClasses[j];
                    resultClasses[j] = resultClasses[j+1];
                    resultClasses[j + 1] = temp;
                }
            }
        }

        session.setAttribute("Classes", resultClasses);

        try {
            ResultSet result = statement.executeQuery("SELECT subject FROM subjects WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (result.next()){
                subjects.add(result.getString("subject"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String resultSubjects[] = subjects.toArray(new String[0]);
        str = Stream.of(resultSubjects).sorted(collator);
        resultSubjects = str.toArray(String[]::new);

        session.setAttribute("Subjects", resultSubjects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/ChooseClassAndSubject.jsp");
        requestDispatcher.forward(request, response);
    }
}
