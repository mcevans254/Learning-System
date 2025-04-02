package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddMaterials extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setStyle("-fx-background-color: #2d2d30;");

        // Title
        Label titleLabel = new Label("Add Course Materials");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; " +
                "-fx-padding: 10 0 20 0;");

        // Course ID Input - Row
        HBox courseIdRow = new HBox(10);
        courseIdRow.setAlignment(Pos.CENTER_LEFT);
        Label courseIdLabel = new Label("Course ID:");
        courseIdLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Enter Course ID");
        courseIdField.setMaxWidth(250);
        styleInputField(courseIdField);
        courseIdRow.getChildren().addAll(courseIdLabel, courseIdField);

        // File Input for PDF - Row
        HBox pdfRow = new HBox(10);
        pdfRow.setAlignment(Pos.CENTER_LEFT);
        Label pdfLabel = new Label("PDF File:");
        pdfLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        Button pdfButton = createStyledButton("Select PDF");
        Label selectedPdfLabel = new Label();
        selectedPdfLabel.setStyle("-fx-text-fill: #cccccc;");
        FileChooser pdfChooser = new FileChooser();
        pdfChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        pdfButton.setOnAction(e -> {
            File selectedPdf = pdfChooser.showOpenDialog(primaryStage);
            if (selectedPdf != null) {
                selectedPdfLabel.setText(selectedPdf.getName());
            }
        });
        pdfRow.getChildren().addAll(pdfLabel, pdfButton, selectedPdfLabel);

        // File Input for Video - Row
        HBox videoRow = new HBox(10);
        videoRow.setAlignment(Pos.CENTER_LEFT);
        Label videoLabel = new Label("Video File:");
        videoLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        Button videoButton = createStyledButton("Select Video");
        Label selectedVideoLabel = new Label();
        selectedVideoLabel.setStyle("-fx-text-fill: #cccccc;");
        FileChooser videoChooser = new FileChooser();
        videoChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov"));
        videoButton.setOnAction(e -> {
            File selectedVideo = videoChooser.showOpenDialog(primaryStage);
            if (selectedVideo != null) {
                selectedVideoLabel.setText(selectedVideo.getName());
            }
        });
        videoRow.getChildren().addAll(videoLabel, videoButton, selectedVideoLabel);

        // Save Button
        Button saveButton = createStyledButton("Save Material");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            String courseId = courseIdField.getText();
            String pdf = selectedPdfLabel.getText();
            String video = selectedVideoLabel.getText();

            if (courseId.isEmpty() || pdf.isEmpty() || video.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            } else {
                boolean isSuccess = saveToDatabase(courseId, pdf, video);
                if (isSuccess) {
                    navigateToWelcomeAdmin(primaryStage);
                }
            }
        });

        // Assemble the layout
        root.getChildren().addAll(titleLabel, courseIdRow, pdfRow, videoRow, saveButton);

        // Scene setup
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Add Materials");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper: Style input fields
    private void styleInputField(TextField field) {
        field.setStyle("-fx-background-color: #4a4a4a;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 5 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;");
    }

    // Helper: Create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #007bff;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 7 15;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 7 15; -fx-background-radius: 5; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 7 15; -fx-background-radius: 5; -fx-cursor: hand;"));
        return button;
    }

    // Save data to "sip.materials" table
    private boolean saveToDatabase(String courseId, String pdf, String video) {
        String url = "jdbc:mysql://localhost:3306/sip";
        String user = "root";
        String password = "";
        String sql = "INSERT INTO material (course_id, pdf, video) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseId);
            statement.setString(2, pdf);
            statement.setString(3, video);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Material added successfully.");
                return true; // Return success
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error saving material to the database.\n" + e.getMessage());
            e.printStackTrace();
        }
        return false; // Return failure
    }

    // Navigate to WelcomeAdminForm
    private void navigateToWelcomeAdmin(Stage primaryStage) {
        primaryStage.close();

        WelcomeAdminForm welcomeAdminForm = new WelcomeAdminForm();
        Stage welcomeAdminStage = new Stage();

        try {
            welcomeAdminForm.start(welcomeAdminStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening WelcomeForm: " + e.getMessage());
        }
    }

    // Helper: Show alert messages
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