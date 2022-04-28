package ru.gb.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.gb.DriverInitializer;

public class BrandSteps {

    private WebDriver webDriver = null;

    @Given("^I open web browser$")
    public void iOpenWebBrowser() {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to brand\\.html page$")
    public void iNavigateToBrandHtmlPage() throws Throwable {
        Thread.sleep(3000);
        webDriver.get(DriverInitializer.getProperty("brand.url"));
    }

    @And("^I click on add brand button$")
    public void iClickOnAddBrandButton() throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("btn-brand"));
        webElement.click();
    }

    @And("^I provide brand name as \"([^\"]*)\"$")
    public void iProvideBrandNameAs(String name) throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(name);
    }

    @Then("^click submit button$")
    public void clickSubmitButton() throws InterruptedException {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("btn-submit"));
        webElement.click();
    }
}
