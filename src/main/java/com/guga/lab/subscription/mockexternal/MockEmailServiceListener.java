package com.guga.lab.subscription.mockexternal;

import com.guga.lab.subscription.domain.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockEmailServiceListener {
    @RabbitListener(queues = "${emailService.queueName}")
    public void listen(Subscription subscription) {
        log.info("Processing Subscription in Email Service: ", subscription);
        //mailProcessor.process(subscription)
        //ack auto mode for rabbit
        // generate completion event, so Subscription service can finish the subscription process been sure email was
        // really sent
    }
}
