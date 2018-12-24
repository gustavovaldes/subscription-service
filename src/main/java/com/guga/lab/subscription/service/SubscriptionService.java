package com.guga.lab.subscription.service;

import com.guga.lab.subscription.domain.Subscription;
import com.guga.lab.subscription.domain.SubscriptionRequest;

public interface SubscriptionService {
    Subscription create(SubscriptionRequest subscriptionRequest);
}
