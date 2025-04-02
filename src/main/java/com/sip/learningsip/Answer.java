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

public class Answer extends Application {

    private TextField courseIdField;
    private Label questionTextLabel;
    private ComboBox<String> questionTextDropdown;
    private TextArea answerTextField;
    private CheckBox isCorrectField;
    private TextField userIdField;

    // Field to store answers temporarily before submission
    private List<String[]> userAnswers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Main VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Form container
        VBox formContainer = new VBox(10);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));

        // Title (Black text)
        Label title = new Label("Answer Registration Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // GridPane for inputs
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Input fields
        courseIdField = createInputField("Course ID");
        Button fetchQuestionsButton = new Button("Fetch Questions");
        fetchQuestionsButton.setStyle("-fx-font-weight: bold; -fx-padding: 5 10;");
        fetchQuestionsButton.setOnAction(e -> fetchQuestions());

        questionTextLabel = new Label("Select a Question:");
        questionTextLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        questionTextDropdown = new ComboBox<>();
        questionTextDropdown.setPrefWidth(300);

        answerTextField = createTextArea("Answer Text");
        isCorrectField = createCheckBox("Is Correct");
        isCorrectField.setDisable(true); // Will remain disabled
        userIdField = createInputField("User ID (Optional)");

        // Add elements to GridPane
        gridPane.add(new Label("Course ID:"), 0, 0);
        gridPane.add(courseIdField, 1, 0);
        gridPane.add(fetchQuestionsButton, 2, 0);
        gridPane.add(questionTextLabel, 0, 1);
        gridPane.add(questionTextDropdown, 1, 1, 2, 1);
        gridPane.add(new Label("Answer Text:"), 0, 2);
        gridPane.add(answerTextField, 1, 2, 2, 1);
        gridPane.add(new Label("Is Correct:"), 0, 3);
        gridPane.add(isCorrectField, 1, 3);
        gridPane.add(new Label("User ID:"), 0, 4);
        gridPane.add(userIdField, 1, 4);

        // Buttons
        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
        nextButton.setOnAction(e -> handleNext());

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
        submitButton.setOnAction(e -> handleSubmit());

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
        backButton.setOnAction(e -> goBack(primaryStage));

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(backButton, nextButton, submitButton);

        // Add all elements to form container
        formContainer.getChildren().addAll(
                title,
                gridPane,
                buttonContainer
        );

        // Add formContainer to root
        root.getChildren().add(formContainer);

        Scene scene = new Scene(root, 800, 500);

        // Configure the stage
        primaryStage.setTitle("Answer Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigate back to WelcomeForm.
     */
    private void goBack(Stage primaryStage) {
        // Assuming WelcomeForm is another Application class
        WelcomeForm welcomeForm = new WelcomeForm();
        try {
            // Start WelcomeForm
            welcomeForm.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to go back to Welcome Form.");
        }
    }

    /**
     * Fetch questions based on the course ID.
     */
    private void fetchQuestions() {
        String courseIdText = courseIdField.getText();
        if (courseIdText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Course ID cannot be empty.");
            return;
        }

        int courseId;

        try {
            courseId = Integer.parseInt(courseIdText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Course ID must be a valid number.");
            return;
        }

        // Query the database to fetch questions
        List<String> questions = getQuestionsFromDatabase(courseId);

        // Display in dropdown
        if (!questions.isEmpty()) {
            questionTextDropdown.getItems().setAll(questions);
            questionTextDropdown.getSelectionModel().selectFirst();
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Questions Found", "No questions found for the given Course ID.");
        }
    }

    /**
     * Temporarily store the current question and answer, then clear the input fields.
     */
    private void handleNext() {
        String selectedQuestion = questionTextDropdown.getValue();
        String answerText = answerTextField.getText();
        String userIdText = userIdField.getText();

        if (selectedQuestion == null || answerText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please select a question and provide an answer before proceeding.");
            return;
        }

        // Add the current answer to the list
        userAnswers.add(new String[]{selectedQuestion, answerText, userIdText.isEmpty() ? "-1" : userIdText});

        // Clear the answer field (but leave question dropdown intact)
        answerTextField.clear();
        isCorrectField.setSelected(false);
    }

    /**
     * Save all answers to the database.
     */
    private void handleSubmit() {
        if (userAnswers.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Submission Error", "No answers to submit. Please add answers using the Next button.");
            return;
        }

        for (String[] answer : userAnswers) {
            String questionText = answer[0];
            String answerText = answer[1];
            int userId = Integer.parseInt(answer[2]);

            // Save each answer to the database
            saveToDatabase(questionText, answerText, userId);
        }

        // Clear the data after submission
        userAnswers.clear();

        showAlert(Alert.AlertType.INFORMATION, "Success", "All answers submitted successfully.");
    }

    /**
     * Fetches questions from the database based on Course ID.
     */
    private List<String> getQuestionsFromDatabase(int courseId) {
        List<String> questions = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/sip";
        String user = "root";
        String password = "";

        String sql = "SELECT question_text FROM questions WHERE course_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(rs.getString("question_text"));
                }
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Saves the answer to the database.
     */
    private void saveToDatabase(String questionText, String answerText, int userId) {
        String url = "jdbc:mysql://localhost:3306/sip";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO answers (question_text, answer_text, userid) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, questionText);
            pstmt.setString(2, answerText);
            if (userId == -1) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, userId);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
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

    private TextField createInputField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setStyle("-fx-text-fill: black;");
        return field;
    }

    private TextArea createTextArea(String placeholder) {
        TextArea area = new TextArea();
        area.setPromptText(placeholder);
        area.setStyle("-fx-text-fill: black;");
        return area;
    }

    private CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setStyle("-fx-text-fill: black;");
        return checkBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}