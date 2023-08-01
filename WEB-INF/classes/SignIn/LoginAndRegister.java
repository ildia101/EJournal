package SignIn;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginAndRegister extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();

        String process = request.getParameter("process");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        HashMap<String, String> savedInfo = new HashMap<>();
        savedInfo.put("Email", email);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            Statement statement = connection.createStatement();

            session.setAttribute("DBAccess", statement);

            if(Objects.equals(process, "registration")){
                String role = request.getParameter("role");

                savedInfo.put("Name", name);
                if(Objects.equals(role, "none")){
                    request.setAttribute("InvalidData", "Оберіть Вашу посаду");
                    returnError(request, response, savedInfo);
                } else if(name.isEmpty()){
                    request.setAttribute("InvalidData", "Поле з ПІБ порожнє");
                    returnError(request, response, savedInfo);
                } else if(email.isEmpty()){
                    request.setAttribute("InvalidData", "Поле з адресою електронної пошти порожнє");
                    returnError(request, response, savedInfo);
                } else if(password.isEmpty()){
                    request.setAttribute("InvalidData", "Поле з паролем порожнє");
                    returnError(request, response, savedInfo);
                } else {
                    if(Objects.equals(role, "principal")) {
                        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                        StringBuilder code = new StringBuilder();
                        for (int i = 0; i < 6; i++) {
                            code.append(chars.charAt((int) (Math.random() * 62)));
                        }

                        statement.executeUpdate("INSERT INTO users VALUES('" + code + "', '" + role + "', '" + name + "', '" + email + "', '" + password + "')");

                        request.setAttribute("Code", code);

                        request.setAttribute("EnterCodePage", true);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("GetCode.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        session.setAttribute("Role", role);
                        session.setAttribute("Name", name);
                        session.setAttribute("Email", email);
                        session.setAttribute("Password", password);

                        request.setAttribute("EnterCodePage", true);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("InputYourCode.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }
            } else if(Objects.equals(process, "login")){
                ResultSet result = statement.executeQuery("SELECT password FROM users WHERE email LIKE \"" + email + "\";");
                result.next();
                if(result.getRow()!=0){
                    if(Objects.equals(result.getString("password"), password)){
                        session.setAttribute("LoggedIn", true);

                        ResultSet DBSearch = statement.executeQuery("SELECT role FROM users WHERE email LIKE \"" + email + "\";");
                        DBSearch.next();
                        session.setAttribute("Role", DBSearch.getString("role"));

                        session.setAttribute("Principal", "principal");
                        session.setAttribute("Tutor", "tutor");
                        session.setAttribute("Teacher", "teacher");

                        DBSearch = statement.executeQuery("SELECT organization FROM users WHERE email LIKE \"" + email + "\";");
                        DBSearch.next();
                        String org = DBSearch.getString("organization");
                        if(Objects.equals(org, "null")){
                            request.setAttribute("EnterCodePage", true);
                            session.setAttribute("Email", email);

                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("InputYourCode.jsp");
                            requestDispatcher.forward(request, response);
                        } else {
                            session.setAttribute("Organization", DBSearch.getString("organization"));
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("UserInSystem.jsp");
                            requestDispatcher.forward(request, response);
                        }
                    } else {
                        request.setAttribute("InvalidData", "Неправильно введений пароль");
                        returnError(request, response, savedInfo);
                    }
                } else {
                    request.setAttribute("InvalidData", "Немає користувача з такою адресою електронної пошти");
                    returnError(request, response, savedInfo);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e){
            request.setAttribute("InvalidData", "Користувач із такою адресою електронної пошти вже був зареєстрований");
            returnError(request, response, savedInfo);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    private static void returnError(HttpServletRequest request, HttpServletResponse response, HashMap<String, String> info) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        request.setAttribute("SavedInfo", info);
        request.setAttribute("Error", true);
        requestDispatcher.forward(request, response);
    }
}