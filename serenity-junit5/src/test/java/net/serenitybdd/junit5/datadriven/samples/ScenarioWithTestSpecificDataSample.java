package net.serenitybdd.junit5.datadriven.samples;

import net.serenitybdd.junit5.StepsInjectorTestInstancePostProcessor;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.samples.SampleScenarioSteps;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

@ExtendWith(StepsInjectorTestInstancePostProcessor.class)
public class ScenarioWithTestSpecificDataSample {

    @Managed(driver = "htmlunit")
    public WebDriver webdriver;

    @ManagedPages(defaultUrl = "http://www.google.com")
    public Pages pages;

    @Steps
    public SampleScenarioSteps steps;


    @ParameterizedTest(name = "Csv File Data Test {0}")
    @CsvFileSource(resources="/test-data/simple-data.csv",numLinesToSkip = 1)
    public void check_each_row(String name, String age,String address) throws Throwable {
        steps.data_driven_test_step(name, age);
    }
}