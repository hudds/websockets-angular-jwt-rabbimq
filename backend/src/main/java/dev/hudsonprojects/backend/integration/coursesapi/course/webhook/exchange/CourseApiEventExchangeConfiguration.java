package dev.hudsonprojects.backend.integration.coursesapi.course.webhook.exchange;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseApiEventExchangeConfiguration {

    public static final String QUEUE_QUALIFIER_NAME = "apiCourseEventQueue";
    public static final String EXCHANGE_QUALIFIER_NAME = "apiCourseEventExchange";
    public static final String EXCHANGE_NAME = "backend-api-course-event";

    @Bean(name = EXCHANGE_QUALIFIER_NAME)
    public FanoutExchange apiCourseEventFanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean(name = QUEUE_QUALIFIER_NAME)
    public Queue apiCourseEventQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding courseApiEventExchangeBinding(@Qualifier(EXCHANGE_QUALIFIER_NAME) FanoutExchange fanout,
                                                 @Qualifier(QUEUE_QUALIFIER_NAME) Queue queue) {
        return BindingBuilder.bind(queue).to(fanout);
    }
}
