// ApiController.java

package com.example.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiController {

    public JSONArray getNews(String c2w_category) {
        try {
            String c2w_apiKey = "**be80b854dbb2c86de6"; // Replace with your actual key
            String c2w_urlStr = "https://newsapi.org/v2/top-headlines/sources?category=" 
                                + c2w_category + "&apiKey=" + c2w_apiKey; 
            // Construct the URL for fetching sources based on the selected category

            URL url = new URL(c2w_urlStr); // Create a URL object with the constructed URL string
            HttpURLConnection c2w_conn = (HttpURLConnection) url.openConnection(); // Open a connection to the URL
            c2w_conn.setRequestMethod("GET"); // Set the request method to GET

            BufferedReader c2w_in = new BufferedReader(new InputStreamReader(c2w_conn.getInputStream()));
            StringBuilder response = new StringBuilder(); // Create a StringBuilder to hold the response
            String c2w_inputLine; // Variable to hold each line of the response

            while ((c2w_inputLine = c2w_in.readLine()) != null) { // Read each line of the response
                response.append(c2w_inputLine); // Append the line to the response StringBuilder
            }
            c2w_in.close(); // Close the BufferedReader

            JSONObject c2w_jsonResponse = new JSONObject(response.toString()); // Parse the response into a JSONObject
            JSONArray c2w_sources = c2w_jsonResponse.getJSONArray("sources"); // Get the "sources" array
            return c2w_sources;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}