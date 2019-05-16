package com.cbt.helper;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;//used if we use the .By in the find elem
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import com.crossbrowsertesting.AutomatedTest;
import com.crossbrowsertesting.Builders;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.RectangleSize;//used for desktop sizing in Applitools

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CBTTest {
    private RemoteWebDriver driver;
    private CBTAPI api;
    private String score;
    private Eyes eyes;

    public void scroll() throws Exception {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

    }

    @Before
    public void setUp() throws Exception {
        String username = System.getenv("CBTUSRNAME").replaceAll("@", "%40");
        String authkey = System.getenv("CBTAUTH");
        System.out.println(username);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("name", "Atypon Testing");
        caps.setCapability("build", "1.0");
        //Mobile
        // caps.setCapability("browserName", "Chrome");
        // caps.setCapability("deviceName", "Galaxy S7");
        // caps.setCapability("platformVersion", "7.0");
        // caps.setCapability("platformName", "Android");
        // caps.setCapability("deviceOrientation", "portrait");

        //Mobile
        // caps.setCapability("browserName", "Safari");
        // caps.setCapability("deviceName", "iPad Pro Simulator");
        // caps.setCapability("platformVersion", "11.0");
        // caps.setCapability("platformName", "iOS");
        // caps.setCapability("deviceOrientation", "portrait");

        //Desktop
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "74x64");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("screenResolution", "2560x1920");
        //testy
        caps.setCapability("record_video", "true");

        api = new CBTAPI(username, authkey);

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS"));

        String hubAddress = String.format("http://%s:%s@hub.crossbrowsertesting.com:80/wd/hub", username, authkey);
        URL url = new URL(hubAddress);
        driver = new RemoteWebDriver(url, caps);

        // record a video using the API instead of the capabilities above. if this and
        // Caps is on it will record 2 and make it seem like 2 tests happened.
        // api.record_video(driver.getSessionId().toString());
    }

    @Test
    public void testToDo() {
        // open a new instance of eyes
        // for desktops
        // eyes.open(driver, "CrossBrowserTesting", "My first Selenium Java test!",
        // new RectangleSize(800, 600));

        // for mobiles. you dont need the RectangleSize()
        eyes.open(driver, "Max Window", "Testing for Window Maximize");

        // Get Site.
        driver.get("https://www.google.com");

        // Visual checkpoint
        eyes.checkWindow("Google Search");

        // for desktops.
        driver.manage().window().maximize();
        System.out.println("window maximized.");

        // Test 1:check what title equals.
        Assert.assertEquals("Google", driver.getTitle());
        System.out.println(driver.getTitle());

        // Test 2

        int tries = 0;
        WebElement button = null;
        while (tries < 10) {
            try {
                button = driver.findElementByCssSelector(
                        "#hptl > a:nth-child(1)");
                break;
            } catch (Exception e) {
                // System.out.println("error finding elem: " + e.getMessage());
                try {
                    scroll();

                } catch (Exception e2) {
                    System.out.println("error scrolling: " + e2.getMessage());
                }
                tries++;
            }
        }

        if (button != null) {
            button.click();
        }
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement logo = null;
        logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "body > main > div > div > p")));
        if (logo != null) {
            System.out.println("found elem");
        }
        Assert.assertEquals("About | Google", driver.getTitle());

        eyes.checkWindow("Our");

        System.out.println(driver.getTitle());
        

        eyes.close();
        score = "Pass";

        AutomatedTest myTest = new AutomatedTest(driver.getSessionId().toString());
        try {
            String username = System.getenv("CBTUSRNAME").replaceAll("@", "%40");
            String authkey = System.getenv("CBTAUTH");
            Builders.login(username, authkey);
            myTest.takeSnapshot();
            myTest.setScore(score);
        } catch (Exception e) {
            System.out.println("error setting score: " + e.getMessage());
        }

    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {

            // Set the score depending on the tests.
            // api.setScore(score, driver.getSessionId().toString());

            driver.quit();
            eyes.abortIfNotClosed();
            System.out.println(score);
        }
    }
}