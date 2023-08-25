package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;

public class ClassStudentDAO {
    public void addStudentToClass(int classID, int studentID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO class_student VALUES(DEFAULT, ?, ?);");

            workWithDB.setInt(1, classID);
            workWithDB.setInt(2, studentID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public Integer[] getAllStudentIDs(int classID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT student_id FROM class_student WHERE class_id = ?");

            workWithDB.setInt(1, classID);

            ResultSet result = workWithDB.executeQuery();

            ArrayList<Integer> arrayList = new ArrayList<>();
            while (result.next()){
                arrayList.add(result.getInt("student_id"));
            }

            return arrayList.toArray(new Integer[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public int getID(int classID, int studentID) throws SQLException {
        Connection connectToDB = null;
        Statement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.createStatement();

            ResultSet result = workWithDB.executeQuery("SELECT * FROM class_student");

            int id = -1;

            while (result.next()){
               if(result.getInt("class_id")==classID && result.getInt("student_id")==studentID){
                   id = result.getInt("id");
                   break;
               }
            }

            return id;
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteInfoByClassID(int classID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM class_student WHERE class_id = ?;");

            workWithDB.setInt(1, classID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
