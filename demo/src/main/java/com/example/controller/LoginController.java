// LoginController.java

package com.example.controller;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * LoginController handles user login using Firebase Authentication REST API.
 * It provides a method to authenticate users with their email and password,
 * and returns a result indicating success or failure.
 * This class is part of a JavaFX application that uses Firebase for user authentication.
 */
public class LoginController {

    /**
     * Attempts to log in the user using Firebase Authentication REST API.
     * @param c2w_email The user's email address.
     * @param c2w_password The user's password.
     * @return Result object containing success status and a message.
     */
    public Result login(String c2w_email, String c2w_password) {
        try {
            String c2w_apiKey = "AIzaSy*"; // Replace with your actual Firebase API key
            URL c2w_url = new URL(
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + c2w_apiKey
            ); // Construct the URL for Firebase Authentication REST API

            HttpURLConnection c2w_conn = (HttpURLConnection) c2w_url.openConnection(); // Open a connection to the URL
            c2w_conn.setRequestMethod("POST"); // Set the request method to POST
            c2w_conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // Set the request content type to JSON
            c2w_conn.setDoOutput(true); // Enable output for the connection to send data

            JSONObject c2w_jsonRequest = new JSONObject(); // Create a JSON object to hold the request data
            c2w_jsonRequest.put("email", c2w_email); // Add the email to the JSON request
            c2w_jsonRequest.put("password", c2w_password); // Add the password to the JSON request
            c2w_jsonRequest.put("returnSecureToken", true); // Indicate that a secure token should be returned

            try (OutputStream c2w_os = c2w_conn.getOutputStream()) { // Get the output stream of the connection to send the request data
                byte[] c2w_input = c2w_jsonRequest.toString().getBytes(StandardCharsets.UTF_8); // Convert the JSON request to bytes
                c2w_os.write(c2w_input, 0, c2w_input.length); // Write the JSON request bytes to the output stream
            }

            if (c2w_conn.getResponseCode() == 200) { // Check if the response code is 200
                return new Result(true, "Login successful."); // Return success result if login is successful
            } else { // If the response code is not 200, login failed
                return new Result(false, "Failed to log in user."); // Return failure result with a generic message
            }
        } catch (Exception c2w_e) { // Handle any exceptions that occur during the login process
            c2w_e.printStackTrace();
            return new Result(false, "Failed to log in user: " + c2w_e.getMessage());
        }
    }

    /**
     * Simple result class for status and message.
     */
    public static class Result {
        public final boolean c2w_success; // Indicates if the operation was successful
        public final String c2w_message; // Message providing additional information about the operation

        public Result(boolean c2w_success, String c2w_message) { // Constructor to initialize the result
            this.c2w_success = c2w_success;
            this.c2w_message = c2w_message;
        }
    }
}