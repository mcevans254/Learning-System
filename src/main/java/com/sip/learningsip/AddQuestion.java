package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddQuestion extends Application {

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sip"; // Update with your actual DB URL
    private static final String DB_USER = "root"; // Update with your DB username
    private static final String DB_PASSWORD = ""; // Update with your DB password

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Question");

        // Create the layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Label and input for course ID
        Label courseIdLabel = new Label("Course ID:");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Enter Course ID");

        // Label and input for question text
        Label questionTextLabel = new Label("Question Text:");
        TextArea questionTextArea = new TextArea();
        questionTextArea.setPromptText("Enter the question here");
        questionTextArea.setPrefRowCount(3);

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String courseId = courseIdField.getText().trim();
            String questionText = questionTextArea.getText().trim();

            if (courseId.isEmpty() || questionText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Both fields are required!");
                return;
            }

            // Add the question to the database
            if (addQuestionToDatabase(courseId, questionText)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Question added successfully!");
                // Clear the input fields
                courseIdField.clear();
                questionTextArea.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the question. Please try again.");
            }
        });

        // Add components to the grid
        gridPane.add(courseIdLabel, 0, 0);
        gridPane.add(courseIdField, 1, 0);
        gridPane.add(questionTextLabel, 0, 1);
        gridPane.add(questionTextArea, 1, 1);
        gridPane.add(submitButton, 1, 2);

        // Set the Scene and Stage
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean addQuestionToDatabase(String courseId, String questionText) {
        String query = "INSERT INTO questions (question_text, course_id) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, questionText);
            preparedStatement.setInt(2, Integer.parseInt(courseId));

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}