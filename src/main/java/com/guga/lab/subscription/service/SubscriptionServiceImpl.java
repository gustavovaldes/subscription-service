package com.guga.lab.subscription.service;

import com.guga.lab.subscription.domain.Subscription;
import com.guga.lab.subscription.domain.SubscriptionRequest;
import com.guga.lab.subscription.repository.SubscriptionRepository;
import com.guga.lab.subscription.service.external.EmailServiceGateway;
import com.guga.lab.subscription.service.external.EventServiceGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository repository;
    private EmailServiceGateway emailServiceGateway;
    private EventServiceGateway eventServiceGateway;

    @Override
    public Subscription create(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = Subscription.builder()
                .email(subscriptionRequest.getEmail())
                .gender(subscriptionRequest.getGender())
                .consent(subscriptionRequest.getConsent())
                .newsletterId(subscriptionRequest.getNewsletterId())
                .firstName(subscriptionRequest.getFirstName())
                .dateOfBirth(subscriptionRequest.getDateOfBirth())
                .status("NEW")
                .build();
        subscription = repository.save(subscription);

        boolean eventServiceResponseStatus =  eventServiceGateway.informSubscriptionCreated(subscription.getEmail(),
                subscription.getId());

        //todo refactor to proper saga
        if(eventServiceResponseStatus){
            try{
                //emailServiceGateway.sendEmail(subscription.getEmail(), subscription.getFirstName());
                emailServiceGateway.publishEvent(subscription);
            }catch (Exception e){
                log.error("action:publishMailEvent, status:error", e);
                rollback(eventServiceResponseStatus, subscription);
                 throw new RuntimeException("alalal");
            }
        }else{
            log.error("action:callEventService, status:error");
            rollback(eventServiceResponseStatus, subscription);
            throw new RuntimeException("alalal");
        }
        subscription.setStatus("COMPLETED");
        return repository.save(subscription);
    }

    public void rollback(boolean revertEvent, Subscription subscription){
        if(revertEvent){
            eventServiceGateway.informSubscriptionCreatedRollback(null, null);
        }
        repository.delete(subscription);
    }


}
