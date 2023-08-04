package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;

public class SubjectsDAO {

    public void addNewSubject(String codeOfOrganization, String subject) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO subjects(organization, subject) VALUES(?, ?)");

            workWithDB.setString(1, codeOfOrganization);
            workWithDB.setString(2, subject);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public String[] getSubjectsOfThisOrganization(String codeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT subject FROM subjects WHERE organization LIKE ?;");

            workWithDB.setString(1, codeOfOrganization);

            ArrayList<String> subjects = new ArrayList<>();
            ResultSet result = workWithDB.executeQuery();
            while (result.next()) {
                subjects.add(result.getString("subject"));
            }
            return subjects.toArray(new String[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteSubject(String codeOfOrganization, String subject) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM subjects WHERE organization = ? AND subject = ?;");

            workWithDB.setString(1, codeOfOrganization);
            workWithDB.setString(2, subject);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
