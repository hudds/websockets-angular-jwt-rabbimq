package dev.hudsonprojects.api.common.messages.error;

import java.util.Objects;

public class APIMessageResolved {

    private final String message;
    private final String code;

    public APIMessageResolved(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIMessageResolved that = (APIMessageResolved) o;
        return Objects.equals(message, that.message) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code);
    }

    @Override
    public String toString() {
        return "APIMessageResolved{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
