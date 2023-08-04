package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;


public class UpdateClassroomHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public UpdateClassroomHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        boolean addToDB = true;

        String organization = (String) session.getAttribute("Organization");
        String classroom = (String) session.getAttribute("Classroom");
        String students[] = new String[(int) session.getAttribute("LengthOfStudentsArray")];

        for (int i = 0; i < students.length; i++) {
            String thisStudent = request.getParameter("student" + i);
            if(thisStudent.isEmpty()) {
                addToDB = false;
            }
            students[i] = thisStudent;
        }
        
        if(addToDB) {
            String studentsList = Arrays.toString(students).replace("'", "''").replace("[", "").replace("]", "");
            
            try {
                classesDAO.updateStudentsInClass(organization, classroom, studentsList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            session.removeAttribute("Classroom");
            session.removeAttribute("LengthOfStudentsArray");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/UpdatedClassroom.html");
            requestDispatcher.forward(request, response);
        } else {
            request.setAttribute("StudentsFromThisClass", students);
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Одне з полів з ПІБ учнів порожнє");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/EditClass.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
