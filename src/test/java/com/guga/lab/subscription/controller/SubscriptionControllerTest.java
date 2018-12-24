package com.guga.lab.subscription.controller;

import com.guga.lab.subscription.domain.Subscription;
import com.guga.lab.subscription.domain.SubscriptionRequest;
import com.guga.lab.subscription.service.SubscriptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;
    @InjectMocks
    private SubscriptionController subscriptionController;
    @Mock
    private SubscriptionRequest subscriptionRequest;

    @Test
    public void create() {
        Subscription subscription = Subscription.builder().id("abc").build();
        Mockito.when(subscriptionService.create(subscriptionRequest)).thenReturn(subscription);
        ResponseEntity<Subscription> reponse = subscriptionController.create(subscriptionRequest);
        assertEquals(HttpStatus.OK, reponse.getStatusCode());
        assertEquals(subscription, reponse.getBody());
        Mockito.verify(subscriptionService).create(subscriptionRequest);
        Mockito.verifyNoMoreInteractions(subscriptionService);
    }

    @Test
    public void handleDuplicate() {
    }

    @Test
    public void handleErrors() {
    }
}