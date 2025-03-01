package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.webhook.event.CourseApiEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CourseWebhookSubscriberService.WEBHOOK_LISTENER_COURSE_URI)
public class CourseWebhookListenerEndpoint {

    private final CourseApiEventService courseApiEventService;

    @Autowired
    public CourseWebhookListenerEndpoint(CourseApiEventService courseApiEventService) {
        this.courseApiEventService = courseApiEventService;
    }

    @PostMapping
    public void listen(@RequestBody CourseInfoDTO courseInfoDTO){
        courseApiEventService.sendEvent(courseInfoDTO);
    }
}
