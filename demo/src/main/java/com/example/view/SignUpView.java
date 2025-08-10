package com.example.view;

import com.example.MainApp;
import com.example.controller.LoginController;
import com.example.controller.SignupController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SignUpView extends Application{

    private Stage c2w_primaryStage;
    private TextField c2w_emailField;
    private PasswordField c2w_passwordField;
    private MainApp c2w_mainApp;
    private Stage c2w_stage;

    public SignUpView() {
    }

    @Override
    public void start(Stage c2w_primaryStage) {
        this.c2w_primaryStage = c2w_primaryStage;
        openAuthWindow();
    }

    public void openAuthWindow() {
        SignUpView signUpView = new SignUpView(c2w_primaryStage);
        signUpView.show();
    }

    public void openMainAppWindow() {
        c2w_primaryStage = new Stage();
        NewsView newsView = new NewsView(this, c2w_primaryStage);
        newsView.show();
    }

    public SignUpView(Stage c2w_primaryStage) {
        this.c2w_mainApp = c2w_mainApp;
        this.c2w_stage = c2w_primaryStage;

        c2w_emailField = new TextField();
        c2w_emailField.setPromptText("Enter your email");
        c2w_emailField.setFocusTraversable(false);

        c2w_passwordField = new PasswordField();
        c2w_passwordField.setPromptText("Enter your password");
        c2w_passwordField.setFocusTraversable(false);

        Button c2w_signUpButton = new Button("Sign Up");
        Button c2w_loginButton = new Button("Login");

        String c2w_buttonStyle = "-fx-background-color: #2196f3; -fx-text-fill: white;-fx-font-size: 16;"
                + " -fx-background-radius: 24; -fx-padding: 8 32 8 32;";

        c2w_signUpButton.setStyle(c2w_buttonStyle);
        c2w_loginButton.setStyle(c2w_buttonStyle);

        c2w_signUpButton.setOnMouseEntered(c2w_e ->
                c2w_signUpButton.setStyle(c2w_buttonStyle + ";-fx-background-color: #1769aa;"));
        c2w_signUpButton.setOnMouseExited(c2w_e ->
                c2w_signUpButton.setStyle(c2w_buttonStyle));
        c2w_loginButton.setOnMouseEntered(c2w_e ->
                c2w_loginButton.setStyle(c2w_buttonStyle + ";-fx-background-color: #1769aa;"));
        c2w_loginButton.setOnMouseExited(c2w_e ->
                c2w_loginButton.setStyle(c2w_buttonStyle));

        SignupController c2w_controller = new SignupController();

        c2w_signUpButton.setOnAction(c2w_event -> {
            String c2w_email = c2w_emailField.getText();
            String c2w_password = c2w_passwordField.getText();
            SignupController.Result c2w_result = c2w_controller.signup(c2w_email, c2w_password);

            if (!c2w_result.c2w_success) {
                showAlert(Alert.AlertType.ERROR, c2w_result.c2w_message);
            }

            if (c2w_result.c2w_success) {
                c2w_stage.close();
                this.openMainAppWindow();
            }
        });

        c2w_loginButton.setOnAction(c2w_event -> {
            String c2w_email = c2w_emailField.getText();
            String c2w_password = c2w_passwordField.getText();
            LoginController.Result c2w_result = new LoginController().login(c2w_email, c2w_password);

            if (!c2w_result.c2w_success) {
                showAlert(Alert.AlertType.ERROR, c2w_result.c2w_message);
            }

            if (c2w_result.c2w_success) {
                c2w_stage.close();
                this.openMainAppWindow();
            }
        });

        Image c2w_backgroundImage = new Image("file:src/main/resources/Designer.png");
        BackgroundImage c2w_background = new BackgroundImage(c2w_backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, false, false, false));
        Pane c2w_pane = new Pane();
        c2w_pane.setBackground(new Background(c2w_background));

        VBox c2w_layout = new VBox(18);
        c2w_layout.setPadding(new Insets(60, 60, 60, 60));
        c2w_layout.setMaxWidth(408);

        c2w_layout.setStyle("-fx-background-color: rgba(255,255,255,0.92);"
                + "-fx-background-radius: 24;"
                + "-fx-effect: dropshadow(gaussian, #2196f3, 18, 0.2, 8, 4);");

        Label c2w_titleLabel1 = new Label("News on NewsApp");
        c2w_titleLabel1.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #1769aa;");

        Label c2w_emailLabel = new Label("Email");
        c2w_emailLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #333;");

        Label c2w_passwordLabel = new Label("Password");
        c2w_passwordLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #333;");

        c2w_emailField.setStyle("-fx-font-size: 16; -fx-background-radius: 12; -fx-padding: 8;");
        c2w_passwordField.setStyle("-fx-font-size: 16; -fx-background-radius: 12; -fx-padding: 8;");

        c2w_layout.getChildren().addAll(
                c2w_titleLabel1, c2w_emailLabel,
                c2w_emailField, c2w_passwordLabel,
                c2w_passwordField, c2w_signUpButton,
                c2w_loginButton, c2w_signUpButton);

        c2w_layout.setAlignment(Pos.CENTER);

        c2w_pane.getChildren().add(c2w_layout);

        c2w_layout.layoutXProperty().bind(c2w_pane.widthProperty().subtract(c2w_layout.widthProperty()).divide(2));
        c2w_layout.layoutYProperty().bind(c2w_pane.heightProperty().subtract(c2w_layout.heightProperty()).divide(2));

        Scene c2w_scene = new Scene(c2w_pane, 545, 800);
        c2w_stage.setScene(c2w_scene);
        c2w_stage.setTitle("News App");

        c2w_stage.setResizable(false);
    }

    public void show() {
        c2w_stage.show();
    }

    private void showAlert(Alert.AlertType c2w_type, String c2w_message) {
        Alert c2w_alert = new Alert(c2w_type);
        c2w_alert.setTitle(c2w_type == Alert.AlertType.ERROR ? "Error" : "Info");
        c2w_alert.setHeaderText(null);
        c2w_alert.setContentText(c2w_message);
        c2w_alert.showAndWait();
    }
}
