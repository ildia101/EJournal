package org.ejournal.dao;

import org.ejournal.dao.entities.MarksEntity;

import java.sql.*;
import java.util.Objects;

public class MarksDAO {

    public void addInformation(String organization, String classroom, String subject, int page, String dates, String marks) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO marks(organization, classroom, subject, page, dates, marks) VALUES(?, ?, ?, ?, ?, ?)");

            workWithDB.setString(1, organization);
            workWithDB.setString(2, classroom);
            workWithDB.setString(3, subject);
            workWithDB.setInt(4, page);
            workWithDB.setString(5, dates);
            workWithDB.setString(6, marks);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateDates(String organization, String classroom, String subject, int page, String dates) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE marks SET dates = ? WHERE organization = ? AND classroom = ? AND subject = ? AND page = ?;");

            workWithDB.setString(1, dates);
            workWithDB.setString(2, organization);
            workWithDB.setString(3, classroom);
            workWithDB.setString(4, subject);
            workWithDB.setInt(5, page);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateMarks(String organization, String classroom, String subject, int page, String marks) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE marks SET marks = ? WHERE organization = ? AND classroom = ? AND subject = ? AND page = ?;");

            workWithDB.setString(1, marks);
            workWithDB.setString(2, organization);
            workWithDB.setString(3, classroom);
            workWithDB.setString(4, subject);
            workWithDB.setInt(5, page);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public MarksEntity getMarks(String organization, String classroom, String subject, int page, int numberOfStudents) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM marks WHERE organization LIKE ? AND classroom LIKE ? AND subject LIKE ? AND page LIKE ?;");

            workWithDB.setString(1, organization);
            workWithDB.setString(2, classroom);
            workWithDB.setString(3, subject);
            workWithDB.setInt(4, page);

            ResultSet result = workWithDB.executeQuery();
            if (result.next()) {
                String dates[];
                dates = result.getString("dates").split(", ");

                for (int k = 0; k < dates.length; k++) {
                    if (Objects.equals(dates[k], "null")) {
                        dates[k] = null;
                    }
                }

                String marks[][] = new String[numberOfStudents][17];
                String packedArr = result.getString("marks");
                packedArr = packedArr.substring(0, packedArr.length() - 1).replace("[", "");

                String partUnpackedArr[] = packedArr.split("], ");

                for (int k = 0; k < partUnpackedArr.length; k++) {
                    String temp[] = partUnpackedArr[k].split(", ");

                    for (int l = 0; l < 17; l++) {
                        if (Objects.equals(temp[l], "null")) {
                            marks[k][l] = null;
                        } else {
                            marks[k][l] = temp[l];
                        }
                    }
                }

                return new MarksEntity(organization, classroom, subject, page, dates, marks);
            } else {
                return null;
            }
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteClassroomMarks(String codeOfOrganization, String classroom) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM marks WHERE organization = ? AND classroom = ?;");

            workWithDB.setString(1, codeOfOrganization);
            workWithDB.setString(2, classroom);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteSubjectMarks(String codeOfOrganization, String subject) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM marks WHERE organization = ? AND subject = ?;");

            workWithDB.setString(1, codeOfOrganization);
            workWithDB.setString(2, subject);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

}
