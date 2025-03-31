package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IndexApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main Label
        Label welcomeLabel = new Label("WELCOME TO SIP GLOBAL E-LEARNING");
        welcomeLabel.setStyle("-fx-font-size: 27px; -fx-font-weight: bold; -fx-padding: 30;");

        Label descriptionLabel = new Label(
                "Relive your special moments with us.\n We believe in transcending boundaries and empowering learners\n"+"across the globe.\n\n"
        );
        descriptionLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10;");

        // Login Buttons
        Button adminButton = new Button("Login as Admin");
        adminButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        adminButton.setOnAction(e -> openLoginAdmin());

        Button instructorButton = new Button("Login as Instructor");
        instructorButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        instructorButton.setOnAction(e -> openLoginInstructor());

        Button userButton = new Button("Login as User");
        userButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        userButton.setOnAction(e -> openLoginInstructor());

        // Layout
        VBox layout = new VBox(15, welcomeLabel, descriptionLabel, adminButton, instructorButton, userButton);
        layout.setAlignment(Pos.CENTER);

        // Create a StackPane and add the layout to allow for a background image
        StackPane root = new StackPane(layout);
        root.setStyle(
                "-fx-background-image: url('/cybesecuritycover.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;"
        );

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SIP Global E-Learning");
        primaryStage.show();
    }

    private void openLoginAdmin() {
        // Logic to open the Admin JavaFX scene
        // Create an instance of LoginForm for Admin
        LoginAdmin loginAdmin = new LoginAdmin();
        Stage adminStage = new Stage(); // Create a new Stage for Login Admin

        try {
            // Use the LoginAdmin's start() method to display it
            loginAdmin.start(adminStage);

        } catch (Exception e) {
            System.err.println("Error opening Login Admin: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private void openLoginInstructor() {
        // Logic to open the Instructor JavaFX scene
        // Create an instance of LoginForm
        LoginInstructor loginInstructor = new LoginInstructor();
        Stage instructorStage = new Stage(); // Create a new Stage for LoginForm

        try {
            // Use the LoginForm's start() method to display it
            loginInstructor.start(instructorStage);

        } catch (Exception e) {
            System.err.println("Error opening Login: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private void openLoginForm() {
        // Create an instance of LoginForm
        LoginForm loginForm = new LoginForm();
        Stage loginStage = new Stage(); // Create a new Stage for LoginForm

        try {
            // Use the LoginForm's start() method to display it
            loginForm.start(loginStage);

        } catch (Exception e) {
            System.err.println("Error opening Login: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}