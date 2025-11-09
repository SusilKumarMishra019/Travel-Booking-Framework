package com.travel.pages;

import com.travel.base.BasePage;
import com.travel.models.FlightDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlightResultsPage extends BasePage {

    private By flightCards = By.xpath("//div[contains(@class,'listingCard')]");
    private By priceLocator = By.xpath(".//div[contains(@class,'priceSection')]//p");
    private By airlineLocator = By.xpath(".//p[contains(@class,'blackText')]");
    private By departureTimeLocator = By.xpath(".//p[contains(@class,'appendBottom2')]");

    public FlightResultsPage(WebDriver driver) {
        super(driver);
    }

    public void waitForResults() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<FlightDetails> getAllFlights() {
        List<FlightDetails> flights = new ArrayList<>();
        List<WebElement> cards = getElements(flightCards);

        for (int i = 0; i < Math.min(cards.size(), 10); i++) {
            try {
                WebElement card = cards.get(i);
                String priceText = card.findElement(priceLocator).getText();
                String airline = card.findElement(airlineLocator).getText();
                String departureTime = card.findElement(departureTimeLocator).getText();

                int price = extractPrice(priceText);
                flights.add(new FlightDetails(airline, departureTime, price));
            } catch (Exception e) {
                // Skip problematic cards
            }
        }
        return flights;
    }

    private int extractPrice(String priceText) {
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }

    public FlightDetails getCheapestFlight() {
        List<FlightDetails> flights = getAllFlights();
        return flights.stream()
                .min(Comparator.comparingInt(FlightDetails::getPrice))
                .orElse(null);
    }

    public FlightDetails getSecondCheapestFlight() {
        List<FlightDetails> flights = getAllFlights();
        flights.sort(Comparator.comparingInt(FlightDetails::getPrice));
        return flights.size() > 1 ? flights.get(1) : null;
    }
}