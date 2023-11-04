package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;

public class ClassStudentDAO {
    public void addStudentToClass(int classID, int studentID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO class_student VALUES(DEFAULT, ?, ?);");

            preparedStatement.setInt(1, classID);
            preparedStatement.setInt(2, studentID);

            preparedStatement.executeUpdate();
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public Integer[] getAllStudentIDs(int classID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT student_id FROM class_student WHERE class_id = ?");

            preparedStatement.setInt(1, classID);

            ResultSet result = preparedStatement.executeQuery();

            ArrayList<Integer> arrayList = new ArrayList<>();
            while (result.next()){
                arrayList.add(result.getInt("student_id"));
            }

            return arrayList.toArray(new Integer[0]);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public int getID(int classID, int studentID) throws SQLException {
        Connection connection = null;
        Statement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.createStatement();

            ResultSet result = preparedStatement.executeQuery("SELECT * FROM class_student");

            int id = -1;

            while (result.next()){
               if(result.getInt("class_id")==classID && result.getInt("student_id")==studentID){
                   id = result.getInt("id");
                   break;
               }
            }

            return id;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void deleteInfoByClassID(int classID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM class_student WHERE class_id = ?;");

            preparedStatement.setInt(1, classID);

            preparedStatement.executeUpdate();
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }
}
