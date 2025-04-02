package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IndexApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main Label
        Label welcomeLabel = new Label("WELCOME TO SIP GLOBAL E-LEARNING");
        welcomeLabel.setStyle("-fx-font-size: 27px; -fx-font-weight: bold; -fx-padding: 30;");

        // Logo (use an ImageView to display the logo)
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/cybesecuritycover.jpg")));
        logo.setFitWidth(100); // Set an appropriate width for the logo
        logo.setPreserveRatio(true); // Preserve the aspect ratio

        // Define a common button width
        double buttonWidth = 200;

        // Login Buttons
        Button adminButton = new Button("Login as Admin");
        adminButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        adminButton.setPrefWidth(buttonWidth); // Set uniform width
        adminButton.setOnAction(e -> openLoginAdmin());

        Button instructorButton = new Button("Login as Instructor");
        instructorButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        instructorButton.setPrefWidth(buttonWidth); // Set uniform width
        instructorButton.setOnAction(e -> openLoginInstructor());

        Button userButton = new Button("Login as User");
        userButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-padding: 10;");
        userButton.setPrefWidth(buttonWidth); // Set uniform width
        userButton.setOnAction(e -> openLoginForm());

        // Layout
        VBox layout = new VBox(15, logo, welcomeLabel, adminButton, instructorButton, userButton);
        layout.setAlignment(Pos.CENTER);

        // Create a StackPane for blue background
        StackPane root = new StackPane(layout);

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SIP Global E-Learning");
        primaryStage.show();
    }

    private void openLoginAdmin() {
        // Logic to open the Admin JavaFX scene
        LoginAdmin loginAdmin = new LoginAdmin();
        Stage adminStage = new Stage();

        try {
            loginAdmin.start(adminStage);
        } catch (Exception e) {
            System.err.println("Error opening Login Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openLoginInstructor() {
        // Logic to open the Instructor JavaFX scene
        LoginInstructor loginInstructor = new LoginInstructor();
        Stage instructorStage = new Stage();

        try {
            loginInstructor.start(instructorStage);
        } catch (Exception e) {
            System.err.println("Error opening Login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openLoginForm() {
        // Logic to open the User login scene
        LoginForm loginForm = new LoginForm();
        Stage loginStage = new Stage();

        try {
            loginForm.start(loginStage);
        } catch (Exception e) {
            System.err.println("Error opening Login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}