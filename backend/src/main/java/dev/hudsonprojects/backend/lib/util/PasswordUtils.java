package dev.hudsonprojects.backend.lib.util;

import static dev.hudsonprojects.backend.lib.util.StringUtils.isNotBlank;

public final class PasswordUtils {

    private PasswordUtils(){}

    public static boolean isStrong(String password){
        return isNotBlank(password) 
        && password.length() >= 8 
        && password.matches(".*[A-Z].*")
        && password.matches(".*[a-z].*")
        && password.matches(".*[0-9].*")
        && password.matches(".*[!@#$%¨&*()._*+.?^\\\\\\]\\[:;-].*");
    }
}
