package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.*;
import org.ejournal.dao.entities.MarkEntity;
import org.ejournal.dao.entities.StudentEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class CreateReportCardHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ClassStudentDAO classStudentDAO;
    private SubjectDAO subjectDAO;
    private MarkDAO markDAO;

    public CreateReportCardHttpServlet() {
        this.classDAO = new ClassDAO();
        this.classStudentDAO = new ClassStudentDAO();
        this.subjectDAO = new SubjectDAO();
        this.markDAO = new MarkDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int organizationID = (int) session.getAttribute("Organization");

        StudentEntity students[] = (StudentEntity[]) session.getAttribute("StudentsFromThisClass");

        for (int i = 0; i < students.length; i++) {
            if(request.getParameter("Student" + i)!=null) {
                try {
                    createReportCardOfThisStudent(session, organizationID, students[i]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/GetReportCard.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createReportCardOfThisStudent(HttpSession session, int organizationID, StudentEntity students) throws SQLException {
        session.setAttribute("NameOfStudent", students);

        String classroom = (String) session.getAttribute("Classroom");

        String subjects[] = subjectDAO.getSubjectNames(organizationID);
        session.setAttribute("Subjects", subjects);

        int classID = classDAO.getClassIDs(organizationID).get(classroom);
        HashMap<String, Integer> subjectIDs = subjectDAO.getSubjectIDs(organizationID);

        String reportCard[][] = new String[subjects.length][3];
        for (int j = 0; j < subjects.length; j++) {
            MarkEntity marks[] = markDAO.getSubjectMarks(classStudentDAO.getID(classID, students.getId()), subjectIDs.get(subjects[j]));
            for (int k = 0; k < marks.length; k++) {
                if(Objects.equals(marks[k].getDate(), "Ñ")){
                    if(reportCard[j][0]==null) {
                        reportCard[j][0] = marks[k].getMark();
                    } else {
                        reportCard[j][1] = marks[k].getMark();
                    }
                } else if(Objects.equals(marks[k].getDate(), "Ð")){
                    reportCard[j][2] = marks[k].getMark();
                }
            }
        }

        session.setAttribute("Grades", reportCard);
    }
}
