package com.travel.pages;

import com.travel.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightSearchPage extends BasePage {

    @FindBy(id = "fromCity")
    private WebElement fromCityField;

    @FindBy(xpath = "//input[@placeholder='From']")
    private WebElement fromInput;

    @FindBy(id = "toCity")
    private WebElement toCityField;

    @FindBy(xpath = "//input[@placeholder='To']")
    private WebElement toInput;

    @FindBy(xpath = "//span[contains(text(),'Departure')]")
    private WebElement departureDate;

    @FindBy(xpath = "//a[contains(@class,'primaryBtn')]")
    private WebElement searchButton;

    public FlightSearchPage(WebDriver driver) {
        super(driver);
    }

    public void selectFromCity(String city) {
        click(fromCityField);
        sendKeys(fromInput, city);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fromInput.sendKeys(Keys.ARROW_DOWN);
        fromInput.sendKeys(Keys.ENTER);
    }

    public void selectToCity(String city) {
        click(toCityField);
        sendKeys(toInput, city);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toInput.sendKeys(Keys.ARROW_DOWN);
        toInput.sendKeys(Keys.ENTER);
    }

    public void selectDateNextMonth() {
        click(departureDate);
        LocalDate nextMonthDate = LocalDate.now().plusMonths(1).withDayOfMonth(15);
        String ariaLabel = nextMonthDate.format(DateTimeFormatter.ofPattern("EEE MMM dd yyyy"));
        
        try {
            WebElement dateElement = driver.findElement(
                By.xpath("//div[@aria-label='" + ariaLabel + "']")
            );
            click(dateElement);
        } catch (Exception e) {
            List<WebElement> dates = driver.findElements(
                By.xpath("//div[contains(@class,'dateInnerCell')]")
            );
            if (!dates.isEmpty()) {
                click(dates.get(15));
            }
        }
    }

    public void clickSearch() {
        click(searchButton);
    }
}