package org.ejournal.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SubjectsDAO {
    private Statement DBAccess;
    public SubjectsDAO() throws SQLException {
        DBAccess = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root").createStatement();
    }

    public void addNewSubject(String codeOfOrganization, String subject) throws SQLException {
        DBAccess.executeUpdate("INSERT INTO subjects(organization, subject) VALUES('" + codeOfOrganization + "', '" + subject + "')");
    }

    public String[] getSubjectsOfThisOrganization(String codeOfOrganization) throws SQLException {
        ArrayList<String> subjects = new ArrayList<>();
        ResultSet result = DBAccess.executeQuery("SELECT subject FROM subjects WHERE organization LIKE \"" + codeOfOrganization + "\";");
        while (result.next()) {
            subjects.add(result.getString("subject"));
        }
        return subjects.toArray(new String[0]);
    }

    public void deleteSubject(String codeOfOrganization, String subject) throws SQLException {
        DBAccess.executeUpdate("DELETE FROM subjects WHERE organization = \"" + codeOfOrganization + "\" AND subject = \"" + subject + "\";");
    }
}
