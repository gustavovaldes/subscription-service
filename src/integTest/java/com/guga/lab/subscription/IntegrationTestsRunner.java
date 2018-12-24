package com.guga.lab.subscription;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "junit:build/reports/integration/integrationTestResults.xml"},
        glue = {"com.guga.lab.subscription"},
        tags = "~@NotImplemented"
)
public class IntegrationTestsRunner {

    @Test
    public void test(){
        Assert.assertTrue(false);
    }
}
