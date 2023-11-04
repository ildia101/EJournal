package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import org.ejournal.dao.ClassStudentDAO;
import org.ejournal.dao.StudentDAO;
import org.ejournal.dao.entities.StudentEntity;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class EditClassroomHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private StudentDAO studentDAO;
    private ClassStudentDAO classStudentDAO;

    public EditClassroomHttpServlet() {
        this.classDAO = new ClassDAO();
        this.studentDAO = new StudentDAO();
        this.classStudentDAO = new ClassStudentDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classroom = request.getParameter("classroom");
        StudentEntity students[];

        if(Objects.equals(classroom, "-")){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
            requestDispatcher.forward(request, response);
        } else {
			try {
                int classID = classDAO.getClassIDs((int) session.getAttribute("Organization")).get(classroom);
                Integer studentsID[] = classStudentDAO.getAllStudentIDs(classID);
				students = studentDAO.getStudentsByIdAsArray(studentsID);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

	
			session.setAttribute("StudentsFromThisClass", students);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/EditClass.jsp");
			requestDispatcher.forward(request, response);
		}
    }
}
