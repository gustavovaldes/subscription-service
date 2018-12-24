package com.guga.lab.subscription.service.external;


import com.guga.lab.subscription.domain.Subscription;
import com.guga.lab.subscription.urigenerator.EmailServiceUriGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
public class EmailServiceGatewayImpl implements EmailServiceGateway {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private EmailServiceUriGenerator emailServiceUriGenerator;

    @Override
    public boolean sendEmail(String email, String firstName) {
        URI uri = emailServiceUriGenerator.sendEmail(email, firstName);
        log.info("{action:subscriptionCreated, downstreamService:emailService, data:{ email:{}, " +
                "firstName:{}" +
                " }", email, firstName);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response.getStatusCode().is2xxSuccessful();

    }

    @Override
    public boolean sendCancellationEmail(String email, String referenceId) {
        log.info("{action:subscriptionCreated, downstreamService:emailService");
        return true;
    }

    public void publishEvent(Subscription subscription){
        rabbitTemplate.convertAndSend(subscription);
    }
}
