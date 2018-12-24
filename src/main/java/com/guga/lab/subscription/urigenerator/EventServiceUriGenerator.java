package com.guga.lab.subscription.urigenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class EventServiceUriGenerator {
    private static final String SEND_EVENT = "/event";

    @Value("${eventService.scheme}")
    String scheme;
    @Value("${eventService.host}")
    String host;
    @Value("${eventService.port}")
    Integer port;
    @Value("${eventService.context}")
    String context;

    public URI sendEvent(String email, String id) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(context)
                .path(SEND_EVENT)
                .queryParam("email", email)
                .queryParam("id", id)
                .build().toUri();
    }

}