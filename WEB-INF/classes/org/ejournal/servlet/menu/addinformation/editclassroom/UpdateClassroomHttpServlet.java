package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.StudentDAO;
import org.ejournal.dao.entities.StudentEntity;

import java.io.IOException;
import java.sql.*;


public class UpdateClassroomHttpServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public UpdateClassroomHttpServlet() {
        this.studentDAO = new StudentDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        boolean addToDB = true;

        StudentEntity students[] = (StudentEntity[]) session.getAttribute("StudentsFromThisClass");

        for (int i = 0; i < students.length; i++) {
            String thisStudent = request.getParameter("student" + i);
            if(thisStudent.isEmpty()) {
                addToDB = false;
            }
        }
        
        if(addToDB) {
            try {
                for (int i = 0; i < students.length; i++) {
                    String thisStudent = request.getParameter("student" + i);
                    String fullName[] = thisStudent.split(" ");
                    studentDAO.editStudent(students[i].getId(), fullName[1], fullName[0], fullName[2]);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/UpdatedClassroom.jsp");
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
