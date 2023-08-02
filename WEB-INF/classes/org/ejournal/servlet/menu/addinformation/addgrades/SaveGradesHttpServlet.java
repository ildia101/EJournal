package org.ejournal.servlet.menu.addinformation.addgrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class SaveGradesHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        boolean addToDB = true;
        String errorMSG = null;

        String organization = (String) session.getAttribute("Organization");
        String classroom = (String) session.getAttribute("Classroom");
        String subject = (String) session.getAttribute("Subject");
        int page = (int) session.getAttribute("NumberOfPage");
        String dates[] = new String[17];
        String datesList;
        String marks[][] = new String[(int)session.getAttribute("NumberOfStudents")][17];
        String marksList;

        for (int i = 0; i < 17; i++) {
            String temp = request.getParameter("date" + i);
            try{
                double num = Double.parseDouble(temp);
                if(num<0.0){
                    throw new NumberFormatException();
                }
                dates[i] = String.valueOf(num);
            } catch (NumberFormatException e){
                if(Objects.equals(temp, "Т") || Objects.equals(temp, "С") || Objects.equals(temp, "Р")) {
                    dates[i] = temp;
                } else if(temp.isEmpty()) {
                    dates[i] = null;
                } else {
                    dates[i] = temp;
                    errorMSG = "Невірне значення у полях з датами";
                    addToDB = false;
                }
            }
        }

        datesList = Arrays.toString(dates).replace("[", "").replace("]", "");

        for (int i = 0; i < (int)session.getAttribute("NumberOfStudents"); i++) {
            for (int j = 0; j < 17; j++) {
                String temp = request.getParameter("grade" + i + "" + j);
                try {
                    int num = Integer.parseInt(temp);
                    if (num < 1 || num > 12) {
                        throw new NumberFormatException();
                    }

                    marks[i][j] = String.valueOf(num);
                } catch (NumberFormatException e){
                    if(Objects.equals(temp, "Н") || Objects.equals(temp, "н")){
                        marks[i][j] = temp;
                    } else if(temp.isEmpty()) {
                        marks[i][j] = null;
                    } else {
                        marks[i][j] = temp;
                        errorMSG = "Невірне значення у полях з оцінками";
                        addToDB = false;
                    }
                }
            }
        }

        marksList = Arrays.deepToString(marks);
        marksList = marksList.substring(1, marksList.length()-1);

        if(addToDB) {
            try {
                boolean infoAlreadyExist = false;

                ResultSet checkForInfo = statement.executeQuery("SELECT dates FROM marks WHERE organization LIKE \"" + organization + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subject + "\" AND page LIKE \"" + page + "\";");
                if(checkForInfo.next()){
                    statement.executeUpdate("UPDATE marks SET dates = \"" + datesList + "\" WHERE organization = \"" + organization + "\" AND classroom = \"" + classroom + "\" AND subject = \"" + subject + "\" AND page = \"" + page + "\";");
                    infoAlreadyExist = true;
                }

                checkForInfo = statement.executeQuery("SELECT marks FROM marks WHERE organization LIKE \"" + organization + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subject + "\" AND page LIKE \"" + page + "\";");
                if(checkForInfo.next()){
                    statement.executeUpdate("UPDATE marks SET marks = \"" + marksList + "\" WHERE organization = \"" + organization + "\" AND classroom = \"" + classroom + "\" AND subject = \"" + subject + "\" AND page = \"" + page + "\";");
                    infoAlreadyExist = true;
                }

                if(!infoAlreadyExist) {
                    statement.executeUpdate("INSERT INTO marks(organization, classroom, subject, page, dates, marks) VALUES('" + organization + "', '" + classroom + "', '" + subject + "', '" + page + "', '" + datesList + "', '" + marksList + "')");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            session.removeAttribute("Classroom");
            session.removeAttribute("Subject");
            session.removeAttribute("NumberOfPage");
            session.removeAttribute("NumberOfStudents");

            session.removeAttribute("StudentsFromThisClass");
            session.removeAttribute("NumberOfPage");

            session.removeAttribute("Dates");
            session.removeAttribute("Marks");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/AddedGrades.html");
            requestDispatcher.forward(request, response);
        } else {
            session.setAttribute("Dates", dates);
            session.setAttribute("Marks", marks);

            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", errorMSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddGrades/InputGrades.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
