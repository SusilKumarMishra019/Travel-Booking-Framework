package com.travel.pages;

import com.travel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//li[@data-cy='menu_Flights']")
    private WebElement flightsTab;

    @FindBy(xpath = "//span[@class='commonModal__close']")
    private WebElement closeLoginModal;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToFlights() {
        try {
            click(closeLoginModal);
        } catch (Exception e) {
            // Modal may not appear
        }
        click(flightsTab);
    }
}