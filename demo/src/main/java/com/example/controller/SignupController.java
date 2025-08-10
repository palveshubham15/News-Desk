// SignupController.java

package com.example.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * SignupController handles user registration using Firebase.
 * It provides methods to create new user accounts with email and password.
 * This class is part of a JavaFX application that uses Firebase for user authentication.
 * It allows users to sign up by providing their email and password.
 * The SignupController class is responsible for interacting with Firebase to create new user accounts.
 * It uses Firebase to manage user records, and provides a simple interface for signing up users.
 * The class includes methods for validating user input and handling exceptions,
 * ensuring a smooth user experience.
 */
public class SignupController {

    /**
     * Attempts to sign up the user using Firebase.
     * @param c2w_email   The user’s email address.
     * @param c2w_password The user’s password.
     * @return Result object containing success status and a message.
     */
    public Result signup(String c2w_email, String c2w_password) {
        if (c2w_email == null || c2w_email.isEmpty() || c2w_password == null || c2w_password.isEmpty()) {
            return new Result(false, "Email and password cannot be empty.");
        }

        try {
            String c2w_apiKey = "AIzaSy*"; // Replace with your actual Firebase API key
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + c2w_apiKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", c2w_email);
            requestBody.put("password", c2w_password);
            requestBody.put("returnSecureToken", true);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.toString().getBytes("UTF-8"));
            }

            // Get response
            int responseCode = conn.getResponseCode();
            InputStream responseStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            String response;

            try (Scanner scanner = new Scanner(responseStream)) {
                scanner.useDelimiter("\\A");
                response = scanner.hasNext() ? scanner.next() : "";
            }

            if (responseCode == 200) {
                JSONObject jsonResponse = new JSONObject(response);
                System.out.println("Successfully created user: " + jsonResponse.getString("localId"));
                return new Result(true, "User created successfully.");
            } else {
                JSONObject errorResponse = new JSONObject(response);
                String errorMessage = errorResponse.has("error") ?
                        errorResponse.getJSONObject("error").getString("message") :
                        "Unknown error";
                return new Result(false, "Failed to create user: " + errorMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception occurred: " + e.getMessage());
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