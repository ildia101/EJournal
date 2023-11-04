package org.ejournal.servlet.menu.createclassroom;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import org.ejournal.dao.ClassStudentDAO;
import org.ejournal.dao.StudentDAO;
import java.io.IOException;

public class CreateClassroomHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private StudentDAO studentDAO;
    private ClassStudentDAO classStudentDAO;

    public CreateClassroomHttpServlet() {
        this.classDAO = new ClassDAO();
        this.studentDAO = new StudentDAO();
        this.classStudentDAO = new ClassStudentDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean addToDB = true;

        int organization = (int) session.getAttribute("Organization");
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
            try {
                int classID = classDAO.createClass(organization, classroom);

                for (int i = 0; i < students.length; i++) {
                    String fullNameOfThisStudent[] = students[i].split(" ");
                    int thisStudentID = studentDAO.addStudent(fullNameOfThisStudent[1], fullNameOfThisStudent[0], fullNameOfThisStudent[2]);
                    classStudentDAO.addStudentToClass(classID, thisStudentID);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            session.removeAttribute("Classroom");
            session.removeAttribute("ListOfStudents");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("CreatedClassroom.jsp");
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
