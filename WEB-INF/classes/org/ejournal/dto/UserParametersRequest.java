package org.ejournal.dto;

public class UserParametersRequest {
    private String name;
    private String email;
    private String password;

    public UserParametersRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserParametersRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
