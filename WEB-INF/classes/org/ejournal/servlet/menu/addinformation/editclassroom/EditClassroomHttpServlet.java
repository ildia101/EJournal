package org.ejournal.servlet.menu.addinformation.editclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class EditClassroomHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public EditClassroomHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classroom = request.getParameter("classroom");
        String students[];

        if(Objects.equals(classroom, "-")){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
            requestDispatcher.forward(request, response);
        } else {
			try {
				students = classesDAO.getClassStudents((String) session.getAttribute("Organization"), classroom);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			session.removeAttribute("Classes");
        
			session.setAttribute("Classroom", classroom);
			session.setAttribute("LengthOfStudentsArray", students.length);
	
			request.setAttribute("StudentsFromThisClass", students);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/EditClass.jsp");
			requestDispatcher.forward(request, response);
		}
    }
}
