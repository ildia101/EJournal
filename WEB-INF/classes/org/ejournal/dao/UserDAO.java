package org.ejournal.dao;

import org.ejournal.dao.entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    public void createUser(int organizationID, String role, String name, String email, String password) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO user VALUES(DEFAULT, ?, ?, ?, ?, ?)");

            workWithDB.setInt(1, organizationID);
            workWithDB.setString(2, role);
            workWithDB.setString(3, name);
            workWithDB.setString(4, email);
            workWithDB.setString(5, password);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public UserEntity getUser(String email) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM user WHERE email = ?;");

            workWithDB.setString(1, email);

            ResultSet result = workWithDB.executeQuery();
            if (result.next()) {
                return new UserEntity(result.getInt("id"), result.getInt("organization_id"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password"));
            } else {
                return null;
            }
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public UserEntity[] getEmployees(int organizationID) throws SQLException{
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM user WHERE organization_id = ? ORDER BY name;");

            workWithDB.setInt(1, organizationID);

            ResultSet result = workWithDB.executeQuery();

            ArrayList<UserEntity> arrayList = new ArrayList<>();
            while (result.next()) {
                arrayList.add(new UserEntity(result.getInt("id"), result.getInt("organization_id"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password")));
            }

            return arrayList.toArray(new UserEntity[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateUserRole(String email, String role) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE user SET role = ? WHERE email = ?;");

            workWithDB.setString(1, role);
            workWithDB.setString(2, email);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateUserOrganization(String email, int idOfNewOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE user SET organization_id = ? WHERE email = ?;");

            workWithDB.setInt(1, idOfNewOrganization);
            workWithDB.setString(2, email);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}