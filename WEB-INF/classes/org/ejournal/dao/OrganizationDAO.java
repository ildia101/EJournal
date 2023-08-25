package org.ejournal.dao;

import java.sql.*;

public class OrganizationDAO {
    public void createOrganization(String codeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO organization VALUES(DEFAULT, ?)");

            workWithDB.setString(1, codeOfOrganization);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public int getOrganizationIdByName(String codeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT id FROM organization WHERE name = ?");

            workWithDB.setString(1, codeOfOrganization);

            ResultSet result = workWithDB.executeQuery();
            if(result.next()){
                return result.getInt("id");
            } else {
                return -1;
            }
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}
