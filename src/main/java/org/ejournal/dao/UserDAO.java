package org.ejournal.dao;

import org.ejournal.dao.entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    public void createUser(int organizationID, String role, String name, String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO user VALUES(DEFAULT, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, organizationID);
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);

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

    public UserEntity getUser(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");

            preparedStatement.setString(1, email);

            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return createUserEntity(result);
            } else {
                return null;
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

    public UserEntity[] getEmployees(int organizationID) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE organization_id = ? ORDER BY name;");

            preparedStatement.setInt(1, organizationID);

            ResultSet result = preparedStatement.executeQuery();

            ArrayList<UserEntity> arrayList = new ArrayList<>();
            while (result.next()) {
                arrayList.add(createUserEntity(result));
            }

            return arrayList.toArray(new UserEntity[0]);
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

    public void updateUserRole(String email, String role) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("UPDATE user SET role = ? WHERE email = ?;");

            preparedStatement.setString(1, role);
            preparedStatement.setString(2, email);

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

    public void updateUserOrganization(String email, int idOfNewOrganization) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = new DatabaseConnectionProvider().createDatabaseConnection();
            preparedStatement = connection.prepareStatement("UPDATE user SET organization_id = ? WHERE email = ?;");

            preparedStatement.setInt(1, idOfNewOrganization);
            preparedStatement.setString(2, email);

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

    private UserEntity createUserEntity(ResultSet resultSet) throws SQLException {
        return new UserEntity(resultSet.getInt("id"), resultSet.getInt("organization_id"), resultSet.getString("role"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"));
    }
}