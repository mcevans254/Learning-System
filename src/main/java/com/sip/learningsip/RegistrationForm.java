package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrationForm extends Application {

    private TextField firstnameField;
    private TextField lastnameField;
    private TextField usernameField;
    private TextField emailField;
    private PasswordField passwordField;

    private Label errorLabel;

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Title
        Text title = new Text("Register");
        title.setFont(Font.font("Arial", 28));
        title.setFill(Color.ROYALBLUE);

        // Message
        Text message = new Text("Signup now and get full access to our app.");
        message.setFill(Color.BLACK);

        // Input Fields
        firstnameField = createInputField("First Name");
        lastnameField = createInputField("Last Name");
        usernameField = createInputField("Username");
        emailField = createInputField("Email");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");


        // Submit Button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> registerUser(primaryStage));
        registerButton.setStyle("-fx-background-color: royalblue; -fx-text-fill: white; -fx-background-radius: 10;");


        // Error Message
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Layout
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, lightgray, 10, 0.3, 0, 0);");
        form.setAlignment(Pos.CENTER);
        form.getChildren().addAll(
                title,
                message,
                createHorizontalGroup(firstnameField, lastnameField),
                usernameField,
                emailField,
                passwordField,
                registerButton,
                errorLabel
        );

        // Scene and Stage Setup
        Scene scene = new Scene(form, 400, 500);
        primaryStage.setTitle("Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create input fields
    private TextField createInputField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #eee; -fx-border-color: navy; -fx-border-radius: 10; -fx-padding: 10;");
        return textField;
    }

    // Helper method to arrange two fields horizontally
    private HBox createHorizontalGroup(TextField field1, TextField field2) {
        HBox hbox = new HBox(10, field1, field2);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Handles the user registration process.
     */
    private void registerUser(Stage primaryStage) {
        // Collect user inputs
        String firstname = firstnameField.getText().trim();
        String lastname = lastnameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate inputs
        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }

        // Save to the database
        if (saveToDatabase(firstname, lastname, username, email, password)) {
            // Registration successful: Close current form and open WelcomeForm
            openLoginForm(primaryStage);
        } else {
            errorLabel.setText("Registration failed. Please try again.");
        }
    }

    /**
     * Saves the user data to the database.
     */
    private boolean saveToDatabase(String firstname, String lastname, String username, String email, String password) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/sip"; // Adjust your DB details
        String user = "root"; // Replace with your DB username
        String dbPassword = ""; // Replace with your DB password

        String query = "INSERT INTO users (firstname, lastname, username, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Hash the password before saving
            String hashedPassword = hashPassword(password);

            // Set query parameters
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, username);
            statement.setString(4, email);
            statement.setString(5, hashedPassword);

            statement.executeUpdate(); // Execute the SQL query
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return false;
        }
    }

    /**
     * Hashes the password using SHA-256.
     */
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Opens the WelcomeForm after successful registration.
     */
    private void openLoginForm(Stage primaryStage) {
        primaryStage.close(); // Close the registration form

        // Create an instance of WelcomeForm
        LoginForm loginForm = new LoginForm();
        Stage loginStage = new Stage(); // Create a new stage for WelcomeForm

        try {
            // Use the LoginForm's start() method to display it
            loginForm.start(loginStage);
        } catch (Exception e) {
            System.err.println("Error opening WelcomeForm: " + e.getMessage());
            e.printStackTrace(); // Debugging stack trace for exceptions
        }
    }
}