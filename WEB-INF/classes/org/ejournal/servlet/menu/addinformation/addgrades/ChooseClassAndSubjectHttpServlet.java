package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import org.ejournal.dao.SubjectsDAO;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class ChooseClassAndSubjectHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;
    private SubjectsDAO subjectsDAO;

    public ChooseClassAndSubjectHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
        this.subjectsDAO = new SubjectsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classrooms[];
        String subjects[];

        session.setAttribute("NumberOfPage", 1);

        try {
            classrooms = classesDAO.getClassesOfThisOrganization((String) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(classrooms).sorted(collator);
        classrooms = str.toArray(String[]::new);
        for (int i = 0; i < classrooms.length - 1; i++) {
            for(int j = 0; j < classrooms.length - i - 1; j++) {
                if(Integer.parseInt(classrooms[j + 1].split("-")[0]) < Integer.parseInt(classrooms[j].split("-")[0])){
                    String temp = classrooms[j];
                    classrooms[j] = classrooms[j+1];
                    classrooms[j + 1] = temp;
                }
            }
        }

        session.setAttribute("Classes", classrooms);

        try {
            subjects = subjectsDAO.getSubjectsOfThisOrganization((String) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        str = Stream.of(subjects).sorted(collator);
        subjects = str.toArray(String[]::new);

        session.setAttribute("Subjects", subjects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/ChooseClassAndSubject.jsp");
        requestDispatcher.forward(request, response);
    }
}
