package dev.hudsonprojects.backend.integration.coursesapi.error;


import java.util.ArrayList;
import java.util.List;

public class CourseAPIError {

    private CourseAPIErrorType type;
    private CourseAPIMessage message;
    private String messageCode;
    private List<CourseAPIFieldError> fieldErrors = new ArrayList<>();

    public CourseAPIErrorType getType() {
        return type;
    }

    public void setType(CourseAPIErrorType type) {
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
