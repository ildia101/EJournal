package org.ejournal.dao.entities;

public class UserEntity {
    private int id;
    private int organizationId;
    private String role;
    private String name;
    private String email;
    private String password;

    public UserEntity(int id, int organizationId, String role, String name, String email, String password) {
        this.id = id;
        this.organizationId = organizationId;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getOrganizationId() {
        return organizationId;
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
