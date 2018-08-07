package test.java.com.cbt.helper;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CBTTest {
    RemoteWebDriver driver = null;
    
    @Before
    public void setUp() throws Exception {
        String username = System.getenv("CBTUSRNAME").replaceAll("@", "%40");
        String authkey = System.getenv("CBTAUTH");
        System.out.println(username);
		
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("name", "CBT Java re-write");
		caps.setCapability("browserName", "Internet Explorer");
		caps.setCapability("version", "10"); // If this cap isn't specified, it will just get the latest one
		caps.setCapability("platform", "Windows 7 64-Bit");
        caps.setCapability("screenResolution", "1366x768");
        caps.setCapability("record_video", "true");       

        String hubAddress = String.format("http://%s:%s@hub.crossbrowsertesting.com:80/wd/hub", username, authkey);
        URL url = new URL(hubAddress);

        //use String.format()
        driver = new RemoteWebDriver(url, caps);
    }

    @Test
    public void testToDo(){
        driver.get("https://www.crossbrowsertesting.com");  
        if (driver.getTitle().equals("Cross Browser Testing Tool: 1500+ Real Browsers & Devices")) {
            System.out.println("Our test passed!!!!");
        }
    }
 
    @After
    public void tearDown(){
        if (driver != null) 
            driver.quit();
            //put the set score statement here.
    }
}