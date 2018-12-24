package com.guga.lab.subscription.service.external;

import com.guga.lab.subscription.urigenerator.EventServiceUriGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
public class EventServiceGatewayImpl implements EventServiceGateway {
    private RestTemplate restTemplate;
    private EventServiceUriGenerator eventServiceUriGenerator;

    @Override
    public boolean informSubscriptionCreated(String email, String referenceId) {
        URI uri = eventServiceUriGenerator.sendEvent(email, referenceId);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean informSubscriptionCreatedRollback(String email, String referenceId) {
        return false;
    }
}
