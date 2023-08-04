package org.ejournal.servlet.menu.addinformation.deleteinformation;

import java.io.*;
import java.sql.*;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import org.ejournal.dao.SubjectsDAO;

public class GetClassesAndSubjectsHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;
    private SubjectsDAO subjectsDAO;

    public GetClassesAndSubjectsHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
        this.subjectsDAO = new SubjectsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classesList[];
        String subjectsList[];

        try {
            classesList = classesDAO.getClassesOfThisOrganization((String) session.getAttribute("Organization"));

            subjectsList = subjectsDAO.getSubjectsOfThisOrganization((String) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(classesList).sorted(collator);
        classesList = str.toArray(String[]::new);
        for (int i = 0; i < classesList.length - 1; i++) {
            for(int j = 0; j < classesList.length - i - 1; j++) {
                if(Integer.parseInt(classesList[j + 1].split("-")[0]) < Integer.parseInt(classesList[j].split("-")[0])){
                    String temp = classesList[j];
                    classesList[j] = classesList[j+1];
                    classesList[j + 1] = temp;
                }
            }
        }

        str = Stream.of(subjectsList).sorted(collator);
        subjectsList = str.toArray(String[]::new);

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
