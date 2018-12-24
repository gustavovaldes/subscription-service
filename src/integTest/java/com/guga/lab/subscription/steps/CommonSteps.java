package com.guga.lab.subscription.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.guga.lab.subscription.SubscriptionServiceApplication;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = {SubscriptionServiceApplication.class},
        loader = SpringBootContextLoader.class)
public class CommonSteps {
    @LocalServerPort
    private int port;
    private ResponseEntity<String> response;
    private String authorizationToken;
    private WireMockServer server;
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Map> responseEntity;
    private String token;

    @PostConstruct
    public void initStubs() throws Exception {
        if (server == null) {
            server = new WireMockServer(1580);
            configureFor("localhost", 1580);
            server.start();


            stubFor(any(urlPathMatching("/emailService/email"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("ok")));

            stubFor(any(urlPathMatching("/eventService/event"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("ok")));
        }
    }

    @When("^I subscribe to the service with this information$")
    public void iSubscribeToTheServiceWithThisInformation(DataTable dataTable) throws Throwable {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String json = new ObjectMapper().writeValueAsString(data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        responseEntity = restTemplate.postForEntity("http://localhost:"+port+"/subscription", entity, Map.class);

    }

    @Given("^I have a valid token$")
    public void iHaveAValidToken() throws Throwable {
        token= "abc";
    }

    @Then("^The subscription should be created successfully$")
    public void theSubscriptionShouldBeCreatedSuccessfully() throws Throwable {
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @And("^The subscription information should be$")
    public void theSubscriptionInformationShouldBe(DataTable dataTable) throws Throwable {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        Map<String, String> response = responseEntity.getBody();
        for (String s : data.keySet()) {
            Assert.assertEquals(data.get(s), response.get(s));
        }
    }
}
