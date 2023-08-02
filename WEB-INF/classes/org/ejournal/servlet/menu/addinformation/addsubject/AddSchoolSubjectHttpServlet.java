package org.ejournal.servlet.menu.addinformation.addsubject;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class AddSchoolSubjectHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean addNew = true;

        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");
        String organization = (String) session.getAttribute("Organization");

        String subject = request.getParameter("subject");
        String button = request.getParameter("button");

        if(Objects.equals(button, "Вийти без додавання")){
            addNew = false;
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Finish.html");
            requestDispatcher.forward(request, response);
        }

        if(subject.isEmpty()){
            addNew = false;
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Поле з назвою предмета порожнє");
            requestDispatcher.forward(request, response);
        } else {
            try {
                ResultSet result = statement.executeQuery("SELECT subject FROM subjects WHERE organization LIKE \"" + organization +"\";");
                while (result.next()){
                    String thisSubject = result.getString("subject");
                    if(Objects.equals(thisSubject, subject)){
                        addNew = false;
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                        request.setAttribute("SomeInfo", true);
                        request.setAttribute("Info", "Такий предмет вже існує");
                        requestDispatcher.forward(request, response);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(addNew) {
            try {
                statement.executeUpdate("INSERT INTO subjects(organization, subject) VALUES('" + organization + "', '" + subject + "')");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        RequestDispatcher requestDispatcher = null;
        if(Objects.equals(button, "Додати")){
            requestDispatcher = request.getRequestDispatcher("index.jsp");

            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Предмет додано успішно");
        } else if(Objects.equals(button, "Додати й завершити")){
            requestDispatcher = request.getRequestDispatcher("Finish.html");
        }
        requestDispatcher.forward(request, response);
    }
}
