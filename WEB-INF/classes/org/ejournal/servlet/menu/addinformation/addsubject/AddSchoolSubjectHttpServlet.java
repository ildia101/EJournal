package org.ejournal.servlet.menu.addinformation.addsubject;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.SubjectsDAO;
import java.io.IOException;
import java.util.Objects;

public class AddSchoolSubjectHttpServlet extends HttpServlet {
    private SubjectsDAO subjectsDAO;

    public AddSchoolSubjectHttpServlet() throws SQLException {
        this.subjectsDAO = new SubjectsDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean addNew = true;

        HttpSession session = request.getSession();
        String organization = (String) session.getAttribute("Organization");

        String subject = request.getParameter("subject");
        String button = request.getParameter("button");

        if (Objects.equals(button, "Вийти без додавання")) {
            addNew = false;
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Finish.html");
            requestDispatcher.forward(request, response);
        } else {
            if (subject.isEmpty()) {
                addNew = false;
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                request.setAttribute("SomeInfo", true);
                request.setAttribute("Info", "Поле з назвою предмета порожнє");
                requestDispatcher.forward(request, response);
            } else {
                try {
                    String alreadyExistingSubjects[] = subjectsDAO.getSubjectsOfThisOrganization(organization);
                    for (int i = 0; i < alreadyExistingSubjects.length; i++) {
                        if (Objects.equals(alreadyExistingSubjects[i], subject)) {
                            addNew = false;
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                            request.setAttribute("SomeInfo", true);
                            request.setAttribute("Info", "Такий предмет вже існує");
                            requestDispatcher.forward(request, response);
                            break;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (addNew) {
                    try {
                        subjectsDAO.addNewSubject(organization, subject);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                RequestDispatcher requestDispatcher = null;
                if (Objects.equals(button, "Додати")) {
                    requestDispatcher = request.getRequestDispatcher("index.jsp");

                    request.setAttribute("SomeInfo", true);
                    request.setAttribute("Info", "Предмет додано успішно");
                } else if (Objects.equals(button, "Додати й завершити")) {
                    requestDispatcher = request.getRequestDispatcher("Finish.html");
                }
                requestDispatcher.forward(request, response);
            }
        }
    }
}
