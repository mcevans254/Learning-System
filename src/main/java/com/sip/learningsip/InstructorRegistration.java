package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class InstructorRegistration extends Application {

    // Declare input fields
    private TextField fullNameField;
    private TextField emailField;
    private TextField phoneNumberField;
    private TextField courseIdField;
    private PasswordField passwordField;

    // Database Configuration
    private final String DB_URL = "jdbc:mysql://localhost:3306/sip"; // Update with your DB details
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = ""; // Update with your DB password

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
                "-fx-background-color: #383838;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0);"
        );

        // Title
        Label title = new Label("Instructor Registration Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e0e0e0;");
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // GridPane for inputs
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Initialize input fields
        fullNameField = createInputField("Full Name");
        emailField = createInputField("Email");
        phoneNumberField = createInputField("Phone Number");
        courseIdField = createInputField("Course Id");
        passwordField = createPasswordField("Password");

        // Add fields to the grid
        gridPane.add(new Label("Full Name:"), 0, 0);
        gridPane.add(fullNameField, 1, 0);
        gridPane.add(new Label("Email:"), 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(new Label("Phone Number:"), 0, 2);
        gridPane.add(phoneNumberField, 1, 2);
        gridPane.add(new Label("Course Id:"), 0, 3);
        gridPane.add(courseIdField, 1, 3);
        gridPane.add(new Label("Password:"), 0, 4);
        gridPane.add(passwordField, 1, 4);

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #4f46e5, #4f8ef7);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 5;"
        );
        submitButton.setOnAction(e -> handleSubmit());

        // Add all elements to form container
        formContainer.getChildren().addAll(title, gridPane, submitButton);

        // Add form container to root
        root.getChildren().add(formContainer);
        Scene scene = new Scene(root, 600, 400);

        // Configure the stage
        primaryStage.setTitle("Instructor Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createInputField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        styleInputField(field);
        return field;
    }

    private PasswordField createPasswordField(String placeholder) {
        PasswordField field = new PasswordField();
        field.setPromptText(placeholder);
        styleInputField(field);
        return field;
    }

    private void styleInputField(TextField field) {
        field.setStyle(
                "-fx-background-color: #4a4a4a;" +
                        "-fx-text-fill: #e0e0e0;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );
    }

    private void handleSubmit() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String courseId = courseIdField.getText();
        String password = passwordField.getText();

        if (!validateInput(fullName, email, phoneNumber, courseId, password)) return;

        if (saveToDatabase(fullName, email, phoneNumber, courseId, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Instructor registered successfully.");
            clearForm();
        }
    }

    private boolean validateInput(String fullName, String email, String phoneNumber, String courseId, String password) {
        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || courseId.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required!");
            return false;
        }

        if (!Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid email format.");
            return false;
        }

        if (!Pattern.matches("\\d{10}", phoneNumber)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid phone number. Must be 10 digits.");
            return false;
        }

        return true;
    }

    private boolean saveToDatabase(String fullName, String email, String phoneNumber, String courseId, String password) {
        String sql = "INSERT INTO instructor (FullName, email, phonenumber, courseid, Password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, courseId);
            pstmt.setString(5, password);

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not save data to the database.");
            return false;
        }
    }

    private void clearForm() {
        fullNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        courseIdField.clear();
        passwordField.clear();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}