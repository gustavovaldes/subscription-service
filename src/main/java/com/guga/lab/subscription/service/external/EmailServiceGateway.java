package com.guga.lab.subscription.service.external;

import com.guga.lab.subscription.domain.Subscription;

public interface EmailServiceGateway {
    boolean sendEmail(String email, String firstName);
    boolean sendCancellationEmail(String email, String referenceId);
    void publishEvent(Subscription subscription);

}
