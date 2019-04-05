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
        caps.setCapability("name", "Basic Test Example");
        caps.setCapability("build", "1.0");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("deviceName", "Galaxy S8");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceOrientation", "portrait");
        // caps.setCapability("version", "15"); // If this cap isn't specified, it will
        // just get the latest one
        
        // caps.setCapability("build", "1.0"); //Set a build number.
        // caps.setCapability("record_network", "true");

        api = new CBTAPI(username, authkey);

        String hubAddress = String.format("http://%s:%s@hub.crossbrowsertesting.com:80/wd/hub", username, authkey);
        URL url = new URL(hubAddress);
        driver = new RemoteWebDriver(url, caps);
        // record a video using the API instead of the capabilities above.
        api.record_video(driver.getSessionId().toString());
    }


    @Test
    public void testToDo() {
    
        // test 1: Get title.
        driver.get("https://disney.com");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for(int i = 0; i < 10; i++){
            js.executeScript("window.scrollBy(0, 10)");
        }
        // test 2:check what title equals.
        //driver.manage().window().maximize();
        Assert.assertEquals("Disney.com | The official home for all things Disney", driver.getTitle());
        System.out.println(driver.getTitle());
        score = "Pass";
        driver.findElementByCssSelector("#ref-1-5 > div > ul > span:nth-child(1) > div > div > a > div > div > img").click();
        //elem = driver.findElement(By.xpath("//*[@id="ref-1-5"]/div/ul/span[1]/div/div/a/div/div/img")).click();
        //driver.findElementByXPath('//*[@id="ref-1-5"]/div/ul/span[1]/div/div/a/div/div/img').click();
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
            //api.setScore(score, driver.getSessionId().toString()):
            driver.quit();
            System.out.println(score);
        }
    }
}