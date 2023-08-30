package org.ejournal.dao;

import java.sql.*;

public class OrganizationDAO {
    public void createOrganization(String codeOfOrganization) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO organization VALUES(DEFAULT, ?)");

            preparedStatement.setString(1, codeOfOrganization);

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

    public int getOrganizationIdByName(String codeOfOrganization) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT id FROM organization WHERE name = ?");

            preparedStatement.setString(1, codeOfOrganization);

            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("id");
            } else {
                return -1;
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
}
