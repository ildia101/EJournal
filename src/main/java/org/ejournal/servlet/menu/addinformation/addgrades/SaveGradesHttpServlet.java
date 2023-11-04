package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.*;
import org.ejournal.dao.entities.MarkEntity;
import org.ejournal.dao.entities.StudentEntity;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class SaveGradesHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ClassStudentDAO classStudentDAO;
    private SubjectDAO subjectDAO;
    private MarkDAO markDAO;

    public SaveGradesHttpServlet() {
        this.classDAO = new ClassDAO();
        this.classStudentDAO = new ClassStudentDAO();
        this.subjectDAO = new SubjectDAO();
        this.markDAO = new MarkDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean addToDB = true;
        String errorMSG = null;

        int organization = (int) session.getAttribute("Organization");
        int classroomID;
        int subjectID;
        try {
            classroomID = classDAO.getClassIDs(organization).get((String)session.getAttribute("Classroom"));
            subjectID = subjectDAO.getSubjectIDs(organization).get((String)session.getAttribute("Subject"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StudentEntity students[] = (StudentEntity[]) session.getAttribute("StudentsFromThisClass");

        MarkEntity marks[] = (MarkEntity[]) session.getAttribute("MarksFromDB");
        if(marks==null){
            marks = (MarkEntity[]) session.getAttribute("Marks");
        }
        String dates[] = new String[17];
        String markValues[][] = new String[students.length][17];

        for (int i = 0; i < 17; i++) {
            String temp = request.getParameter("date" + i);
            try {
                double num = Double.parseDouble(temp);
                if (num < 0.0) {
                    throw new NumberFormatException();
                }
                dates[i] = String.valueOf(num);
            } catch (NumberFormatException e) {
                if (Objects.equals(temp, "Т") || Objects.equals(temp, "С") || Objects.equals(temp, "Р")) {
                    dates[i] = temp;
                } else if (temp.isEmpty()) {
                    dates[i] = null;
                } else {
                    dates[i] = temp;
                    errorMSG = "Невірне значення у полях з датами";
                    addToDB = false;
                }
            }
        }

        for (int i = 0; i < students.length; i++) {
            for (int j = 0; j < 17; j++) {
                String temp = request.getParameter("grade" + i + "" + j);
                try {
                    int num = Integer.parseInt(temp);
                    if (num < 1 || num > 12) {
                        throw new NumberFormatException();
                    }

                    markValues[i][j] = String.valueOf(num);
                } catch (NumberFormatException e) {
                    if (Objects.equals(temp, "Н") || Objects.equals(temp, "н")) {
                        markValues[i][j] = temp;
                    } else if (temp.isEmpty()) {
                        markValues[i][j] = null;
                    } else {
                        markValues[i][j] = temp;
                        errorMSG = "Невірне значення у полях з оцінками";
                        addToDB = false;
                    }
                }
            }
        }

        if (addToDB) {
            for (int i = 0; i < students.length; i++) {
                for (int j = 0; j < 17; j++) {
                    try {
                        if (marks.length == 0) {
                            int classStudentID = classStudentDAO.getID(classroomID, students[i].getId());
                            markDAO.addMark(classStudentID, subjectID, dates[j], markValues[i][j]);
                        } else {
                            markDAO.updateMark(marks[j + (i * 17)].getId(), dates[j], markValues[i][j]);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            session.removeAttribute("Classroom");
            session.removeAttribute("Subject");
            session.removeAttribute("ClassroomID");
            session.removeAttribute("SubjectID");
            session.removeAttribute("NumberOfPage");

            session.removeAttribute("StudentsFromThisClass");
            session.removeAttribute("Marks");
            session.removeAttribute("MarksFromDB");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/AddedGrades.jsp");
            requestDispatcher.forward(request, response);
        } else {
            MarkEntity savedMarks[] = new MarkEntity[students.length*17];

            for (int i = 0; i < students.length; i++) {
                for (int j = 0; j < 17; j++) {
                    savedMarks[j+(i*17)] = new MarkEntity(0, dates[j], markValues[i][j]);
                }
            }

            session.setAttribute("Marks", savedMarks);
            session.setAttribute("MarksFromDB", marks);

            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", errorMSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/InputGrades.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
