package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class GetClassGradesHttpServlet extends HttpServlet {
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
            Statement statement = (Statement) session.getAttribute("DBAccess");

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
            }

            try {
                ResultSet DBSearch = statement.executeQuery("SELECT students FROM classes WHERE classroom LIKE \"" + classroom + "\" AND organization LIKE \"" + session.getAttribute("Organization") + "\";");
                DBSearch.next();
                students = DBSearch.getString("students").split(", ");
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }

            session.setAttribute("StudentsFromThisClass", students);


            String dates[] = null;
            String marks[][] = new String[students.length][17];
            try {
                ResultSet result = statement.executeQuery("SELECT dates FROM marks WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subject + "\" AND page LIKE \"" + numberOfPage + "\";");
                if (result.next()) {
                    dates = result.getString("dates").split(", ");

                    for (int i = 0; i < dates.length; i++) {
                        if (Objects.equals(dates[i], "null")) {
                            dates[i] = null;
                        }
                    }
                }

                result = statement.executeQuery("SELECT marks FROM marks WHERE organization LIKE \"" + session.getAttribute("Organization") + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subject + "\" AND page LIKE \"" + numberOfPage + "\";");
                if (result.next()) {
                    String packedArr = result.getString("marks");
                    packedArr = packedArr.substring(0, packedArr.length() - 1).replace("[", "");

                    String partUnpackedArr[] = packedArr.split("], ");

                    for (int i = 0; i < partUnpackedArr.length; i++) {
                        String temp[] = partUnpackedArr[i].split(", ");

                        for (int j = 0; j < 17; j++) {
                            if (Objects.equals(temp[j], "null")) {
                                marks[i][j] = null;
                            } else {
                                marks[i][j] = temp[j];
                            }
                        }
                    }
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
