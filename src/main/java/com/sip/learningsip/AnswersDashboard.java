package com.sip.learningsip;

import com.sip.learningsip.models.AnswersModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AnswersDashboard application to fetch and display answers from the database.
 */
public class AnswersDashboard extends Application {

    // Database Configuration (Update these credentials based on your setup)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sip"; // Database URL
    private static final String DB_USER = "root"; // Database username
    private static final String DB_PASSWORD = ""; // Database password

    /**
     * Entry point for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Fetch answers from database
        List<AnswersModel> answers = fetchAnswers();

        // Display answers in the console (you can later integrate this with JavaFX UI elements)
        if (answers.isEmpty()) {
            System.out.println("No answers found in the database.");
        } else {
            System.out.println("Fetched Answers:");
            for (AnswersModel answer : answers) {
                System.out.println(answer);
            }
        }
    }

    /**
     * Fetches answers from the database with enhanced debugging.
     *
     * @return List of Answer objects fetched from the database.
     */
    public List<AnswersModel> fetchAnswers() {
        List<AnswersModel> answers = new ArrayList<>();
        String query = "SELECT * FROM answers"; // SQL query to fetch all records

        System.out.println("Starting database fetch...");
        System.out.println("Connecting to database at URL: " + DB_URL);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            System.out.println("Database connected successfully!");

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("Executing query: " + query);

                // Iterate through the result set
                while (rs.next()) {
                    // Extract data from each column
                    int id = rs.getInt("id"); // Assuming 'id' is a primary key
                    int questionId = rs.getInt("question_id"); // Check this matches your column name
                    String answerText = rs.getString("answer_text"); // Answer text
                    boolean isCorrect = rs.getBoolean("is_correct"); // Fetch boolean value
                    Integer userId = rs.getObject("userid") != null ? rs.getInt("userid") : null; // Handle nullable userId

                    // Create Answer object and populate it
                    AnswersModel answer = new AnswersModel(id, questionId, answerText, isCorrect, userId);

                    System.out.println("Fetched answer: " + answer); // Log each answer

                    // Add the Answer object to the list
                    answers.add(answer);
                }

                if (answers.isEmpty()) {
                    System.out.println("Query executed successfully, but no answers found.");
                }

            } catch (SQLException e) {
                System.err.println("Query execution failed: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }

        return answers; // Return the list of answers
    }
    /**
     * Main method to launch the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}