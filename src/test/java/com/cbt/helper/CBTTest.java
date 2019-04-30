package com.cbt.helper;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.crossbrowsertesting.AutomatedTest;
import com.crossbrowsertesting.Builders;

import org.openqa.selenium.By;//used if we use the .By in the find elem
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement; 

public class CBTTest {
    private RemoteWebDriver driver;
    private CBTAPI api;
    private String score;

    public void scroll() throws Exception {
        
        JavascriptExecutor js = (JavascriptExecutor)driver;
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
        caps.setCapability("browserName", "Safari");
        caps.setCapability("deviceName", "iPhone 7 Simulator");
        caps.setCapability("platformVersion", "10.2");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("record_video", "true");

        api = new CBTAPI(username, authkey);

        String hubAddress = String.format("http://%s:%s@hub.crossbrowsertesting.com:80/wd/hub", username, authkey);
        URL url = new URL(hubAddress);
        driver = new RemoteWebDriver(url, caps);

        // record a video using the API instead of the capabilities above. if this and Caps is on it will record 2 and make it seem like 2 tests happened. 
        //api.record_video(driver.getSessionId().toString());
    }


    @Test
    public void testToDo() {
    
        // test 1: Get title.
         driver.get("https://dlnext.acm.org/toc/csur/current");

        //for desktops. 
        //driver.manage().window().maximize();

        // test 2:check what title equals.
        Assert.assertEquals("CSUR: Vol 52, No 2", driver.getTitle());
        System.out.println(driver.getTitle());
        
        
        int tries = 0;
        WebElement button = null; 
        while (tries < 10) {
            try{
            button = driver.findElementByCssSelector("#pb-page-content > div > header > div.header__fixed-items.fixed.auto-hide-bar.fixed-element > div.container.header--first-row > div.pull-left > div.header__logo1 > a > img");
            break;
            }catch (Exception e) {
                //System.out.println("error finding elem: " + e.getMessage());
                try{
                    scroll();
                    
                }catch(Exception e2) {
                    System.out.println("error scrolling: " + e2.getMessage());
                }
                tries++;
            }
        }
        if (button != null) {
            button.click();
        }
        score = "Pass";
        
        AutomatedTest myTest = new AutomatedTest(driver.getSessionId().toString());
        try {
            String username = System.getenv("CBTUSRNAME").replaceAll("@", "%40");
            String authkey = System.getenv("CBTAUTH");
            Builders.login(username, authkey);
            myTest.takeSnapshot();
            myTest.setScore(score);
            }catch (Exception e){
                System.out.println("error setting score: " + e.getMessage());
            }

        }
    

    @After
    public void tearDown() throws Exception {
        if (driver != null) {

            // Set the score depending on the tests.
            //api.setScore(score, driver.getSessionId().toString());

            driver.quit();
            System.out.println(score);
        }
    }
}