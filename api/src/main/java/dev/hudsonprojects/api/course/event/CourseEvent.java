package dev.hudsonprojects.api.course.event;

public record CourseEvent(Long courseId) {

    public static String TOPIC_NAME = "course";
}
