package dev.hudsonprojects.backend.integration.coursesapi.course.webhook.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.lib.instance.InstanceId;
import dev.hudsonprojects.backend.common.messagequeue.QueueConsumer;
import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.webhook.event.CourseApiEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseApiEventQueueConsumer extends QueueConsumer<CourseInfoDTO> {

    private static Logger LOGGER = LoggerFactory.getLogger(CourseApiEventQueueConsumer.class);

    private final InstanceId instanceId;

    private final CourseApiEventService courseApiEventService;

    @Autowired
    public CourseApiEventQueueConsumer(ObjectMapper objectMapper, InstanceId instanceId, CourseApiEventService courseApiEventService) {
        super(objectMapper);
        this.instanceId = instanceId;
        this.courseApiEventService = courseApiEventService;
    }

    @Override
    @RabbitListener(queues = "#{"+ CourseApiEventExchangeConfiguration.QUEUE_QUALIFIER_NAME+".name}")
    public void receive(String message) {
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<CourseInfoDTO> getMessageType() {
        return CourseInfoDTO.class;
    }

    @Override
    protected void processMessage(CourseInfoDTO message) {
        courseApiEventService.publishMessage(message);
    }
}
