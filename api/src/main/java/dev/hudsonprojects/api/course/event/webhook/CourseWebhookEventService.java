package dev.hudsonprojects.api.course.event.webhook;

import dev.hudsonprojects.api.course.event.CourseEvent;
import dev.hudsonprojects.api.course.repository.CourseRepository;
import dev.hudsonprojects.api.webhook.event.WebhookEvent;
import dev.hudsonprojects.api.webhook.event.queue.WebhookEventSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class CourseWebhookEventService {

    private final CourseRepository courseRepository;
    private final WebhookEventSender webhookEventSender;

    @Autowired
    public CourseWebhookEventService(CourseRepository courseRepository,  WebhookEventSender webhookEventSender) {
        this.courseRepository = courseRepository;
        this.webhookEventSender = webhookEventSender;
    }


    @Async
    @TransactionalEventListener
    public void notifyListeners(CourseEvent courseEvent) {
        courseRepository.getCourseInfoById(courseEvent.courseId())
                .map(courseInfo -> new WebhookEvent(CourseEvent.TOPIC_NAME, courseInfo))
                .ifPresent(webhookEventSender::send);
    }


}
