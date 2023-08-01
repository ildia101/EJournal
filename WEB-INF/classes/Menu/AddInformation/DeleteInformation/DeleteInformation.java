package Menu.AddInformation.DeleteInformation;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DeleteInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        String button = request.getParameter("button");

        if(Objects.equals(button, "Видалити")) {
            String deleting = request.getParameter("Deleting");
            String objectToDelete;

            RequestDispatcher requestDispatcher = null;
            if (Objects.equals(deleting, "Class")) {
                objectToDelete = request.getParameter("classroom");
                CheckForError(objectToDelete, "DeleteClass/ChooseClass.jsp", request, response);

                try {
                    statement.executeUpdate("DELETE FROM classes WHERE organization = \"" + session.getAttribute("Organization") + "\" AND classroom = \"" + objectToDelete + "\";");
                    statement.executeUpdate("DELETE FROM marks WHERE organization = \"" + session.getAttribute("Organization") + "\" AND classroom = \"" + objectToDelete + "\";");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                ArrayList<String> classes = new ArrayList<>(Arrays.asList((String[]) session.getAttribute("Classes")));
                classes.remove(objectToDelete);
                session.setAttribute("Classes", classes.toArray(new String[0]));

                requestDispatcher = request.getRequestDispatcher("DeleteClass/ChooseClass.jsp");
            } else if (Objects.equals(deleting, "Subject")) {
                objectToDelete = request.getParameter("subject");
                CheckForError(objectToDelete, "DeleteSubject/ChooseSubject.jsp", request, response);

                try {
                    statement.executeUpdate("DELETE FROM subjects WHERE organization = \"" + session.getAttribute("Organization") + "\" AND subject = \"" + objectToDelete + "\";");
                    statement.executeUpdate("DELETE FROM marks WHERE organization = \"" + session.getAttribute("Organization") + "\" AND subject = \"" + objectToDelete + "\";");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                ArrayList<String> subjects = new ArrayList<>(Arrays.asList((String[]) session.getAttribute("Subjects")));
                subjects.remove(objectToDelete);
                session.setAttribute("Subjects", subjects.toArray(new String[0]));

                requestDispatcher = request.getRequestDispatcher("DeleteSubject/ChooseSubject.jsp");
            } else if (Objects.equals(deleting, "Marks")) {
                objectToDelete = request.getParameter("classroom");
                CheckForError(objectToDelete, "DeleteMarks/ChooseClass.jsp", request, response);

                try {
                    statement.executeUpdate("DELETE FROM marks WHERE organization = \"" + session.getAttribute("Organization") + "\" AND classroom = \"" + objectToDelete + "\";");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                requestDispatcher = request.getRequestDispatcher("DeleteMarks/ChooseClass.jsp");
            }

            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Розділ видалений успішно");
            requestDispatcher.forward(request, response);
        } else if (Objects.equals(button, "Завершити")){
            session.removeAttribute("Classes");
            session.removeAttribute("Subjects");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Finish.html");
            requestDispatcher.forward(request, response);
        }
    }

    private void CheckForError(String deletableItem, String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Objects.equals(deletableItem, "-")){
            request.setAttribute("SomeInfo", true);
            request.setAttribute("Info", "Обрано невірне значення");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }
    }
}
