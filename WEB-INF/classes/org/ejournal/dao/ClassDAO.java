package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassDAO {
    public int createClass(int organizationID, String classroom) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO class VALUES(DEFAULT, ?, ?)");

            preparedStatement.setInt(1, organizationID);
            preparedStatement.setString(2, classroom);

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

    public String[] getClassNames(int organizationID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT name FROM class WHERE organization_id = ? ORDER BY name");

            preparedStatement.setInt(1, organizationID);

            ResultSet result = preparedStatement.executeQuery();

            ArrayList<String> arrayList = new ArrayList<>();
            while (result.next()){
                arrayList.add(result.getString("name"));
            }

            return arrayList.toArray(new String[0]);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public HashMap<String, Integer> getClassIDs(int organizationID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM class WHERE organization_id = ?");

            preparedStatement.setInt(1, organizationID);

            ResultSet result = preparedStatement.executeQuery();

            HashMap<String, Integer> hashMap = new HashMap<>();
            while (result.next()){
                hashMap.put(result.getString("name"), result.getInt("id"));
            }

            return hashMap;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public boolean isThisClassInThisOrganization(int classID, int organizationID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT organization_id FROM class WHERE id = ?");

            preparedStatement.setInt(1, classID);

            ResultSet result = preparedStatement.executeQuery();

            if(result.getInt("organization_id")==organizationID){
                return true;
            } else {
                return false;
            }
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void deleteClassByID(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM class WHERE id = ?;");

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
}
