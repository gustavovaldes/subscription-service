package com.guga.lab.subscription.urigenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class EmailServiceUriGenerator {
    private static final String SEND_EMAIL = "/email";

    @Value("${emailService.scheme}")
    String scheme;
    @Value("${emailService.host}")
    String host;
    @Value("${emailService.port}")
    Integer port;
    @Value("${emailService.context}")
    String context;

    public URI sendEmail(String email, String name) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(context)
                .path(SEND_EMAIL)
                .queryParam("email", email)
                .queryParam("name", name)
                .build().toUri();
    }

}