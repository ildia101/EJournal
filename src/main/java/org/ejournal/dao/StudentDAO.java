package org.ejournal.dao;

import org.ejournal.dao.entities.StudentEntity;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    public int addStudent(String firstName, String lastName, String middleName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(DEFAULT, ?, ?, ?);");

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, middleName);

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getInt(1);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void editStudent(int studentID, String firstName, String lastName, String middleName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("UPDATE student SET first_name = ?, last_name = ?, middle_name = ? WHERE id = ?;");

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, middleName);
            preparedStatement.setInt(4, studentID);

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

    public StudentEntity[] getStudentsByIdAsArray(Integer studentIDs[]) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
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

            preparedStatement = connection.prepareStatement(stringBuilder.toString());

            for (int i = 0; i < studentIDs.length; i++) {
                preparedStatement.setInt(i+1, studentIDs[i]);
            }

            ResultSet result = preparedStatement.executeQuery();

            ArrayList<StudentEntity> students = new ArrayList<>();
            while (result.next()){
                students.add(createStudentEntity(result));
            }

            return students.toArray(new StudentEntity[0]);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void deleteStudentByID(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM student WHERE id = ?;");

            preparedStatement.setInt(1, id);

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

    private StudentEntity createStudentEntity(ResultSet resultSet) throws SQLException {
        return new StudentEntity(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("middle_name"));
    }
}
