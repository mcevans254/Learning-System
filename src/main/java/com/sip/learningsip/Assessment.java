package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Assessment extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sip";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Label questionTextLabel;
    private ComboBox<String> questionComboBox;
    private TextField answerInputField;
    private CheckBox isCorrectCheckbox;

    @Override
    public void start(Stage primaryStage) {
        // Main layout container
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #001F3F;"); // Styling the background color

        // Title
        Label titleLabel = new Label("Assessment Form");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        root.getChildren().add(titleLabel);

        // Input fields container
        VBox inputContainer = new VBox(15);
        inputContainer.setAlignment(Pos.CENTER);

        // Fetchable Question Dropdown
        questionTextLabel = new Label("Select a Question:");
        questionTextLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px;");

        questionComboBox = new ComboBox<>();
        questionComboBox.setPrefWidth(400);
        questionComboBox.setPromptText("Select a question");
        fetchQuestionsForDropdown();

        // Answer Input Field
        answerInputField = new TextField();
        answerInputField.setPromptText("Enter your answer here...");
        answerInputField.setStyle("-fx-background-color: #023047; -fx-text-fill: #D3D3D3; -fx-padding: 8; -fx-border-color: #FFC300; -fx-font-weight: bold;");

        // Is Correct Checkbox
        isCorrectCheckbox = new CheckBox("Is Correct");
        isCorrectCheckbox.setStyle("-fx-text-fill: #FFFFFF;");

        inputContainer.getChildren().addAll(questionTextLabel, questionComboBox, answerInputField, isCorrectCheckbox);

        // Buttons
        Button submitButton = new Button("Submit Answer");
        submitButton.setStyle("-fx-background-color: #FFC300; -fx-text-fill: #001F3F; -fx-font-weight: bold;");
        submitButton.setOnAction(e -> handleSubmitAnswer());

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        backButton.setOnAction(e -> goBack(primaryStage));

        HBox buttonContainer = new HBox(15, backButton, submitButton);
        buttonContainer.setAlignment(Pos.CENTER);

        // Add components to main layout
        root.getChildren().addAll(inputContainer, buttonContainer);

        // Set up the scene and stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Assessment Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Fetch questions from the database and populate the dropdown (ComboBox).
     */
    private void fetchQuestionsForDropdown() {
        List<String> questions = new ArrayList<>();
        String query = "SELECT question_text FROM questions";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                questions.add(rs.getString("question_text"));
            }

            if (!questions.isEmpty()) {
                questionComboBox.getItems().addAll(questions);
                questionComboBox.getSelectionModel().selectFirst();
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Questions", "No questions were found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    /**
     * Handles the submission of an answer.
     */
    private void handleSubmitAnswer() {
        String selectedQuestion = questionComboBox.getValue();
        String userAnswer = answerInputField.getText();
        boolean isCorrect = isCorrectCheckbox.isSelected();

        if (selectedQuestion == null || selectedQuestion.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a question.");
            return;
        }

        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please provide an answer.");
            return;
        }

        saveAnswerToDatabase(selectedQuestion, userAnswer, isCorrect);
        answerInputField.clear();
        isCorrectCheckbox.setSelected(false);
    }

    /**
     * Saves the given answer to the database.
     *
     * @param question   The selected question text.
     * @param answer     The user's answer text.
     * @param isCorrect  Whether the answer is correct or not.
     */
    private void saveAnswerToDatabase(String question, String answer, boolean isCorrect) {
        String insertQuery = "INSERT INTO answers (question_text, answer_text, is_correct) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, question);
            pstmt.setString(2, answer);
            pstmt.setBoolean(3, isCorrect);

            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Answer submitted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the answer. Please try again.");
        }
    }

    /**
     * Navigates back to the WelcomeForm.
     *
     * @param primaryStage The primary stage to switch scenes.
     */
    private void goBack(Stage primaryStage) {
        WelcomeForm welcomeForm = new WelcomeForm(); // Update: Ensure WelcomeForm exists in your project
        try {
            welcomeForm.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate back.");
        }
    }

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of alert to display.
     * @param title     The title of the alert.
     * @param message   The message to display in the alert.
     */
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