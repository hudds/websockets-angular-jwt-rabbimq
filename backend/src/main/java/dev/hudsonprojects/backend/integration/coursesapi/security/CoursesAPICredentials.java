package dev.hudsonprojects.backend.integration.coursesapi.security;

public class CoursesAPICredentials {

    private final String username;
    private final String password;

    public CoursesAPICredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
