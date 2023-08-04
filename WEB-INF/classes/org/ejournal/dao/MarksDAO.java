package org.ejournal.dao;

import org.ejournal.dao.entities.MarksEntity;
import org.ejournal.dao.entities.UserEntity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class MarksDAO {
    private Statement DBAccess;
    public MarksDAO() throws SQLException {
        DBAccess = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root").createStatement();
    }

    public void addInformation(String organization, String classroom, String subject, int page, String dates, String marks) throws SQLException {
        DBAccess.executeUpdate("INSERT INTO marks(organization, classroom, subject, page, dates, marks) VALUES('" + organization + "', '" + classroom + "', '" + subject + "', '" + page + "', '" + dates + "', '" + marks + "')");
    }

    public void updateDates(String organization, String classroom, String subject, int page, String dates) throws SQLException {
        DBAccess.executeUpdate("UPDATE marks SET dates = \"" + dates + "\" WHERE organization = \"" + organization + "\" AND classroom = \"" + classroom + "\" AND subject = \"" + subject + "\" AND page = \"" + page + "\";");
    }

    public void updateMarks(String organization, String classroom, String subject, int page, String marks) throws SQLException {
        DBAccess.executeUpdate("UPDATE marks SET marks = \"" + marks + "\" WHERE organization = \"" + organization + "\" AND classroom = \"" + classroom + "\" AND subject = \"" + subject + "\" AND page = \"" + page + "\";");
    }

    public MarksEntity getMarks(String organization, String classroom, String subject, int page, int numberOfStudents) throws SQLException {
        ResultSet result = DBAccess.executeQuery("SELECT * FROM marks WHERE organization LIKE \"" + organization + "\" AND classroom LIKE \"" + classroom + "\" AND subject LIKE \"" + subject + "\" AND page LIKE \"" + page + "\";");
        if(result.next()) {
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
    }

    public void deleteClassroomMarks(String codeOfOrganization, String classroom) throws SQLException {
        DBAccess.executeUpdate("DELETE FROM marks WHERE organization = \"" + codeOfOrganization + "\" AND classroom = \"" + classroom + "\";");
    }

    public void deleteSubjectMarks(String codeOfOrganization, String subject) throws SQLException {
        DBAccess.executeUpdate("DELETE FROM marks WHERE organization = \"" + codeOfOrganization + "\" AND subject = \"" + subject + "\";");
    }

}
