package com.example.tnr;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.example.tnr", tags = "@Backend or @Checkout", plugin = {
		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "pretty",
		"html:target/cucumber-reports/cucumber-report.html" })
public class CucumberTest {
}
//mvn test -Dcucumber.filter.tags="@Checkout"
//pretty, html:target/cucumber-reports.html,

//mvn test
//mvn allure:report
//allure serve target/allure-results/