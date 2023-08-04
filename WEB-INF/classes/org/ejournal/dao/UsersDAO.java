package org.ejournal.dao;

import org.ejournal.dao.entities.UserEntity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersDAO {
    Statement DBAccess;
    public UsersDAO() throws SQLException {
        DBAccess = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal", "root", "root").createStatement();
    }

    public void createUser(String codeOfOrganization, String role, String name, String email, String password) throws SQLException {
        DBAccess.executeUpdate("INSERT INTO users VALUES('" + codeOfOrganization + "', '" + role + "', '" + name + "', '" + email + "', '" + password + "')");
    }

    public UserEntity getUser(String email) throws SQLException {
        ResultSet result = DBAccess.executeQuery("SELECT * FROM users WHERE email LIKE \"" + email + "\";");
        if(result.next()){
            return new UserEntity(result.getString("organization"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password"));
        } else {
            return null;
        }
    }

    public ArrayList<UserEntity> getEmployees(String codeOfOrganization) throws SQLException {
        ArrayList<UserEntity> employees = new ArrayList<>();

        ResultSet result = DBAccess.executeQuery("SELECT * FROM users WHERE organization LIKE \"" + codeOfOrganization + "\";");
        while (result.next()){
            employees.add(new UserEntity(result.getString("organization"), result.getString("role"), result.getString("name"), result.getString("email"), result.getString("password")));
        }

        return employees;
    }

    public void updateUserOrganization(String email, String newCodeOfOrganization, String oldCodeOfOrganization) throws SQLException {
        DBAccess.executeUpdate("UPDATE users SET organization = \"" + newCodeOfOrganization + "\" WHERE email = \"" + email + "\" AND organization = \"" + oldCodeOfOrganization + "\";");
    }

    public String[] getAllOrganizations() throws SQLException {
        ArrayList<String> allOrganizations = new ArrayList<>();

        ResultSet result = DBAccess.executeQuery("SELECT organization FROM users;");
        while (result.next()){
            allOrganizations.add(result.getString("organization"));
        }

        return allOrganizations.toArray(new String[0]);
    }

    public void updateUserRole(String codeOfOrganization, String nameOfEmployee, String role) throws SQLException {
        DBAccess.executeUpdate("UPDATE users SET role = '" + role + "' WHERE name = '" + nameOfEmployee + "' AND organization LIKE '" + codeOfOrganization + "';");
    }
}