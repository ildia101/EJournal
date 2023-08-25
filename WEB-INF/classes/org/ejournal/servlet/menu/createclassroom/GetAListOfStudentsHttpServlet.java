package org.ejournal.servlet.menu.createclassroom;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassDAO;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class GetAListOfStudentsHttpServlet extends HttpServlet {
    private ClassDAO classDAO;

    public GetAListOfStudentsHttpServlet() {
        this.classDAO = new ClassDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

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
        } else {
			String classroom = number + "-" + letter;
		
			try {
				String classes[] = classDAO.getClassNames((int) session.getAttribute("Organization"));
				for (int i = 0; i < classes.length; i++) {
					if(Objects.equals(classes[i], classroom)){
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
}
