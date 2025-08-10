package com.example.view;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.controller.ApiController;

public class NewsView {
    private SignUpView signUpView;
    private static int page = 1;
    private Stage c2w_primaryStage;
    private VBox c2w_newsContainer;
    private ApiController apiController = new ApiController();

    public NewsView(SignUpView signUpView, Stage c2w_primaryStage) {
        this.c2w_primaryStage = c2w_primaryStage;
        this.signUpView = signUpView;
    }

    public void show() {
        showCategorySelection();
    }

    private void showCategorySelection() {
        GridPane c2w_grid = new GridPane();
        c2w_grid.setAlignment(Pos.CENTER);
        c2w_grid.setHgap(30);
        c2w_grid.setVgap(30);
        c2w_grid.setPadding(new Insets(40));
        c2w_grid.setStyle("-fx-background-color: #FFFAF5;");
        String[] categories = { "general", "business", "entertainment", "health", "science", "sports" };

        String[] gradients = {
                "-fx-background-color: linear-gradient(to right,rgb(86, 199, 227), #6dd5ed);",
                "-fx-background-color: linear-gradient(to right, #cc2b5e, #753a88);",
                "-fx-background-color: linear-gradient(to right, #ee9ca7,rgb(248, 135, 158));",
                "-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063);",
                "-fx-background-color: linear-gradient(to right, #614385, #516395);",
                "-fx-background-color: linear-gradient(to right, #e65c00, #f9d423);"
        };

        int c2w_index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                if (c2w_index < categories.length) {
                    String c2w_category = categories[c2w_index];
                    Button c2w_btn = new Button(capitalize(c2w_category));
                    c2w_btn.setPrefWidth(198);
                    c2w_btn.setPrefHeight(200);
                    c2w_btn.setStyle(gradients[c2w_index] +
                            "-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 22; -fx-background-radius: 18;");
                    c2w_btn.setOnAction(e -> handleCategorySelection(c2w_category));

                    ScaleTransition c2w_stEnlarge = new ScaleTransition(Duration.millis(180), c2w_btn);
                    c2w_stEnlarge.setToX(1.08);
                    c2w_stEnlarge.setToY(1.08);

                    ScaleTransition c2w_stNormal = new ScaleTransition(Duration.millis(180), c2w_btn);
                    c2w_stNormal.setToX(1.0);
                    c2w_stNormal.setToY(1.0);

                    c2w_btn.setOnMouseEntered(e -> c2w_stEnlarge.playFromStart());
                    c2w_btn.setOnMouseExited(e -> c2w_stNormal.playFromStart());

                    c2w_grid.add(c2w_btn, col, row);
                    c2w_index++;
                }
            }
        }

        Button c2w_logoutButton = new Button();
        c2w_logoutButton.setText("Logout");
        c2w_logoutButton.setPrefWidth(120);
        c2w_logoutButton.setPrefHeight(50);
        c2w_logoutButton.setStyle(
                "-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24;");
        c2w_logoutButton.setOnMouseEntered(e -> c2w_logoutButton.setStyle(
                "-fx-background-color: #b71c1c; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24;"));
        c2w_logoutButton.setOnMouseExited(e -> c2w_logoutButton.setStyle(
                "-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24;"));
        c2w_logoutButton.setOnAction(e -> {
            signUpView.openAuthWindow();
        });

        c2w_grid.add(c2w_logoutButton, 0, 3, 2, 1);
        Scene c2w_scene = new Scene(c2w_grid, 545, 800);
        c2w_primaryStage.setScene(c2w_scene);
        c2w_primaryStage.setTitle("C2W News App");
        c2w_primaryStage.setResizable(false);
        c2w_primaryStage.show();
    }

    private void handleCategorySelection(String c2w_category) {
        if (c2w_category.equals("general")) {
            showGeneralNews();
        } else {
            showCategorySources(c2w_category);
        }
    }

    private void showCategorySources(String c2w_category) {
        VBox c2w_sourcesContainer = new VBox(10);
        c2w_sourcesContainer.setPadding(new Insets(10));
        c2w_sourcesContainer.setAlignment(Pos.TOP_CENTER);

        ProgressIndicator c2w_loader = new ProgressIndicator();
        c2w_loader.setVisible(true);

        Button c2w_backButton = new Button("Back");
        c2w_backButton.setStyle(
                "-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24; " +
                        "-fx-padding: 8 32 8 32;");
        c2w_backButton.setOnAction(e -> showCategorySelection());
        c2w_backButton.setOnMouseEntered(e -> c2w_backButton.setStyle(
                "-fx-background-color: #b71c1c; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24; -fx-padding: 8 32 8 32;"));
        c2w_backButton.setOnMouseExited(e -> c2w_backButton.setStyle(
                "-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 24; -fx-padding: 8 32 8 32;"));

        HBox c2w_bottomBox = new HBox(16, c2w_loader, c2w_backButton);
        c2w_bottomBox.setPadding(new Insets(20, 0, 40, 0));
        c2w_bottomBox.setAlignment(Pos.CENTER);

        c2w_sourcesContainer.getChildren().add(c2w_bottomBox);

        ScrollPane c2w_scrollPane = new ScrollPane(c2w_sourcesContainer);
        c2w_scrollPane.setFitToWidth(true);
        c2w_scrollPane.setStyle("-fx-background: #f5f5f5;");

        Scene scene = new Scene(c2w_scrollPane, 545, 800);
        c2w_primaryStage.setScene(scene);
        c2w_primaryStage.show();

        Task<Void> c2w_task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                JSONArray c2w_sources = apiController.getNews(c2w_category);

                Platform.runLater(() -> {
                    c2w_loader.setVisible(false);
                    c2w_sourcesContainer.getChildren().clear();
                    if (c2w_sources.length() == 0) {
                        c2w_sourcesContainer.getChildren().add(new Label("No sources found for this category."));
                    } else {
                        for (int i = 0; i < c2w_sources.length(); i++) {
                            JSONObject source = c2w_sources.getJSONObject(i);
                            String name = source.getString("name");
                            String description = source.optString("description", "");
                            String urlSource = source.getString("url");

                            HBox c2w_card = createSourceCard(name, description, urlSource);
                            c2w_sourcesContainer.getChildren().add(c2w_card);
                        }
                    }
                });
                return null;
            }
        };
        new Thread(c2w_task).start();
    }

    // ✅ Added missing method to fix capitalize error
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Placeholder methods so code compiles
    private void showGeneralNews() {}
    private HBox createSourceCard(String name, String description, String url) { return new HBox(); }
}
