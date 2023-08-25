package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.*;
import org.ejournal.dao.entities.MarkEntity;
import org.ejournal.dao.entities.StudentEntity;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GetClassGradesHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ClassStudentDAO classStudentDAO;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;
    private MarkDAO markDAO;

    public GetClassGradesHttpServlet() {
        this.classDAO = new ClassDAO();
        this.classStudentDAO = new ClassStudentDAO();
        this.studentDAO = new StudentDAO();
        this.subjectDAO = new SubjectDAO();
        this.markDAO = new MarkDAO();
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
            int organization = (int) session.getAttribute("Organization");

            String classroom = (String) session.getAttribute("Classroom");
            if(classroom==null){
                classroom = request.getParameter("classroom");
            }
            String subject = (String) session.getAttribute("Subject");
            if(subject==null){
                subject = request.getParameter("subject");
            }
            StudentEntity students[];
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
                int classID;
                try {
                    classID = classDAO.getClassIDs(organization).get(classroom);
                    Integer studentIDs[] = classStudentDAO.getAllStudentIDs(classID);
                    students = studentDAO.getStudentsByIdAsArray(studentIDs);
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }

                session.setAttribute("StudentsFromThisClass", students);


                ArrayList<MarkEntity> requiredMarks = new ArrayList<>();
                for (int i = 0; i < students.length; i++) {
                    try {
                        MarkEntity alreadyExistingMarks[] = markDAO.getSubjectMarks(classStudentDAO.getID(classID, students[i].getId()), subjectDAO.getSubjectIDs(organization).get(subject));
                        requiredMarks.addAll(Arrays.asList(Arrays.copyOfRange(alreadyExistingMarks, 17 * (numberOfPage - 1), 17 * numberOfPage)));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        break;
                    }
                }

                for (int i = 0; i < requiredMarks.size(); i++) {
                    if(requiredMarks.get(i)==null){
                        requiredMarks.clear();
                        break;
                    }
                }

                session.setAttribute("Marks", requiredMarks.toArray(new MarkEntity[0]));

                session.setAttribute("Classroom", classroom);
                session.setAttribute("Subject", subject);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/InputGrades.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}
