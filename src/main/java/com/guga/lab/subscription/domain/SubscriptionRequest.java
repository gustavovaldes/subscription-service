package com.guga.lab.subscription.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SubscriptionRequest {
    @NotNull
    private String email;
    private String firstName;
    private String gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotNull
    private Boolean consent;
    @NotNull
    private String newsletterId;
}