package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Window title and layout
        primaryStage.setTitle("Admin Dashboard");

        // Add Courses Button
        Button addCoursesButton = new Button("Add Courses");
        addCoursesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        addCoursesButton.setOnAction(e -> addCourses());

        // Register Instructor Button
        Button registerInstructorButton = new Button("Register Instructor");
        registerInstructorButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        registerInstructorButton.setOnAction(e -> registerInstructor());

        // Delete User Button
        Button deleteUserButton = new Button("Delete User");
        deleteUserButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        deleteUserButton.setOnAction(e -> deleteUser());

        // Layout
        VBox layout = new VBox(15, addCoursesButton, registerInstructorButton, deleteUserButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        // Scene
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Placeholder for "Add Courses" logic
    private void addCourses() {
        Stage addCoursesStage = new Stage();
        addCoursesStage.setTitle("Add Courses");
        VBox layout = new VBox(new javafx.scene.control.Label("Add Courses Functionality Coming Soon..."));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 300, 200);
        addCoursesStage.setScene(scene);
        addCoursesStage.show();
    }

    // Placeholder for "Register Instructor" logic
    private void registerInstructor() {
        Stage registerInstructorStage = new Stage();
        registerInstructorStage.setTitle("Register Instructor");
        VBox layout = new VBox(new javafx.scene.control.Label("Register Instructor Functionality Coming Soon..."));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 300, 200);
        registerInstructorStage.setScene(scene);
        registerInstructorStage.show();
    }

    // Placeholder for "Delete User" logic
    private void deleteUser() {
        Stage deleteUserStage = new Stage();
        deleteUserStage.setTitle("Delete User");
        VBox layout = new VBox(new javafx.scene.control.Label("Delete User Functionality Coming Soon..."));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 300, 200);
        deleteUserStage.setScene(scene);
        deleteUserStage.show();
    }
}