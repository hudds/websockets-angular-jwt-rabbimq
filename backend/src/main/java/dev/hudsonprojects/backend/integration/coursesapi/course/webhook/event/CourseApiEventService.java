package dev.hudsonprojects.backend.integration.coursesapi.course.webhook.event;

import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.webhook.exchange.CourseApiEventExchangeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CourseApiEventService {

    private final CourseApiEventExchangeSender exchangeSender;
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public CourseApiEventService(CourseApiEventExchangeSender exchangeSender, SimpMessagingTemplate messagingTemplate) {
        this.exchangeSender = exchangeSender;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendEvent(CourseInfoDTO courseInfoDTO){
        exchangeSender.send(courseInfoDTO);
    }


    public void publishMessage(CourseInfoDTO courseInfoDTO){
        messagingTemplate.convertAndSend("/topic/course", courseInfoDTO);
    }


}
