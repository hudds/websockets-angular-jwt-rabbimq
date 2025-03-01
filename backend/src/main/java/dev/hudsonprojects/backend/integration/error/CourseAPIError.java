package dev.hudsonprojects.backend.integration.error;


import java.util.ArrayList;
import java.util.List;

public class CourseAPIError {

    private CourseAPIError type;
    private CourseAPIMessage message;
    private String messageCode;
    private List<CourseAPIFieldError> fieldErrors = new ArrayList<>();

    public CourseAPIError getType() {
        return type;
    }

    public void setType(CourseAPIError type) {
        this.type = type;
    }

    public CourseAPIMessage getMessage() {
        return message;
    }

    public void setMessage(CourseAPIMessage message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public List<CourseAPIFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<CourseAPIFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
