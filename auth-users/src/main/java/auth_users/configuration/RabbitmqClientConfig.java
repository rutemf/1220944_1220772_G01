package auth_users.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("LMS.users");
    }

    private static class ReceiverConfig {

        public static final String EXCHANGE = "LMS.readers";
        public static final String QUEUE = "auth.users.reader.created.queue";

        @Bean
        public DirectExchange readersExchange() {
            return new DirectExchange(EXCHANGE);
        }

        @Bean
        public Queue readerCreatedQueue() {
            return QueueBuilder.durable(QUEUE).build();
        }

        @Bean
        public Binding readerCreatedBinding(
                Queue readerCreatedQueue,
                DirectExchange readersExchange) {

            return BindingBuilder.bind(readerCreatedQueue)
                    .to(readersExchange)
                    .with("READER_CREATED");
        }
    }
}
