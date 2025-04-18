package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CourseWebhookSubscriberEventListener {

    private final CourseWebhookSubscriberService courseWebhookSubscriberService;

    @Autowired
    public CourseWebhookSubscriberEventListener(CourseWebhookSubscriberService courseWebhookSubscriberService) {
        this.courseWebhookSubscriberService = courseWebhookSubscriberService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void subscribeCourseTopic() {
        courseWebhookSubscriberService.subscribeCourseTopic();
    }
}
