package readers.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import readers.shared.model.ReaderEvents;

@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("LMS.readers");
    }

    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_Reader_Created")
        public Queue autoDeleteQueue_Reader_Created() {

            System.out.println("autoDeleteQueue_Reader_Created created!");
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue_Reader_Updated() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue_Reader_Deleted() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(DirectExchange direct, @Qualifier("autoDeleteQueue_Reader_Created") Queue autoDeleteQueue_Reader_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Reader_Created).to(direct).with(ReaderEvents.READER_CREATED);
        }

        @Bean
        public Binding binding2(DirectExchange direct, Queue autoDeleteQueue_Reader_Updated) {
            return BindingBuilder.bind(autoDeleteQueue_Reader_Updated).to(direct).with(ReaderEvents.READER_UPDATED);
        }

        @Bean
        public Binding binding3(DirectExchange direct, Queue autoDeleteQueue_Reader_Deleted) {
            return BindingBuilder.bind(autoDeleteQueue_Reader_Deleted).to(direct).with(ReaderEvents.READER_DELETED);
        }
    }
}
