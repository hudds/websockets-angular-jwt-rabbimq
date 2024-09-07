package dev.hudsonprojects.backend.common.lib.util;



public final class PasswordUtils {

    private PasswordUtils(){}

    public static boolean isStrong(String password){
        return StringUtils.isNotBlank(password) 
        && password.length() >= 8 
        && password.matches(".*[A-Z].*")
        && password.matches(".*[a-z].*")
        && password.matches(".*[0-9].*")
        && password.matches(".*[!@#$%¨&*()._*+.?^\\\\\\]\\[:;-].*");
    }
}
