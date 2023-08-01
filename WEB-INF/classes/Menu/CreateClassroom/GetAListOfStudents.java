package Menu.CreateClassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class GetAListOfStudents extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
		Statement statement = (Statement) session.getAttribute("DBAccess");

        String number = request.getParameter("number");
        String letter = request.getParameter("letter");
        int numberOfStudents;
        try {
            numberOfStudents = Integer.parseInt(request.getParameter("number-of-students"));
        } catch (NumberFormatException e){
            numberOfStudents = 0;
        }

        request.setAttribute("Letter", letter);
        request.setAttribute("NumberOfStudents", String.valueOf(numberOfStudents));

        if(Objects.equals(number, "-")){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі з цифрою класу");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else if(letter.isEmpty()){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Поле з буквою класа порожнє");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else if(numberOfStudents<1){
            request.setAttribute("Error", true);
            request.setAttribute("InvalidData", "Невірне значення у полі кількості учнів");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        }

        String classroom = number + "-" + letter;
		
		try {
            ResultSet DBSearch = statement.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + session.getAttribute("Organization") + "\";");
            while (DBSearch.next()){
                String existingClass = DBSearch.getString("classroom");
                if(Objects.equals(existingClass, classroom)){
                    request.setAttribute("Error", true);
                    request.setAttribute("InvalidData", "Такий клас вже існує");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
        String students[] = new String[numberOfStudents];

        session.setAttribute("Classroom", classroom);
        session.setAttribute("ListOfStudents", students);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("2ndStep.jsp");
        requestDispatcher.forward(request, response);
    }
}
