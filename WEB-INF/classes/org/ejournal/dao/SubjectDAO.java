package org.ejournal.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SubjectDAO {
    public void addSubject(int organizationID, String nameOfSubject) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO subject VALUES(DEFAULT, ?, ?);");

            preparedStatement.setInt(1, organizationID);
            preparedStatement.setString(2, nameOfSubject);

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

    public String[] getSubjectNames(int organizationID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT name FROM subject WHERE organization_id = ? ORDER BY name;");

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

    public HashMap<String, Integer> getSubjectIDs(int organizationID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM subject WHERE organization_id = ?");

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

    public void deleteSubjectByID(int subjectID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM subject WHERE id = ?;");

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
}
