package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassDAO {
    public int createClass(int organizationID, String classroom) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO class VALUES(DEFAULT, ?, ?)");

            workWithDB.setInt(1, organizationID);
            workWithDB.setString(2, classroom);

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

    public String[] getClassNames(int organizationID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT name FROM class WHERE organization_id = ? ORDER BY name");

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

    public HashMap<String, Integer> getClassIDs(int organizationID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM class WHERE organization_id = ?");

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

    public boolean isThisClassInThisOrganization(int classID, int organizationID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT organization_id FROM class WHERE id = ?");

            workWithDB.setInt(1, classID);

            ResultSet result = workWithDB.executeQuery();

            if(result.getInt("organization_id")==organizationID){
                return true;
            } else {
                return false;
            }
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void deleteClassByID(int id) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("DELETE FROM class WHERE id = ?;");

            workWithDB.setInt(1, id);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
