package com.sip.learningsip;

import com.sip.learningsip.database.DatabaseConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.sql.*;

public class LoginAdmin extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLabel;

    @Override
    public void start(Stage primaryStage) {
        // Title
        Text title = new Text("Admin Login");
        title.setFont(Font.font("Arial", 28));
        title.setFill(Color.WHITE);

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font("Arial", 16));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #ecedec; -fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: white;");

        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(Font.font("Arial", 16));

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #ecedec; -fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: white;");

        // Error Label
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        // Remember Me Checkbox and Forgot Password
        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setTextFill(Color.WHITE);

        Hyperlink forgotPassword = new Hyperlink("Forgot password?");
        forgotPassword.setTextFill(Color.ORANGE);

        HBox rememberRow = new HBox(10, rememberMe, forgotPassword);
        rememberRow.setAlignment(Pos.CENTER_LEFT);

        // Sign In Button
        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-background-color: royalblue; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        signInButton.setOnAction(event -> loginUser(primaryStage));

        // Sign Up Redirect
        HBox signUpRow = new HBox();
        signUpRow.setAlignment(Pos.CENTER);
        signUpRow.setSpacing(5);

        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.setTextFill(Color.WHITE);
        Hyperlink signUpLink = new Hyperlink("Sign Up");
        signUpLink.setTextFill(Color.ORANGE);

        signUpRow.getChildren().addAll(noAccountLabel, signUpLink);

        // Or With Section
        Label orWithLabel = new Label("Or With");
        orWithLabel.setTextFill(Color.WHITE);
        orWithLabel.setFont(Font.font("Arial", 14));
        orWithLabel.setAlignment(Pos.CENTER);

        // Social Buttons
        Button googleButton = new Button("Google");
        googleButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-radius: 20; -fx-background-radius: 20;");

        Button appleButton = new Button("Apple");
        appleButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-radius: 20; -fx-background-radius: 20;");

        HBox socialRow = new HBox(10, googleButton, appleButton);
        socialRow.setAlignment(Pos.CENTER);

        // Layout
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: linear-gradient(to bottom, skyblue, darkblue); -fx-background-radius: 20;");
        form.getChildren().addAll(
                title,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                errorLabel,
                rememberRow,
                signInButton,
                signUpRow,
                orWithLabel,
                socialRow
        );

        // Scene and Stage
        Scene scene = new Scene(form, 450, 600);
        primaryStage.setTitle("Admin Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loginUser(Stage primaryStage) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in your username and password!");
            return;
        }

        // Validate user credentials
        if (authenticateUser(username, password)) {
            // Open the Welcome Form upon successful login
            openWelcomeForm(primaryStage);
        } else {
            errorLabel.setText("Invalid username or password!");
        }
    }
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT password FROM admin WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                return password.equals(storedPassword);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void openWelcomeForm(Stage primaryStage) {
        primaryStage.close();

        WelcomeAdminForm welcomeAdminFormForm = new WelcomeAdminForm();
        Stage welcomeStage = new Stage();

        try {
            welcomeAdminFormForm.start(welcomeStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening WelcomeForm: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}