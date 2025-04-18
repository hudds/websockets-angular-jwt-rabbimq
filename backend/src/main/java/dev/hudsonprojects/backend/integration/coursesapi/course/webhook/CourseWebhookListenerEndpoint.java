package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import dev.hudsonprojects.backend.common.exception.UnauthorizedException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.webhook.event.CourseApiEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(CourseWebhookSubscriberService.WEBHOOK_LISTENER_COURSE_URI)
public class CourseWebhookListenerEndpoint {

    @Value("${integration.courses_api.webhook.course.token}")
    private String token;
    private final CourseApiEventService courseApiEventService;

    @Autowired
    public CourseWebhookListenerEndpoint(CourseApiEventService courseApiEventService) {
        this.courseApiEventService = courseApiEventService;
    }

    @PostMapping
    public void listen(@RequestBody CourseInfoDTO courseInfoDTO, @RequestHeader("token") String token){
        if(!Objects.equals(this.token, token)){
            throw new UnauthorizedException(ErrorDetailsBuilder.unauthorized().build());
        }
        courseApiEventService.sendEvent(courseInfoDTO);
    }
}
