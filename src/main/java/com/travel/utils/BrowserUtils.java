package com.travel.utils;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;

public class BrowserUtils {
    
    public static void openNewTab(WebDriver driver, String url) {
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(url);
    }

    public static void switchToTab(WebDriver driver, int tabIndex) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabIndex < tabs.size()) {
            driver.switchTo().window(tabs.get(tabIndex));
        }
    }

    public static void closeCurrentTab(WebDriver driver) {
        driver.close();
    }
}