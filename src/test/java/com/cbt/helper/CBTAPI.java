// Getting started: http://docs.seleniumhq.org/docs/03_webdriver.jsp
// API details: https://github.com/SeleniumHQ/selenium#selenium 

// Unirest is the recommended way to interact with RESTful APIs in Java
// http://unirest.io/java.html

// runs test against http://crossbrowsertesting.github.io/selenium_example_page.html

package com.cbt.helper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CBTAPI {
    private String username, authkey;

    public CBTAPI(String username, String authkey) {
        this.username = username; // Your username
        this.authkey = authkey; // Your authkey
    }

    public void setScore(String score, String seleniumSessionId) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumSessionId}")
                                                        .basicAuth(username, authkey)
                                                        .routeParam("seleniumSessionId", seleniumSessionId)
                                                        .field("action","set_score")
                                                        .field("score", score)
                                                        .asJson();                                           
    }

    public void record_video(String seleniumSessionId) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://crossbrowsertesting.com/api/v3/selenium/{seleniumSessionId}/videos")
                                                        .basicAuth(username, authkey)
                                                        .routeParam("seleniumSessionId", seleniumSessionId)
                                                        .asJson();                                                                                            
    }
    
}