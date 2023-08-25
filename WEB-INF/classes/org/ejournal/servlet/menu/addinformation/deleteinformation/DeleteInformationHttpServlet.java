package org.ejournal.servlet.menu.addinformation.deleteinformation;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.ejournal.dao.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DeleteInformationHttpServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ClassStudentDAO classStudentDAO;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;
    private MarkDAO markDAO;

    public DeleteInformationHttpServlet() {
        this.classDAO = new ClassDAO();
        this.classStudentDAO = new ClassStudentDAO();
        this.studentDAO = new StudentDAO();
        this.subjectDAO = new SubjectDAO();
        this.markDAO = new MarkDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String button = request.getParameter("button");

        if(Objects.equals(button, "Видалити")) {
            String deleting = request.getParameter("Deleting");
            String objectToDelete;

            RequestDispatcher requestDispatcher = null;
            if (Objects.equals(deleting, "Class")) {
                objectToDelete = request.getParameter("classroom");
                CheckForError(objectToDelete, "DeleteClass/ChooseClass.jsp", request, response);

                try {
                    int classID = classDAO.getClassIDs((int) session.getAttribute("Organization")).get(objectToDelete);
                    Integer studentsIDs[] = classStudentDAO.getAllStudentIDs(classID);

                    classDAO.deleteClassByID(classID);

                    for (int i = 0; i < studentsIDs.length; i++) {
                        int classStudentID = classStudentDAO.getID(classID, studentsIDs[i]);
                        markDAO.deleteStudentMarksByClassStudentID(classStudentID);

                        studentDAO.deleteStudentByID(studentsIDs[i]);
                    }

                    classStudentDAO.deleteInfoByClassID(classID);
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
                    int subjectID = subjectDAO.getSubjectIDs((int) session.getAttribute("Organization")).get(objectToDelete);

                    subjectDAO.deleteSubjectByID(subjectID);
                    markDAO.deleteStudentMarksBySubjectID(subjectID);
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
                    int classID = classDAO.getClassIDs((int) session.getAttribute("Organization")).get(objectToDelete);
                    Integer studentIDs[] = classStudentDAO.getAllStudentIDs(classID);

                    for (int i = 0; i < studentIDs.length; i++) {
                        int classStudentID = classStudentDAO.getID(classID, studentIDs[i]);
                        markDAO.deleteStudentMarksByClassStudentID(classStudentID);
                    }
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
