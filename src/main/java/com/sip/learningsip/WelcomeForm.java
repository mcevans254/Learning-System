package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WelcomeForm extends Application {

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
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        logoutButton.setOnAction(e -> {
                    ((Stage) logoutButton.getScene().getWindow()).close(); // Close the current modal
                    openloginForm();
                });

        authButtons.getChildren().addAll(logoutButton);

        navbar.getChildren().addAll(searchBar, authButtons);
        HBox.setHgrow(authButtons, Priority.ALWAYS);
        authButtons.setAlignment(Pos.CENTER_RIGHT);

        return navbar;
    }

    private VBox createHeroSection() {
        VBox heroSection = new VBox();
        heroSection.setAlignment(Pos.CENTER);
        heroSection.setPadding(new Insets(20));
        heroSection.setSpacing(10);
        heroSection.setStyle("-fx-background-image: url('/bg.jpg'); -fx-background-size: cover;");

        Label title = new Label("Welcome to SIP GLOBAL E-Learning");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Label description = new Label("The SIP Global E-Learning System is designed to provide an interactive and scalable digital learning platform for instructors and learners.");
        description.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        description.setTextFill(Color.WHITE);
        description.setWrapText(true);

        Button getStartedButton = new Button("Get Started");
        getStartedButton.setStyle("-fx-background-color: #d6a037; -fx-text-fill: white;");

        heroSection.getChildren().addAll(title, description, getStartedButton);
        return heroSection;
    }

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
            courseCard.setPadding(new Insets(15));
            courseCard.setSpacing(10);
            courseCard.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

            Label courseTitle = new Label(title);
            courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label courseBody = new Label(getCourseDescription(title));
            courseBody.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            courseBody.setWrapText(true);

            // Button for starting the course
            Button startCourseButton = new Button("Start Course");
            startCourseButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
            startCourseButton.setOnAction(e -> openCourseApplication(title));

            // Button for starting the assessment
            Button startAssessmentButton = new Button("Start Assessment");
            startAssessmentButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
            startAssessmentButton.setOnAction(e -> startAssessment(title)); // Add functionality to start assessment

            courseCard.getChildren().addAll(courseTitle, courseBody, startCourseButton, startAssessmentButton);
            grid.getChildren().add(courseCard);
        }

        return grid;
    }

    private void startAssessment(String courseTitle) {
        Stage assessmentStage = new Stage();
        try {
            // Logic to open the corresponding assessment window
            switch (courseTitle) {
                case "Introduction to Data Protection" -> new Answer().start(assessmentStage);
                case "Rights of Data Subjects" -> new Answer().start(assessmentStage);
                case "Obligations Of Data Controllers and Data Processors" -> new Answer().start(assessmentStage);
                default -> System.out.println("No assessment available for this course.");
            }
        } catch (Exception e) {
            System.err.println("Error starting assessment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openloginForm() {
        // Create an instance of WelcomeForm
        LoginForm loginForm = new LoginForm();
        Stage loginStage = new Stage(); // Create a new Stage for WelcomeForm

        try {
            // Use the WelcomeForm's start() method to display it
            loginForm.start(loginStage);
        } catch (Exception e) {
            System.err.println("Error opening WelcomeForm: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
    // Method to return a description based on the course title
    private String getCourseDescription(String title) {
        return switch (title) {
            case "Introduction to Data Protection" ->
                    "Learn about safeguarding sensitive information, understanding encryption, access control, and data privacy best practices.";
            case "Rights of Data Subjects" ->
                    "Understand your rights under regulations like GDPR, including access, rectification, and erasure of personal information.";
            case "Obligations Of Data Controllers and Data Processors" ->
                    "Explore the legal responsibilities of organizations handling data and how compliance is achieved.";
            case "React vs Vue" ->
                    "A deep dive into the two most popular JavaScript frameworks and their use cases.";
            case "Best JavaScript Libraries" ->
                    "Discover the most widely used JavaScript libraries and how they accelerate web development.";
            case "Security in Web Apps" ->
                    "Understand web app vulnerabilities, risks, and how to secure them against threats like XSS and SQL injections.";
            default -> "Explore this course to enhance your knowledge.";
        };
    }

    // Method to open the corresponding course application based on the course title
    private void openCourseApplication(String courseTitle) {
        Stage newStage = new Stage();
        try {
            switch (courseTitle) {
                case "Introduction to Data Protection" -> new DataProtection().start(newStage); // Navigate to DataProtection
                case "Rights of Data Subjects" -> new Right_of_data_subject().start(newStage); // Navigate to Rights
                case "Obligations Of Data Controllers and Data Processors" -> new LifeskillDataProtection().start(newStage); // Navigate to LifeskillDataProtection
                default -> System.out.println("No application available for this course.");
            }

        } catch (Exception e) {
            System.err.println("Error opening course application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox createFooter() {
        VBox footer = new VBox();
        footer.setPadding(new Insets(20));
        footer.setSpacing(10);
        footer.setStyle("-fx-background-color: #0969c9;");

        HBox footerSections = new HBox();
        footerSections.setSpacing(20);

        VBox useCases = createFooterSection("Use Cases", new String[]{"UI Design", "UX Design", "Technology News"});
        VBox explore = createFooterSection("Explore", new String[]{"Cyber Security", "Software Development", "Artificial Intelligence"});
        VBox resources = createFooterSection("Resources", new String[]{"Courses", "Developers", "Support"});

        footerSections.getChildren().addAll(useCases, explore, resources);

        Label copyright = new Label("© " + java.time.Year.now() + " SIP GLOBAL. All rights reserved.");
        copyright.setTextFill(Color.WHITE);
        copyright.setAlignment(Pos.CENTER);

        footer.getChildren().addAll(footerSections, copyright);
        return footer;
    }

    private VBox createFooterSection(String title, String[] items) {
        VBox section = new VBox();
        section.setSpacing(5);

        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        sectionTitle.setTextFill(Color.WHITE);

        VBox itemList = new VBox();
        itemList.setSpacing(5);
        for (String item : items) {
            Label itemLabel = new Label(item);
            itemLabel.setTextFill(Color.WHITE);
            itemList.getChildren().add(itemLabel);
        }

        section.getChildren().addAll(sectionTitle, itemList);
        return section;
    }


    public static void main(String[] args) {
        launch(args);
    }
}