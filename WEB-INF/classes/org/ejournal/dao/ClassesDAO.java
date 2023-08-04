package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;

public class ClassesDAO {

    public void createClass(String organization, String classroom, String students) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO classes(organization, classroom, students) VALUES(?, ?, ?)");

            workWithDB.setString(1, organization);
            workWithDB.setString(2, classroom);
            workWithDB.setString(3, students);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public String[] getClassesOfThisOrganization(String codeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT classroom FROM classes WHERE organization LIKE ?;");

            workWithDB.setString(1, codeOfOrganization);
            ArrayList<String> classes = new ArrayList<>();

            ResultSet result = workWithDB.executeQuery();
            while (result.next()) {
                classes.add(result.getString("classroom"));
            }

            return classes.toArray(new String[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public String[] getClassStudents(String codeOfOrganization, String classroom) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT students FROM classes WHERE classroom LIKE ? AND organization LIKE ?;");

            workWithDB.setString(1, classroom);
            workWithDB.setString(2, codeOfOrganization);

            ResultSet result = workWithDB.executeQuery();
            result.next();
            return result.getString("students").split(", ");
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateStudentsInClass(String codeOfOrganization, String classroom, String students) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE classes SET students = ? WHERE classroom = ? AND organization LIKE ?;");

            workWithDB.setString(1, students);
            workWithDB.setString(2, classroom);
            workWithDB.setString(3, codeOfOrganization);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteClassroom(String codeOfOrganization, String classroom) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM classes WHERE organization = ? AND classroom = ?;");

            workWithDB.setString(1, codeOfOrganization);
            workWithDB.setString(2, classroom);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
