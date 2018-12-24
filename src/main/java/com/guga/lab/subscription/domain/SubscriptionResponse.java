package com.guga.lab.subscription.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class SubscriptionResponse {
    private String subscriptionId;
}
