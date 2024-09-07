package dev.hudsonprojects.backend.common.lib.util;

public final class EmailUtils {

    private static final String VALID_EMAIL_PATTERN = "^[A-Za-z0-9\\.\\-_]+@[A-Za-z0-9\\.\\-_]+$";

    private EmailUtils() {}

    public static boolean isEmailValid(String email){
        return StringUtils.isNotBlank(email) && email.matches(VALID_EMAIL_PATTERN);
    }

}
