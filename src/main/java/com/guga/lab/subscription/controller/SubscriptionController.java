package com.guga.lab.subscription.controller;

import com.guga.lab.subscription.domain.Subscription;
import com.guga.lab.subscription.domain.SubscriptionRequest;
import com.guga.lab.subscription.service.SubscriptionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;

@RestController
@Slf4j
@Api("Api to creae subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping(value = "/subscription")
    public ResponseEntity<Subscription> create(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(subscriptionService.create(subscriptionRequest));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateKeyException e){
        log.error("Duplicated Key ", e);
        return  ResponseEntity.unprocessableEntity().body("Email Already Registered");
    }

    @ExceptionHandler({HttpStatusCodeException.class})
    public void handleDuplicate(HttpStatusCodeException e){
        log.error("HTTP exception:", e.getMessage());
        throw e;
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleErrors(Throwable t){
        log.error("Internal Error ", t);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t.getLocalizedMessage());
    }
}
