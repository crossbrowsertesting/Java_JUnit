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

import com.applitools.eyes.selenium.fluent.Target;
import com.crossbrowsertesting.AutomatedTest;
import com.crossbrowsertesting.Builders;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class CBTTest {
    private RemoteWebDriver driver;
    private CBTAPI api;
    private String score;

    @Before
    public void setUp() throws Exception {
        String username = System.getenv("CBTUSRNAME").replaceAll("@", "%40");
        String authkey = System.getenv("CBTAUTH");
        System.out.println(username);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("name", "Modal Button");
        caps.setCapability("build", "1.0");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "80x64");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("screenResolution", "1366x768");
        caps.setCapability("record_video", "true");
        caps.setCapability("record_network", "true");

        api = new CBTAPI(username, authkey);

        String hubAddress = String.format("http://%s:%s@hub.crossbrowsertesting.com:80/wd/hub", username, authkey);
        URL url = new URL(hubAddress);
        driver = new RemoteWebDriver(url, caps);
    }

    @Test
    public void testToDo() {

        // Get Site.
        driver.get("https://www.biotrust.com/pages/ancient-remedy");
        System.out.println("got bio-trust page");

        //for desktops.
        driver.manage().window().maximize();
        System.out.println("window maximized.");

        // Test 1:check what title equals.
        Assert.assertEquals("Ageless Multi-Collagen Protein Powder Ancient Remedy VL T1", driver.getTitle());
        System.out.println(driver.getTitle());

        try{
            Actions action = new Actions(driver);
            action.moveByOffset(0, 300).perform();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        driver.findElement(By.id("ouibounce-modal"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        
            e.printStackTrace();
        }
        
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

            driver.quit();
            
            System.out.println(score);
        }
    }
}