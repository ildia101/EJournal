package org.ejournal.dao;

import org.ejournal.dao.entities.StudentEntity;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    public int addStudent(String firstName, String lastName, String middleName) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO student VALUES(DEFAULT, ?, ?, ?);");

            workWithDB.setString(1, firstName);
            workWithDB.setString(2, lastName);
            workWithDB.setString(3, middleName);

            workWithDB.executeUpdate();

            workWithDB = connectToDB.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet result = workWithDB.executeQuery();
            result.next();
            return result.getInt(1);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void editStudent(int studentID, String firstName, String lastName, String middleName) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE student SET first_name = ?, last_name = ?, middle_name = ? WHERE id = ?;");

            workWithDB.setString(1, firstName);
            workWithDB.setString(2, lastName);
            workWithDB.setString(3, middleName);
            workWithDB.setInt(4, studentID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public StudentEntity[] getStudentsByIdAsArray(Integer studentIDs[]) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT * FROM student WHERE id IN (");
            for (int i = 0; i < studentIDs.length; i++) {
                if(i+1==studentIDs.length){
                    stringBuilder.append("?");
                } else {
                    stringBuilder.append("?, ");
                }
            }
            stringBuilder.append(") ORDER BY last_name;");

            workWithDB = connectToDB.prepareStatement(stringBuilder.toString());

            for (int i = 0; i < studentIDs.length; i++) {
                workWithDB.setInt(i+1, studentIDs[i]);
            }

            ResultSet result = workWithDB.executeQuery();

            ArrayList<StudentEntity> students = new ArrayList<>();
            while (result.next()){
                students.add(new StudentEntity(result.getInt("id"), result.getString("first_name"), result.getString("last_name"), result.getString("middle_name")));
            }

            return students.toArray(new StudentEntity[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteStudentByID(int id) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM student WHERE id = ?;");

            workWithDB.setInt(1, id);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
