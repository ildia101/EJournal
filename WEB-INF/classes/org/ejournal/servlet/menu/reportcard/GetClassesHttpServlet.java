package org.ejournal.servlet.menu.reportcard;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.ClassesDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;
import java.util.stream.Stream;

public class GetClassesHttpServlet extends HttpServlet {
    private ClassesDAO classesDAO;

    public GetClassesHttpServlet() throws SQLException {
        this.classesDAO = new ClassesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String classes[];
        try {
            classes = classesDAO.getClassesOfThisOrganization((String) session.getAttribute("Organization"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        Stream<String> str = Stream.of(classes).sorted(collator);
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
