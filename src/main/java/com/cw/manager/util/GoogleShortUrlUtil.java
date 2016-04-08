package com.cw.manager.util;

import java.lang.reflect.Type;
import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GoogleShortUrlUtil {

	public static String shortUrl(String url) {
		 //Instantiating the oAuth Service of Scribe-Java API
	    OAuthService oAuthService = new ServiceBuilder()

	            //Google Api Provider - Google's URL Shortener API is part of Google Platform APIs
	            .provider(GoogleApi.class)

	            /*
	                Using "anonymous" as API Key & Secret because Google's URL Shortener service
	                does not necessarily requires App identification and/or User Information Access
	             */
	            .apiKey("anonymous")
	            .apiSecret("anonymous")

	            //OAuth 2.0 scope for the Google URL Shortener API
	            .scope("https://www.googleapis.com/auth/urlshortener")

	            //build it!
	            .build();

	    //Instantiating oAuth Request of type POST and with Google URL Shortener API End Point URL
	    OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyAmIDiqCDXPr2OIXAua3C7XfBygKZfwj9Q");

	    //set the content type header to application/json - this is the type of content you are sending as payload
	    oAuthRequest.addHeader("Content-Type", "application/json");

	    //Preparing JSON payload to send url to Google URL Shortener
	    String json = String.format("{\"longUrl\": \"%s\"}", url);

	    //add xml payload to request
	    oAuthRequest.addPayload(json);

	    //send the request
	    Response response = oAuthRequest.send();

	    //print the response from server
	    System.out.println("response.getBody() = " + response.getBody());

	    //determining the generic type of map
	    Type typeOfMap = new TypeToken<Map<String, String>>() {}.getType();
	    //desrialize json to map
	    Map<String, String> responseMap = new GsonBuilder().create().fromJson(response.getBody(), typeOfMap);

	    //print id which is actually the shortened url
	    System.out.println("Shortened URL = " + responseMap.get("id"));
	    
	    return responseMap.get("id");
	}

}
