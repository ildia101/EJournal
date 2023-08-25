package org.ejournal.dao;

import org.ejournal.dao.entities.MarkEntity;

import java.sql.*;
import java.util.ArrayList;

public class MarkDAO {
    public void addMark(int classStudentID, int subjectID, String date, String mark) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO mark VALUES(DEFAULT, ?, ?, ?, ?)");

            workWithDB.setInt(1, classStudentID);
            workWithDB.setInt(2, subjectID);
            workWithDB.setString(3, date);
            workWithDB.setString(4, mark);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public MarkEntity[] getSubjectMarks(int classStudentID, int subjectID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM mark WHERE class_student_id = ?");

            workWithDB.setInt(1, classStudentID);

            ResultSet result = workWithDB.executeQuery();

            ArrayList<MarkEntity> marks = new ArrayList<>();
            while (result.next()){
                if(result.getInt("subject_id")==subjectID) {
                    marks.add(new MarkEntity(result.getInt("id"), result.getString("date"), result.getString("value")));
                }
            }

            return marks.toArray(new MarkEntity[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateMark(int id, String date, String mark) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE mark SET date = ?, value = ? WHERE id = ?;");

            workWithDB.setString(1, date);
            workWithDB.setString(2, mark);
            workWithDB.setInt(3, id);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteStudentMarksByClassStudentID(int classStudentID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM mark WHERE class_student_id = ?;");

            workWithDB.setInt(1, classStudentID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteStudentMarksBySubjectID(int subjectID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM mark WHERE subject_id = ?;");

            workWithDB.setInt(1, subjectID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
