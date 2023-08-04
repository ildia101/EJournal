package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.MarksDAO;
import org.ejournal.dao.SubjectsDAO;
import org.ejournal.dao.entities.MarksEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class CreateReportCardHttpServlet extends HttpServlet {
    private SubjectsDAO subjectsDAO;
    private MarksDAO marksDAO;

    public CreateReportCardHttpServlet() throws SQLException {
        this.subjectsDAO = new SubjectsDAO();
        this.marksDAO = new MarksDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String students[] = (String[]) session.getAttribute("StudentsFromThisClass");

        for (int i = 0; i < students.length; i++) {
            if(request.getParameter("Student" + i)!=null) {
                try {
					session.setAttribute("NameOfStudent", students[i]);
                    String classroom = (String) session.getAttribute("Classroom");

                    String subjects[] = subjectsDAO.getSubjectsOfThisOrganization((String) session.getAttribute("Organization"));
                    Collator collator = Collator.getInstance(new Locale("uk", "UA"));
                    Stream<String> str = Stream.of(subjects).sorted(collator);
                    String subjectsList[] = str.toArray(String[]::new);

                    session.setAttribute("Subjects", subjectsList);

                    String grades[][] = new String[subjectsList.length][3];
                    for (int j = 0; j < subjectsList.length; j++) {
                        int numberOfPage = 1;

                        boolean turnPage = true;

                        while (turnPage) {
                            MarksEntity marksInfo = marksDAO.getMarks((String) session.getAttribute("Organization"), classroom, subjectsList[j], numberOfPage, students.length);
                            if(marksInfo==null){
                               break;
                            }

                            String dates[];
                            String marks[][];

                            dates = marksInfo.getDates();
                            marks = marksInfo.getMarks();

                            for (int k = 0; k < dates.length; k++) {
                                if (Objects.equals(dates[k], "Ñ")) {
                                    if (grades[j][0] == null) {
                                        grades[j][0] = marks[i][k];
                                    } else {
                                        grades[j][1] = marks[i][k];
                                    }
                                } else if (Objects.equals(dates[k], "Ð")) {
                                    grades[j][2] = marks[i][k];
                                }
                            }

                            for (int k = 0; k < 3; k++) {
                                if (grades[j][k] == null) {
                                    turnPage = true;
                                    numberOfPage++;
                                    break;
                                } else {
                                    turnPage = false;
                                }
                            }
                        }

                        session.setAttribute("Grades", grades);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                break;
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/GetReportCard.jsp");
        requestDispatcher.forward(request, response);
    }
}
