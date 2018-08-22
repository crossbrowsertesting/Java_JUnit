// package com.cbt.helper;

// import com.mashape.unirest.http.HttpResponse;
// import com.mashape.unirest.http.JsonNode;
// import com.mashape.unirest.http.Unirest;
// import com.mashape.unirest.http.exceptions.UnirestException;

// public class CBTAPI {
//     private String username, authkey;

//     public CBTAPI(String username, String authkey) {
//         this.username = username;
//         this.authkey = authkey;
//     }

//     public void setScore(String score, String seleniumSessionId) throws UnirestException {
//         HttpResponse<JsonNode> response = Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumSessionId}")
//                                                         .basicAuth(username, authkey)
//                                                         .routeParam("seleniumSessionId", seleniumSessionId)
//                                                         .field("action","set_score")
//                                                         .field("score", score)
//                                                         .asJson();                                           
//     }
    
// }