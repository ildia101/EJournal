package Menu.ReportCard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class CreateReportCard extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        String students[] = (String[]) session.getAttribute("StudentsFromThisClass");

        for (int i = 0; i < students.length; i++) {
            if(request.getParameter("Student" + i)!=null) {
                try {
                    String classroom = (String) session.getAttribute("Classroom");

                    ArrayList<String> subjects = new ArrayList<>();
                    ResultSet resultSet = statement.executeQuery("SELECT subject FROM subjects WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
                    while (resultSet.next()) {
                        subjects.add(resultSet.getString("subject"));
                    }
                    Collator collator = Collator.getInstance(new Locale("uk", "UA"));
                    Stream<String> str = Stream.of(subjects.toArray(new String[0])).sorted(collator);
                    String subjectsList[] = str.toArray(String[]::new);

                    session.setAttribute("Subjects", subjectsList);

                    String grades[][] = new String[subjectsList.length][3];
                    for (int j = 0; j < subjectsList.length; j++) {
                        int numberOfPage = 1;

                        boolean turnPage = true;

                        while (turnPage) {
                            String dates[] = null;
                            String marks[][] = new String[students.length][17];

                            resultSet = statement.executeQuery("SELECT dates FROM marks WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subjectsList[j] + "\" AND page LIKE \"" + numberOfPage + "\";");
                            if (resultSet.next()) {
                                dates = resultSet.getString("dates").split(", ");

                                for (int k = 0; k < dates.length; k++) {
                                    if (Objects.equals(dates[k], "null")) {
                                        dates[k] = null;
                                    }
                                }
                            }

                            if(dates==null){
                                break;
                            }

                            resultSet = statement.executeQuery("SELECT marks FROM marks WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subjectsList[j] + "\" AND page LIKE \"" + numberOfPage + "\";");
                            if (resultSet.next()) {
                                String packedArr = resultSet.getString("marks");
                                packedArr = packedArr.substring(0, packedArr.length() - 1).replace("[", "");

                                String partUnpackedArr[] = packedArr.split("], ");

                                for (int k = 0; k < partUnpackedArr.length; k++) {
                                    String temp[] = partUnpackedArr[k].split(", ");

                                    for (int l = 0; l < 17; l++) {
                                        if (Objects.equals(temp[l], "null")) {
                                            marks[k][l] = null;
                                        } else {
                                            marks[k][l] = temp[l];
                                        }
                                    }
                                }
                            }

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
                                if(grades[j][k]==null){
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
