package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import org.ejournal.dao.MarksDAO;
import org.ejournal.dao.entities.MarksEntity;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class GetClassGradesHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;
    private MarksDAO marksDAO;

    public GetClassGradesHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
        this.marksDAO = new MarksDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            boolean error = (boolean) request.getAttribute("Error");
            String errorMSG = (String) request.getAttribute("InvalidData");
            if (error) {
                request.setAttribute("Error", true);
                request.setAttribute("InvalidData", errorMSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/InputGrades.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (NullPointerException nullPointerException){
            HttpSession session = request.getSession();

            String classroom = (String) session.getAttribute("Classroom");;
            if(classroom==null){
                classroom = request.getParameter("classroom");
            }
            String subject = (String) session.getAttribute("Subject");
            if(subject==null){
                subject = request.getParameter("subject");
            }
            String students[];
            int numberOfPage = (int) session.getAttribute("NumberOfPage");

            if (Objects.equals(classroom, "-")) {
                request.setAttribute("Error", true);
                request.setAttribute("InvalidData", "Невірне значення у полі з класом");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/ChooseClassAndSubject.jsp");
                requestDispatcher.forward(request, response);
            } else if (Objects.equals(subject, "-")) {
                request.setAttribute("Error", true);
                request.setAttribute("InvalidData", "Невірне значення у полі з предметом");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/ChooseClassAndSubject.jsp");
                requestDispatcher.forward(request, response);
            } else {
				try {
					students = classesDAO.getClassStudents((String) session.getAttribute("Organization"), classroom);
				} catch (SQLException sqlException) {
					throw new RuntimeException(sqlException);
				}

				session.setAttribute("StudentsFromThisClass", students);


				String dates[] = new String[0];
				String marks[][] = new String[0][0];
				try {
					MarksEntity marksInfo = marksDAO.getMarks((String) session.getAttribute("Organization"), classroom, subject, numberOfPage, students.length);
					
					if(marksInfo!=null) {
						dates = marksInfo.getDates();
						marks = marksInfo.getMarks();
					}
				} catch (SQLException sqlException) {
					throw new RuntimeException(sqlException);
				}

				session.setAttribute("Dates", dates);
				session.setAttribute("Marks", marks);

				session.setAttribute("Classroom", classroom);
				session.setAttribute("Subject", subject);
				session.setAttribute("NumberOfStudents", students.length);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/InputGrades.jsp");
				requestDispatcher.forward(request, response);
			}
        }
    }
}
