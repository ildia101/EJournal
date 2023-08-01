package Menu.AddInformation.AddGrades;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class TurnPage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int numberOfPage = (int) session.getAttribute("NumberOfPage");
        String action = request.getParameter("Action");
        if(Objects.equals(action, "<")){
            if(numberOfPage==1){
                request.setAttribute("Error", true);
                request.setAttribute("InvalidData", "Досягнуто початкову сторінку");
            } else {
                numberOfPage--;
            }
        } else if(Objects.equals(action, ">")){
            numberOfPage++;
        }

        session.setAttribute("NumberOfPage", numberOfPage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Menu/AddInformation/InputGrades");
        requestDispatcher.forward(request, response);
    }
}
