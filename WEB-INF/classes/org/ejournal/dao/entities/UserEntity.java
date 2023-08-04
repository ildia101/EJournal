package org.ejournal.dao.entities;

public class UserEntity {
    private String organization;
    private String role;
    private String name;
    private String email;
    private String password;

    public UserEntity(String organization, String role, String name, String email, String password) {
        this.organization = organization;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
