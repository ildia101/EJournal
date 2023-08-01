package Menu.AddInformation.DeleteInformation;

import java.io.*;
import java.sql.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class GetClassesAndSubjects extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        ArrayList<String> classes = new ArrayList<>();
        ArrayList<String> subjects = new ArrayList<>();

        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + session.getAttribute("Organization") +"\";");
            while (resultSet.next()){
                classes.add(resultSet.getString("classroom"));
            }

            resultSet = statement.executeQuery("SELECT subject FROM subjects WHERE organization LIKE \"" + session.getAttribute("Organization") +"\";");
            while (resultSet.next()){
                subjects.add(resultSet.getString("subject"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(classes.toArray(new String[0])).sorted(collator);
        String classesList[] = str.toArray(String[]::new);
        for (int i = 0; i < classesList.length - 1; i++) {
            for(int j = 0; j < classesList.length - i - 1; j++) {
                if(Integer.parseInt(classesList[j + 1].split("-")[0]) < Integer.parseInt(classesList[j].split("-")[0])){
                    String temp = classesList[j];
                    classesList[j] = classesList[j+1];
                    classesList[j + 1] = temp;
                }
            }
        }

        str = Stream.of(subjects.toArray(new String[0])).sorted(collator);
        String subjectsList[] = str.toArray(String[]::new);

        session.setAttribute("Classes", classesList);
        session.setAttribute("Subjects", subjectsList);

        RequestDispatcher requestDispatcher = null;
        String requestPathArr[] = request.getRequestURI().split("/");

        switch (requestPathArr[requestPathArr.length-1]) {
            case "ChooseClass":
                requestDispatcher = request.getRequestDispatcher("DeleteClass/ChooseClass.jsp");
                break;
            case "ChooseSubject":
                requestDispatcher = request.getRequestDispatcher("DeleteSubject/ChooseSubject.jsp");
                break;
            case "ChooseClassGrades":
                requestDispatcher = request.getRequestDispatcher("DeleteMarks/ChooseClass.jsp");
                break;
        }

        requestDispatcher.forward(request, response);
    }
}
