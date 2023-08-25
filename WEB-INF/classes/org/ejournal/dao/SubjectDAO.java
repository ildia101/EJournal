package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SubjectDAO {
    public void addSubject(int organizationID, String nameOfSubject) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO subject VALUES(DEFAULT, ?, ?);");

            workWithDB.setInt(1, organizationID);
            workWithDB.setString(2, nameOfSubject);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public String[] getSubjectNames(int organizationID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT name FROM subject WHERE organization_id = ? ORDER BY name;");

            workWithDB.setInt(1, organizationID);

            ResultSet result = workWithDB.executeQuery();

            ArrayList<String> arrayList = new ArrayList<>();
            while (result.next()){
                arrayList.add(result.getString("name"));
            }

            return arrayList.toArray(new String[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public HashMap<String, Integer> getSubjectIDs(int organizationID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM subject WHERE organization_id = ?");

            workWithDB.setInt(1, organizationID);

            ResultSet result = workWithDB.executeQuery();

            HashMap<String, Integer> hashMap = new HashMap<>();
            while (result.next()){
                hashMap.put(result.getString("name"), result.getInt("id"));
            }

            return hashMap;
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteSubjectByID(int subjectID) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM subject WHERE id = ?;");

            workWithDB.setInt(1, subjectID);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
