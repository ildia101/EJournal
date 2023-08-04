package org.ejournal.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClassesDAO {
    private Statement DBAccess;
    public ClassesDAO() throws SQLException {
        DBAccess = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root").createStatement();
    }

    public void createClass(String organization, String classroom, String students) throws SQLException {
        DBAccess.executeUpdate("INSERT INTO classes(organization, classroom, students) VALUES('" + organization + "', '" + classroom + "', '" + students + "')");
    }

    public String[] getClassesOfThisOrganization(String codeOfOrganization) throws SQLException {
        ArrayList<String> classes = new ArrayList<>();

        ResultSet result = DBAccess.executeQuery("SELECT classroom FROM classes WHERE organization LIKE \"" + codeOfOrganization +"\";");
        while (result.next()){
            classes.add(result.getString("classroom"));
        }

        return classes.toArray(new String[0]);
    }

    public String[] getClassStudents(String codeOfOrganization, String classroom) throws SQLException {
        ResultSet result = DBAccess.executeQuery("SELECT students FROM classes WHERE classroom LIKE \"" + classroom + "\" AND organization LIKE \"" + codeOfOrganization + "\";");
        result.next();
        return result.getString("students").split(", ");
    }

    public void updateStudentsInClass(String codeOfOrganization, String classroom, String students) throws SQLException {
        DBAccess.executeUpdate("UPDATE classes SET students = '" + students + "' WHERE classroom = '" + classroom + "' AND organization LIKE '" + codeOfOrganization + "';");
    }

    public void deleteClassroom(String codeOfOrganization, String classroom) throws SQLException {
        DBAccess.executeUpdate("DELETE FROM classes WHERE organization = \"" + codeOfOrganization + "\" AND classroom = \"" + classroom + "\";");
    }
}
