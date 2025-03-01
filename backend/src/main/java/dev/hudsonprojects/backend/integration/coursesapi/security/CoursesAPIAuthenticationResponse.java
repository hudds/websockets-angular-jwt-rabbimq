package dev.hudsonprojects.backend.integration.coursesapi.security;

import dev.hudsonprojects.backend.integration.coursesapi.CoursesAPIUser;

public class CoursesAPIAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private CoursesAPIUser user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public CoursesAPIUser getUser() {
        return user;
    }

    public void setUser(CoursesAPIUser user) {
        this.user = user;
    }
}
