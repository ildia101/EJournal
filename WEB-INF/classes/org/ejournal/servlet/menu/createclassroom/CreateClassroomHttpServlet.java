package org.ejournal.servlet.menu.createclassroom;

import java.sql.*;
import java.util.Arrays;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class CreateClassroomHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public CreateClassroomHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean addToDB = true;

        String organization = (String) session.getAttribute("Organization");
        String classroom = (String) session.getAttribute("Classroom");
        String students[] = (String[]) session.getAttribute("ListOfStudents");

        for (int i = 0; i < students.length; i++) {
            String thisStudent = request.getParameter("children" + i);
            if(thisStudent.isEmpty()) {
                addToDB = false;
            }
            students[i] = thisStudent;
        }

        if(addToDB) {
            Collator collator = Collator.getInstance(new Locale("uk", "UA"));
            Stream<String> str = Stream.of(students).sorted(collator);
            students = str.toArray(String[]::new);
            String studentsList = Arrays.toString(students).replace("'", "''").replace("[", "").replace("]", "");

            try {
                classesDAO.createClass(organization, classroom, studentsList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            session.removeAttribute("Classroom");
            session.removeAttribute("ListOfStudents");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("CreatedClassroom.html");
            requestDispatcher.forward(request, response);
        } else {
            session.setAttribute("ListOfStudents", students);
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Одне з полів з ПІБ учнів порожнє");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("2ndStep.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
