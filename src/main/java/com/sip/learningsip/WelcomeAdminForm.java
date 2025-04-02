package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WelcomeAdminForm extends Application {

    private Button logoutButton; // Logout button moved to class-level scope
    private Button addCourseButton; // New Add Course button
    private Button addInstructorButton;
    private Button deleteUserButton; // Delete user button
    private Button deleteInstructorButton;
    @Override
    public void start(Stage primaryStage) {
        // Root layout
        VBox root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-background-color: white;");

        // Navbar
        HBox navbar = createNavbar();
        root.getChildren().add(navbar);

        // Hero Section
        VBox heroSection = createHeroSection();
        root.getChildren().add(heroSection);

        // Course Sections
        VBox courseSections = createCourseSections();
        root.getChildren().add(courseSections);

        // Footer
        VBox footer = createFooter();
        root.getChildren().add(footer);

        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Welcome to SIP GLOBAL E-Learning");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createNavbar() {
        HBox navbar = new HBox();
        navbar.setPadding(new Insets(10));
        navbar.setSpacing(20);
        navbar.setStyle("-fx-background-color: #0969c9;");

        // Search Bar
        HBox searchBar = new HBox();
        searchBar.setSpacing(10);
        searchBar.setAlignment(Pos.CENTER);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Course...");
        searchField.setPrefWidth(200);
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white;");
        searchBar.getChildren().addAll(searchField, searchButton);

        // Authentication Buttons
        HBox authButtons = new HBox();
        authButtons.setSpacing(10);

        // Logout Button
        logoutButton = new Button("Logout"); // Made it class-level
        logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        logoutButton.setOnAction(e -> performLogout());

        authButtons.getChildren().add(logoutButton);

        navbar.getChildren().addAll(searchBar, authButtons);
        HBox.setHgrow(authButtons, Priority.ALWAYS);
        authButtons.setAlignment(Pos.CENTER_RIGHT);

        return navbar;
    }

    private void performLogout() {
        System.out.println("Logout successful!");

        // Close the current stage (WelcomeAdminForm)
        Stage currentStage = (Stage) logoutButton.getScene().getWindow(); // Get the window from the logout button
        currentStage.close();

        // Open the LoginAdmin application (Placeholder logic for login application)
        IndexApplication indexApplication = new IndexApplication(); // Assuming LoginAdmin is defined elsewhere
        Stage indexApplicationStage = new Stage(); // Create a new stage for the LoginAdmin screen
        try {
            indexApplication.start(indexApplicationStage); // Launch the LoginAdmin application
        } catch (Exception e) {
            System.err.println("Error opening LoginAdmin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox createHeroSection() {
        VBox heroSection = new VBox();
        heroSection.setAlignment(Pos.CENTER);
        heroSection.setPadding(new Insets(20));
        heroSection.setSpacing(10);

        // Update with a valid, accessible image
        heroSection.setStyle("-fx-background-color: #f5f5f5; -fx-background-image:url('/cybesecuritycover.jpg')");

        Label title = new Label("Welcome to SIP GLOBAL E-Learning Administration Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.BLACK);

        Label description = new Label("The SIP Global E-Learning System is designed to provide an interactive and scalable digital learning platform for instructors and learners.");
        description.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        description.setTextFill(Color.BLACK);
        description.setWrapText(true);

        // Get Started Button
        Button getStartedButton = new Button("Get Started");
        getStartedButton.setStyle("-fx-background-color: #d6a037; -fx-text-fill: white;");

        // Add Course Button
        addCourseButton = new Button("Add Course");
        addCourseButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        addCourseButton.setOnAction(e -> openCourseregApplication());
        // Add Instructor Button
        addInstructorButton = new Button("Add Instructor");
        addInstructorButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        addInstructorButton.setOnAction(e -> openInstructorRegistration());

        // Delete Users Button
        deleteUserButton = new Button("Delete User");
        deleteUserButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteUserButton.setOnAction(e -> openDeleteUserDialog());
        // Delete Users Button
        deleteInstructorButton = new Button("Delete Instructor");
        deleteInstructorButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteInstructorButton.setOnAction(e -> openDeleteInstructorDialog());
        heroSection.getChildren().addAll(title, description, getStartedButton, addCourseButton, addInstructorButton, deleteUserButton,deleteInstructorButton); // Add the Add Course button here
        return heroSection;
    }

    private void openCourseregApplication() {
        Stage courseregStage = new Stage();
        try {
            // Launch the Coursereg application
            Coursereg coursereg = new Coursereg();
            coursereg.start(courseregStage);
        } catch (Exception e) {
            System.err.println("Error opening Coursereg application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openInstructorRegistration() {
        Stage instructorStage = new Stage();
        try {
            InstructorRegistration instructorRegistration = new InstructorRegistration();
            instructorRegistration.start(instructorStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening Instructor Registration: " + e.getMessage());
        }
    }

    private void openDeleteUserDialog() {
        Stage deleteDialog = new Stage();
        deleteDialog.setTitle("Delete User");

        VBox dialogLayout = new VBox();
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setSpacing(10);
        dialogLayout.setAlignment(Pos.CENTER);

        Label instructionLabel = new Label("Enter User ID to Delete:");
        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");

        deleteButton.setOnAction(e -> {
            String userId = userIdField.getText();
            if (userId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "User ID cannot be empty.");
            } else {
                try {
                    deleteUserFromDatabase(Integer.parseInt(userId));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
                    deleteDialog.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "User ID must be a number.");
                }
            }
        });

        dialogLayout.getChildren().addAll(instructionLabel, userIdField, deleteButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 200);
        deleteDialog.setScene(dialogScene);
        deleteDialog.show();
    }

    private void deleteUserFromDatabase(int userId) {
        String dbUrl = "jdbc:mysql://localhost:3306/sip";
        String dbUser = "root";
        String dbPassword = "";

        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + userId + " deleted successfully.");
            } else {
                System.out.println("No user found with ID " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete user: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //
    private void openDeleteInstructorDialog() {
        Stage deleteDialog = new Stage();
        deleteDialog.setTitle("Delete Instructor");

        VBox dialogLayout = new VBox();
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setSpacing(10);
        dialogLayout.setAlignment(Pos.CENTER);

        Label instructionLabel = new Label("Enter User ID to Delete:");
        TextField instructorIdField = new TextField();
        instructorIdField.setPromptText("Instructor ID");

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");

        deleteButton.setOnAction(e -> {
            String instructorId = instructorIdField.getText();
            if (instructorId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "User ID cannot be empty.");
            } else {
                try {
                    deleteUserFromDatabase(Integer.parseInt(instructorId));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
                    deleteDialog.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "User ID must be a number.");
                }
            }
        });

        dialogLayout.getChildren().addAll(instructionLabel, instructorIdField, deleteButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 200);
        deleteDialog.setScene(dialogScene);
        deleteDialog.show();
    }

    private void deleteInstructorFromDatabase(int instructorId) {
        String dbUrl = "jdbc:mysql://localhost:3306/sip";
        String dbUser = "root";
        String dbPassword = "";

        String deleteQuery = "DELETE FROM instructor WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, instructorId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + instructorId + " deleted successfully.");
            } else {
                System.out.println("No user found with ID " + instructorId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete user: " + e.getMessage());
        }
    }


//
    private VBox createCourseSections() {
        VBox courseSections = new VBox();
        courseSections.setPadding(new Insets(20));
        courseSections.setSpacing(20);

        // Main Courses Label
        Label coursesLabel = new Label("COURSES");
        coursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        coursesLabel.setAlignment(Pos.CENTER);

        // Main Courses Grid
        HBox coursesGrid = createCourseGrid(new String[]{
                "Introduction to Data Protection",
                "Rights of Data Subjects",
                "Obligations Of Data Controllers and Data Processors"
        });

        // Popular Courses Label
        Label popularCoursesLabel = new Label("Popular Courses");
        popularCoursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        popularCoursesLabel.setAlignment(Pos.CENTER);

        // Popular Courses Grid
        HBox popularCoursesGrid = createCourseGrid(new String[]{
                "React vs Vue",
                "Best JavaScript Libraries",
                "Security in Web Apps"
        });

        courseSections.getChildren().addAll(coursesLabel, coursesGrid, popularCoursesLabel, popularCoursesGrid);
        return courseSections;
    }

    private HBox createCourseGrid(String[] courseTitles) {
        HBox grid = new HBox();
        grid.setSpacing(10);
        grid.setAlignment(Pos.CENTER);

        for (String title : courseTitles) {
            VBox courseCard = new VBox();
            courseCard.setPadding(new Insets(10));
            courseCard.setSpacing(10);
            courseCard.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

            Label courseTitle = new Label(title);
            courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label courseBody = new Label(getCourseDescription(title));
            courseBody.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            courseBody.setWrapText(true);

            // Button for navigation (can add course-specific actions here)
            Button navigateButton = new Button("Add Materials for Course");
            navigateButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

            courseCard.getChildren().addAll(courseTitle, courseBody, navigateButton);
            grid.getChildren().add(courseCard);
        }

        return grid;
    }

    private String getCourseDescription(String title) {
        return "Explore this course to enhance your knowledge."; // Placeholder text for all courses
    }

    private VBox createFooter() {
        VBox footer = new VBox();
        footer.setPadding(new Insets(20));
        footer.setSpacing(10);
        footer.setStyle("-fx-background-color: #0969c9;");

        Label copyright = new Label("© " + java.time.Year.now() + " SIP GLOBAL. All rights reserved.");
        copyright.setTextFill(Color.WHITE);
        copyright.setAlignment(Pos.CENTER);

        footer.getChildren().addAll(copyright);
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}