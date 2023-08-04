package org.ejournal.dao;

import org.ejournal.dao.entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;

public class UsersDAO {

    public void createUser(String codeOfOrganization, String role, String name, String email, String password) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?)");

            workWithDB.setString(1, codeOfOrganization);
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
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM users WHERE email LIKE ?;");

            workWithDB.setString(1, email);

            ResultSet result = workWithDB.executeQuery();
            if (result.next()) {
                return new UserEntity(result.getString("organization"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password"));
            } else {
                return null;
            }
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public ArrayList<UserEntity> getEmployees(String codeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT * FROM users WHERE organization LIKE ?;");

            workWithDB.setString(1, codeOfOrganization);

            ArrayList<UserEntity> employees = new ArrayList<>();

            ResultSet result = workWithDB.executeQuery();
            while (result.next()) {
                employees.add(new UserEntity(result.getString("organization"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password")));
            }

            return employees;
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateUserOrganization(String email, String newCodeOfOrganization, String oldCodeOfOrganization) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE users SET organization = ? WHERE email = ? AND organization = ?;");

            workWithDB.setString(1, newCodeOfOrganization);
            workWithDB.setString(2, email);
            workWithDB.setString(3, oldCodeOfOrganization);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public String[] getAllOrganizations() throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("SELECT organization FROM users;");

            ArrayList<String> allOrganizations = new ArrayList<>();

            ResultSet result = workWithDB.executeQuery();
            while (result.next()) {
                allOrganizations.add(result.getString("organization"));
            }

            return allOrganizations.toArray(new String[0]);
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }

    public void updateUserRole(String codeOfOrganization, String nameOfEmployee, String role) throws SQLException {
        Connection connectToDB = null;
        PreparedStatement workWithDB = null;
        try {
            connectToDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root");
            workWithDB = connectToDB.prepareStatement("UPDATE users SET role = ? WHERE name = ? AND organization LIKE ?;");

            workWithDB.setString(1, role);
            workWithDB.setString(2, nameOfEmployee);
            workWithDB.setString(3, codeOfOrganization);

            workWithDB.executeUpdate();
        } finally {
            workWithDB.close();
            connectToDB.close();
        }
    }
}