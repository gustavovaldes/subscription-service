package com.guga.lab.subscription.mockexternal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile({"local", "default"})
@Slf4j
@Api("Just for emulate the external services")
public class MockController {


    @GetMapping("/emailService/email")
    @ApiOperation(value = "EmailService to send email, let's use for synchronous integration")
    public ResponseEntity<String> mail() {
        log.info("Email Service: sending.....");
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/eventService/event")
    public ResponseEntity<String> event( ){
        log.info("Event Service: sending.....");
        return ResponseEntity.ok("ok");
    }
}
