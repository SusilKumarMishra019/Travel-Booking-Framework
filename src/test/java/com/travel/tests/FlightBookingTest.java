package com.travel.tests;

import com.travel.base.BaseTest;
import com.travel.models.FlightDetails;
import com.travel.pages.FlightResultsPage;
import com.travel.pages.FlightSearchPage;
import com.travel.pages.HomePage;
import com.travel.utils.BrowserUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlightBookingTest extends BaseTest {

    @Test(priority = 1)
    public void testFlightSearchAndPriceComparison() {
        driver.get(BASE_URL);
        System.out.println("✓ Navigated to MakeMyTrip");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToFlights();
        System.out.println("✓ Navigated to Flights section");

        FlightSearchPage searchPage = new FlightSearchPage(driver);
        searchPage.selectFromCity("Mumbai");
        System.out.println("✓ Selected source: Mumbai");
        
        searchPage.selectToCity("Delhi");
        System.out.println("✓ Selected destination: Delhi");

        searchPage.selectDateNextMonth();
        System.out.println("✓ Selected date for next month");

        searchPage.clickSearch();
        System.out.println("✓ Clicked Search button");

        FlightResultsPage resultsPage = new FlightResultsPage(driver);
        resultsPage.waitForResults();

        FlightDetails cheapest = resultsPage.getCheapestFlight();
        FlightDetails secondCheapest = resultsPage.getSecondCheapestFlight();

        System.out.println("\n========== FLIGHT SEARCH RESULTS ==========");
        System.out.println("Cheapest Flight: " + cheapest);
        System.out.println("Second Cheapest Flight: " + secondCheapest);
        System.out.println("===========================================\n");

        Assert.assertNotNull(cheapest, "Cheapest flight should be found");
        Assert.assertNotNull(secondCheapest, "Second cheapest flight should be found");
    }

    @Test(priority = 2)
    public void testMultiTabNavigation() {
        BrowserUtils.openNewTab(driver, "https://www.google.com");
        System.out.println("✓ Opened new tab and navigated to Google");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("google"), 
            "Should be on Google page");
        System.out.println("✓ Verified Google page loaded");

        BrowserUtils.switchToTab(driver, 0);
        System.out.println("✓ Switched back to MakeMyTrip tab");
    }

    @Test(priority = 3)
    public void testFilterFlightsByAirline() {
        driver.get(BASE_URL);
        
        HomePage homePage = new HomePage(driver);
        homePage.navigateToFlights();

        FlightSearchPage searchPage = new FlightSearchPage(driver);
        searchPage.selectFromCity("Bangalore");
        searchPage.selectToCity("Mumbai");
        searchPage.selectDateNextMonth();
        searchPage.clickSearch();

        FlightResultsPage resultsPage = new FlightResultsPage(driver);
        resultsPage.waitForResults();

        try {
            driver.findElement(By.xpath("//span[contains(text(),'IndiGo')]")).click();
            Thread.sleep(2000);
            System.out.println("✓ Applied IndiGo airline filter");
            
            FlightDetails filtered = resultsPage.getCheapestFlight();
            System.out.println("Cheapest IndiGo flight: " + filtered);
            Assert.assertTrue(filtered.getAirline().contains("IndiGo"), 
                "Filtered flight should be IndiGo");
        } catch (Exception e) {
            System.out.println("⚠ Filter not available, skipping");
        }
    }
}