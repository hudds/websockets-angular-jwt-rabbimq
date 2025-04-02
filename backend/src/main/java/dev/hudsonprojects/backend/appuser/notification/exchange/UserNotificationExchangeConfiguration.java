package dev.hudsonprojects.backend.appuser.notification.exchange;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserNotificationExchangeConfiguration {

    public static final String QUEUE_QUALIFIER_NAME = "backendUserNotificationQueue";
    public static final String EXCHANGE_QUALIFIER_NAME = "backendUserNotificationExchange";
    public static final String EXCHANGE_NAME = "backend-user-notification";

    @Bean(name = EXCHANGE_QUALIFIER_NAME)
    public FanoutExchange apiUserSSEFanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean(name = QUEUE_QUALIFIER_NAME)
    public Queue apiUserSSEQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding apiUserSSEExchangeBinding(@Qualifier(EXCHANGE_QUALIFIER_NAME) FanoutExchange fanout,
                                             @Qualifier(QUEUE_QUALIFIER_NAME) Queue queue) {
        return BindingBuilder.bind(queue).to(fanout);
    }
}
