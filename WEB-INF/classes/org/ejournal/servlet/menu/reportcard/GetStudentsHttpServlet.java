package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import org.ejournal.dao.ClassStudentDAO;
import org.ejournal.dao.StudentDAO;
import org.ejournal.dao.entities.StudentEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class GetStudentsHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ClassStudentDAO classStudentDAO;
    private StudentDAO studentDAO;;

    public GetStudentsHttpServlet() {
        this.classDAO = new ClassDAO();
        this.classStudentDAO = new ClassStudentDAO();
        this.studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classroom = request.getParameter("classroom");
        if (Objects.equals(classroom, "-")) {
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseClass.jsp");
            requestDispatcher.forward(request, response);
        } else {
            StudentEntity students[];
            try {
                int classID = classDAO.getClassIDs((int) session.getAttribute("Organization")).get(classroom);
                Integer studentIDs[] = classStudentDAO.getAllStudentIDs(classID);
                students = studentDAO.getStudentsByIdAsArray(studentIDs);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }

            session.setAttribute("Classroom", classroom);
            session.setAttribute("StudentsFromThisClass", students);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseStudent.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
