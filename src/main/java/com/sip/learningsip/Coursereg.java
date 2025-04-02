package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Coursereg extends Application {

    private TextField courseNameField;
    private TextArea courseDescriptionField;

    @Override
    public void start(Stage primaryStage) {
        // Main VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2d2d30;");

        // Form container
        VBox formContainer = new VBox(10);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle(
                "-fx-background-image:  -fx-background-color: #383838;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);"
        );

        // Title
        Label title = new Label("Course Registration Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e0e0e0;");
        VBox.setMargin(title, new Insets(0, 0, 20, 0));

        // Input fields
        courseNameField = createInputField("Course Name");
        courseDescriptionField = createTextArea("Course Description");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #4f46e5, #4f8ef7);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 5;"
        );
        submitButton.setOnAction(e -> handleSubmit(primaryStage)); // Pass the current stage

        // Add elements to form container
        formContainer.getChildren().addAll(title, courseNameField, courseDescriptionField, submitButton);

        // Add formContainer to root
        root.getChildren().add(formContainer);
        Scene scene = new Scene(root, 600, 400);

        // Configure the stage
        primaryStage.setTitle("Course Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a styled text input field.
     */
    private TextField createInputField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setStyle(
                "-fx-background-color: #4a4a4a;" +
                        "-fx-text-fill: #e0e0e0;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );
        return field;
    }

    /**
     * Creates a styled text area.
     */
    private TextArea createTextArea(String placeholder) {
        TextArea area = new TextArea();
        area.setPromptText(placeholder);
        area.setWrapText(true);
        area.setPrefHeight(100);
        area.setStyle(
                "-fx-background-color: #4a4a4a;" +
                        "-fx-text-fill: #e0e0e0;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );
        return area;
    }

    /**
     * Handles form submission and sends data to the database.
     */
    private void handleSubmit(Stage currentStage) {
        String courseName = courseNameField.getText();
        String courseDescription = courseDescriptionField.getText();

        if (courseName.isEmpty() || courseDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill out all the fields.");
            return;
        }

        // Save data to the database
        if (saveToDatabase(courseName, courseDescription)) {
            // If saving succeeds, close the current stage and open WelcomeAdminForm
            currentStage.close();
            openWelcomeAdminForm();
        }
    }

    /**
     * Saves the course data to the database.
     */
    private boolean saveToDatabase(String courseName, String courseDescription) {
        String url = "jdbc:mysql://localhost:3306/sip"; // Replace with your database connection details
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseName);
            pstmt.setString(2, courseDescription);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course registered successfully.");
                clearForm();
                return true;
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Opens the existing WelcomeAdminForm window.
     */
    private void openWelcomeAdminForm() {
        try {
            // Assuming WelcomeAdminForm has a `start` method that accepts a Stage
            WelcomeAdminForm adminForm = new WelcomeAdminForm(); // Ensure WelcomeAdminForm is imported
            Stage adminStage = new Stage();
            adminForm.start(adminStage); // Launch WelcomeAdminForm
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        courseNameField.clear();
        courseDescriptionField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}