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
import java.util.stream.Stream;

public class GetClasses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Statement statement = (Statement) session.getAttribute("DBAccess");

        ArrayList<String> classes = new ArrayList<>();

        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + session.getAttribute("Organization") +"\";");
            while (resultSet.next()){
                classes.add(resultSet.getString("classroom"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(classes.toArray(new String[0])).sorted(collator);
        String classesList[] = str.toArray(String[]::new);
        for (int i = 0; i < classesList.length - 1; i++) {
            for(int j = 0; j < classesList.length - i - 1; j++) {
                if(Integer.parseInt(classesList[j + 1].split("-")[0]) < Integer.parseInt(classesList[j].split("-")[0])){
                    String temp = classesList[j];
                    classesList[j] = classesList[j+1];
                    classesList[j + 1] = temp;
                }
            }
        }

        session.setAttribute("Classes", classesList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReportCard/ChooseClass.jsp");
        requestDispatcher.forward(request, response);
    }
}
