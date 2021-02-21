package com.jagan.api.steps;

import com.jagan.api.base.BaseTest;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions( features = "features",
        glue = "com/jagan/api/steps", tags = "@projects", plugin = {"pretty","json:target/cucumber.json",
        "html:target/cucumber-reports/cucumber-pretty",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "rerun:target/cucumber-reports/rerun.txt"})
public class TestRunner extends BaseTest {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
