
package com.example;

import com.example.view.SignUpView;
import javafx.application.Application;

/**
 * MainApp is the entry point for the JavaFX application.
 */
public class MainApp {

    /**
     * Main method to launch the JavaFX application.
     * This is the entry point for the JavaFX runtime.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(SignUpView.class, args);
    }
}