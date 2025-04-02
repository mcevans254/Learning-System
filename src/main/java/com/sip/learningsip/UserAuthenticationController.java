package com.sip.learningsip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.stage.Stage;

public class UserAuthenticationController {

    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sip"; // Replace with your database URL
    private static final String DATABASE_USERNAME = "root"; // Replace with your database username
    private static final String DATABASE_PASSWORD = ""; // Replace with your database password

    /**
     * Validates the entered username and password against the database.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return The role of the user if credentials are valid ("admin" or "user"), or null if invalid.
     */
    public String validateUser(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the values of the query parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a record was found
            if (resultSet.next()) {
                // Return the user's role (either "admin" or "user")
                return resultSet.getString("role");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return null if no record is found (credentials are invalid)
        return null;
    }

    /**
     * Navigates the user to the appropriate dashboard based on their role.
     *
     * @param role The role of the user ("admin" or "user").
     * @param primaryStage The current stage to close the login screen.
     */
    public void navigateToDashboard(String role, Stage primaryStage) {
        try {
            // Close the current login screen
            primaryStage.close();

            if ("admin".equalsIgnoreCase(role)) {
                // Open the admin dashboard
                WelcomeAdminForm adminForm = new WelcomeAdminForm();
                Stage adminStage = new Stage();
                adminForm.start(adminStage);

            } else if ("user".equalsIgnoreCase(role)) {
                // Open the user dashboard
                WelcomeForm userForm = new WelcomeForm();
                Stage userStage = new Stage();
                userForm.start(userStage);
            }

        } catch (Exception e) {
            System.err.println("Failed to navigate to the dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}