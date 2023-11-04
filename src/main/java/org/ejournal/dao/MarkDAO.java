package org.ejournal.dao;

import org.ejournal.dao.entities.MarkEntity;

import java.sql.*;
import java.util.ArrayList;

public class MarkDAO {
    public void addMark(int classStudentID, int subjectID, String date, String mark) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO mark VALUES(DEFAULT, ?, ?, ?, ?)");

            preparedStatement.setInt(1, classStudentID);
            preparedStatement.setInt(2, subjectID);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, mark);

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

    public MarkEntity[] getSubjectMarks(int classStudentID, int subjectID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM mark WHERE class_student_id = ?");

            preparedStatement.setInt(1, classStudentID);

            ResultSet result = preparedStatement.executeQuery();

            ArrayList<MarkEntity> marks = new ArrayList<>();
            while (result.next()){
                if(result.getInt("subject_id")==subjectID) {
                    marks.add(createMarkEntity(result));
                }
            }

            return marks.toArray(new MarkEntity[0]);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void updateMark(int id, String date, String mark) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("UPDATE mark SET date = ?, value = ? WHERE id = ?;");

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, mark);
            preparedStatement.setInt(3, id);

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

    public void deleteStudentMarksByClassStudentID(int classStudentID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM mark WHERE class_student_id = ?;");

            preparedStatement.setInt(1, classStudentID);

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

    public void deleteStudentMarksBySubjectID(int subjectID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM mark WHERE subject_id = ?;");

            preparedStatement.setInt(1, subjectID);

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

    private MarkEntity createMarkEntity(ResultSet resultSet) throws SQLException {
        return new MarkEntity(resultSet.getInt("id"), resultSet.getString("date"), resultSet.getString("value"));
    }
}
