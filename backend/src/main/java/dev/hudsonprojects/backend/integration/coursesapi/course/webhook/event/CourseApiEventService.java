package dev.hudsonprojects.backend.integration.coursesapi.course.webhook.event;

import dev.hudsonprojects.backend.appuser.notification.UserNotificationService;
import dev.hudsonprojects.backend.appuser.notification.UserNotificationTopicName;
import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.webhook.exchange.CourseApiEventExchangeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseApiEventService {

    private final CourseApiEventExchangeSender exchangeSender;
    private final UserNotificationService userNotificationService;


    @Autowired
    public CourseApiEventService(CourseApiEventExchangeSender exchangeSender, UserNotificationService userNotificationService) {
        this.exchangeSender = exchangeSender;
        this.userNotificationService = userNotificationService;
    }

    public void sendEvent(CourseInfoDTO courseInfoDTO){
        exchangeSender.send(courseInfoDTO);
    }


    public void publishMessage(CourseInfoDTO courseInfoDTO){
        userNotificationService.sendGlobalNotification(UserNotificationTopicName.COURSE_UPDATE, courseInfoDTO);
    }


}
