package Menu.AddInformation.EditClassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class EditClassroom extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        String classroom = request.getParameter("classroom");
        String students[] = null;

        if(Objects.equals(classroom, "-")){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з класом");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/ChooseClassForEditing.jsp");
            requestDispatcher.forward(request, response);
        }

        try {
            ResultSet result = statement.executeQuery("SELECT students FROM classes WHERE classroom LIKE \"" + classroom + "\" AND organization LIKE \"" + session.getAttribute("Organization") + "\";");
            result.next();
            students = result.getString("students").split(", ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        session.removeAttribute("Classes");
        
        session.setAttribute("Classroom", classroom);
		session.setAttribute("LengthOfStudentsArray", students.length);

        request.setAttribute("StudentsFromThisClass", students);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("EditClassroom/EditClass.jsp");
        requestDispatcher.forward(request, response);
    }
}
