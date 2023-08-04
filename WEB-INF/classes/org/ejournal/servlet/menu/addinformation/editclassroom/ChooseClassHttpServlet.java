package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class ChooseClassHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public ChooseClassHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classrooms[];

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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
        requestDispatcher.forward(request, response);
    }
}
