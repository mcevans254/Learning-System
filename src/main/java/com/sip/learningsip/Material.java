package com.sip.learningsip;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;

public class Material extends Application {

    // Properties for Material class
    private final IntegerProperty courseId = new SimpleIntegerProperty();
    private final StringProperty pdf = new SimpleStringProperty();
    private final StringProperty video = new SimpleStringProperty();

    // Constructor
    public Material(int courseId, String pdf, String video) {
        this.courseId.set(courseId);
        this.pdf.set(pdf);
        this.video.set(video);
    }

    // Getters and setters for properties
    public int getCourseId() {
        return courseId.get();
    }

    public void setCourseId(int courseId) {
        this.courseId.set(courseId);
    }

    public String getPdf() {
        return pdf.get();
    }

    public void setPdf(String pdf) {
        this.pdf.set(pdf);
    }

    public String getVideo() {
        return video.get();
    }

    public void setVideo(String video) {
        this.video.set(video);
    }

    public IntegerProperty courseIdProperty() {
        return courseId;
    }

    public StringProperty pdfProperty() {
        return pdf;
    }

    public StringProperty videoProperty() {
        return video;
    }

    // Database connection details (replace placeholders with real values)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name"; // Update with your DB name
    private static final String DB_USER = "your_db_username"; // Update with your DB username
    private static final String DB_PASSWORD = "your_db_password"; // Update with your DB password

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Materials Management");

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        // Input Section
        Label courseIdLabel = new Label("Course ID:");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Enter Course ID");

        Label pdfLabel = new Label("PDF Material:");
        TextField pdfField = new TextField();
        pdfField.setPromptText("Select PDF File");
        Button selectPdfButton = new Button("Browse...");
        selectPdfButton.setOnAction(e -> selectFile(pdfField, "PDF Files", "*.pdf"));

        Label videoLabel = new Label("Video Material:");
        TextField videoField = new TextField();
        videoField.setPromptText("Select Video File");
        Button selectVideoButton = new Button("Browse...");
        selectVideoButton.setOnAction(e -> selectFile(videoField, "Video Files", "*.mp4", "*.avi", "*.mov"));

        Button addButton = new Button("Add Material");
        addButton.setOnAction(e -> addMaterials(courseIdField.getText(), pdfField.getText(), videoField.getText()));

        TableView<Material> materialTable = new TableView<>();
        setupMaterialTable(materialTable);

        Button refreshButton = new Button("Refresh Table");
        refreshButton.setOnAction(e -> loadMaterials(materialTable));

        // Add all components to the main layout
        mainLayout.getChildren().addAll(
                courseIdLabel, courseIdField,
                pdfLabel, createFileSelectionLayout(pdfField, selectPdfButton),
                videoLabel, createFileSelectionLayout(videoField, selectVideoButton),
                addButton, refreshButton, materialTable
        );

        // Load materials data on startup
        loadMaterials(materialTable);

        // Scene and Stage setup
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectFile(TextField targetField, String description, String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            targetField.setText(file.getAbsolutePath());
        }
    }

    private HBox createFileSelectionLayout(TextField field, Button button) {
        HBox layout = new HBox(10, field, button);
        layout.setAlignment(Pos.CENTER_LEFT);
        return layout;
    }

    private void addMaterials(String courseId, String pdfPath, String videoPath) {
        if (courseId.isBlank()) { // Ensure Course ID is entered
            showAlert(Alert.AlertType.ERROR, "Error", "Course ID is required!");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert record into material table
            String query = "INSERT INTO material (course_id, pdf, video) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(courseId));
            statement.setString(2, pdfPath.isBlank() ? null : pdfPath);
            statement.setString(3, videoPath.isBlank() ? null : videoPath);
            statement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Material added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add material: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Course ID must be a valid number!");
        }
    }

    private void setupMaterialTable(TableView<Material> table) {
        // Course ID column
        TableColumn<Material, Integer> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(data -> data.getValue().courseIdProperty().asObject());

        // PDF column
        TableColumn<Material, String> pdfColumn = new TableColumn<>("PDF");
        pdfColumn.setCellValueFactory(data -> data.getValue().pdfProperty());

        // Video column
        TableColumn<Material, String> videoColumn = new TableColumn<>("Video");
        videoColumn.setCellValueFactory(data -> data.getValue().videoProperty());

        // Add columns to the table
        table.getColumns().addAll(courseIdColumn, pdfColumn, videoColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadMaterials(TableView<Material> table) {
        table.getItems().clear(); // Clear existing data
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Query to fetch all materials
            String query = "SELECT course_id, pdf, video FROM material";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String pdf = resultSet.getString("pdf");
                String video = resultSet.getString("video");

                // Add row to the table
                table.getItems().add(new Material(courseId, pdf, video));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load materials: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}