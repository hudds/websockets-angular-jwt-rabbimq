package dev.hudsonprojects.backend.integration.coursesapi.course.webhook.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.messagequeue.exception.QueueSenderException;
import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CourseApiEventExchangeSender {

    private final RabbitTemplate template;
    private final FanoutExchange fanout;
    private final ObjectMapper objectMapper;

    @Autowired
    public CourseApiEventExchangeSender(RabbitTemplate template, @Qualifier(CourseApiEventExchangeConfiguration.EXCHANGE_QUALIFIER_NAME) FanoutExchange fanout, ObjectMapper objectMapper) {
        this.template = template;
        this.fanout = fanout;
        this.objectMapper = objectMapper;
    }

    public void send(CourseInfoDTO message) {
        try {
            template.convertAndSend(fanout.getName(), "", objectMapper.writeValueAsString(message));
        }catch (Exception e){
            throw new QueueSenderException(e);
        }

    }
}
