package com.guga.lab.subscription.service.external;

public interface EventServiceGateway {
    boolean informSubscriptionCreated(String email, String firstName);
    boolean informSubscriptionCreatedRollback(String email, String referenceId);
}
