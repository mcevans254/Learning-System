package com.sip.learningsip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class LifeskillDataProtection extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Life Skills for Data Protection");
        Button openModalButton = new Button("Learn Life Skills for Data Protection");
        openModalButton.setOnAction(e -> openModal(primaryStage));

        VBox mainLayout = new VBox(openModalButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openModal(Stage ownerStage) {
        Stage modalStage = new Stage();
        modalStage.initOwner(ownerStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Life Skills for Data Protection");

        // Modal Header
        HBox header = new HBox();
        header.setPadding(new Insets(16, 32, 16, 32));
        header.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0; -fx-background-color: #f5f5f5;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label headerTitle = new Label("Life Skills for Data Protection");
        headerTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> modalStage.close());
        closeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 14px; -fx-cursor: hand;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(headerTitle, spacer, closeButton);

        // Modal Body with Scrollable Content
        ScrollPane bodyScroll = new ScrollPane();
        bodyScroll.setPadding(new Insets(16, 32, 16, 32));
        bodyScroll.setStyle("-fx-background-color: white;");
        bodyScroll.setFitToWidth(true);

        VBox bodyContent = new VBox(10);
        bodyContent.setPadding(new Insets(0, 0, 32, 0));
        bodyContent.setStyle("-fx-font-size: 14px; -fx-line-spacing: 0.2em;");

        // Adding extensive content
        bodyContent.getChildren().addAll(
                createHeading("Introduction to Life Skills for Data Protection"),
                createParagraph(
                        "In today's digital age, life skills for data protection are essential for safeguarding sensitive information "
                                + "in both personal and professional settings. These skills enable individuals to identify risks, implement practical solutions, "
                                + "and respond effectively to data-related threats."
                ),

                createHeading("1. Understanding Digital Privacy"),
                createParagraph(
                        "Digital privacy encompasses the right to control how your personal data is collected, used, and shared online. "
                                + "Awareness of digital privacy empowers individuals to understand the value of their data and protect it effectively.\n\n"
                                + "**Key Skills:**\n"
                                + "• Recognizing what personal data is being shared online.\n"
                                + "• Understanding privacy policies and terms of service."
                ),

                createHeading("2. Safe Online Practices"),
                createParagraph(
                        "Adopting safe online practices significantly reduces the risk of data breaches and cyberattacks. Some essential habits include:\n\n"
                                + "• Using **strong, unique passwords** and password managers.\n"
                                + "• Enabling **two-factor authentication (2FA)** for important accounts.\n"
                                + "• Avoiding suspicious emails, links, or attachments to prevent phishing scams.\n"
                                + "• Regularly updating browsers, operating systems, and software to patch vulnerabilities."
                ),

                createHeading("3. Recognizing Threats"),
                createParagraph(
                        "An essential life skill is the ability to identify potential cyber threats. Individuals should be aware of: \n\n"
                                + "• **Phishing:** Fraudulent attempts to steal credentials through fake emails or websites.\n"
                                + "• **Social Engineering:** Manipulative techniques used to extract confidential information.\n"
                                + "• **Ransomware:** Malware that locks your files until a ransom is paid."
                ),

                createHeading("4. Managing Social Media Privacy"),
                createParagraph(
                        "Social media platforms often collect a significant amount of personal information. Here are some critical privacy management skills:\n\n"
                                + "• Reviewing and adjusting **privacy settings** on social media accounts frequently.\n"
                                + "• Avoiding oversharing sensitive details like your address or travel plans.\n"
                                + "• Using **private accounts** or limiting access to posts to trusted individuals only."
                ),

                createHeading("5. Practical Data Backup"),
                createParagraph(
                        "Regularly backing up important data is a critical skill for protecting against accidental loss, ransomware, or breaches.\n\n"
                                + "**Steps to Back Up Data Effectively:**\n\n"
                                + "• Use cloud storage solutions like Google Drive, OneDrive, or secure local storage.\n"
                                + "• Establish automated backups for critical systems.\n"
                                + "• Verify backups by restoring data occasionally to ensure they are accessible."
                ),

                createHeading("6. Emotional and Behavioral Resilience"),
                createParagraph(
                        "Data protection isn't just about technical skills—it also involves emotional and behavioral control to respond effectively to threats:\n\n"
                                + "• Staying calm and rational when encountering suspicious activities.\n"
                                + "• Consulting experts or cybersecurity teams for guidance when necessary.\n"
                                + "• Avoiding impulsive decisions, like paying a ransom or opening suspicious links."
                )
        );

        bodyScroll.setContent(bodyContent);

        // Modal Footer with Download PDF and Play Video Buttons
        HBox footer = new HBox();
        footer.setPadding(new Insets(16, 32, 16, 32));
        footer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1 0 0 0; -fx-background-color: #f5f5f5;");
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setSpacing(10);

        Button downloadPdfButton = new Button("Download PDF");
        downloadPdfButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");
        downloadPdfButton.setOnAction(e -> downloadPdf());

        Button playVideoButton = new Button("Play Video");
        playVideoButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        playVideoButton.setOnAction(e -> playVideo());

        Button backToWelcomeButton = new Button("Back to Welcome");
        backToWelcomeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        backToWelcomeButton.setOnAction(e -> {
            modalStage.close(); // Close the current modal
            openWelcomeForm();  // Open the WelcomeForm
        });

        footer.getChildren().addAll(downloadPdfButton, playVideoButton, backToWelcomeButton);

        // Modal Layout
        BorderPane modalLayout = new BorderPane();
        modalLayout.setTop(header);
        modalLayout.setCenter(bodyScroll);
        modalLayout.setBottom(footer);

        // Modal Scene
        Scene modalScene = new Scene(modalLayout, 800, 600);
        modalStage.setScene(modalScene);
        modalStage.show();
    }

    // Create Heading Component
    private Label createHeading(String text) {
        Label heading = new Label(text);
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return heading;
    }

    // Create Paragraph Component
    private Label createParagraph(String text) {
        Label paragraph = new Label(text);
        paragraph.setWrapText(true);
        return paragraph;
    }

    // Download PDF Method
    private void downloadPdf() {
        String pdfPath = "material/lifeskills for dataprotection.pdf"; // Path to the PDF file
        File pdfFile = new File(pdfPath);

        if (pdfFile.exists()) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                    System.out.println("PDF successfully opened!");
                } else {
                    System.out.println("Desktop not supported for file opening.");
                }
            } catch (IOException e) {
                System.out.println("Error opening PDF file: " + e.getMessage());
            }
        } else {
            System.out.println("PDF file not found at path: " + pdfFile.getAbsolutePath());
        }
    }

    private void openWelcomeForm() {
        // Create an instance of WelcomeForm
        WelcomeForm welcomeForm = new WelcomeForm();
        Stage welcomeStage = new Stage(); // Create a new Stage for WelcomeForm

        try {
            // Use the WelcomeForm's start() method to display it
            welcomeForm.start(welcomeStage);
        } catch (Exception e) {
            System.err.println("Error opening WelcomeForm: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    // Play Video Method
    private void playVideo() {
        String videoPath = "videos/dataprotection.mp4"; // Path to the video file
        File videoFile = new File(videoPath);

        if (videoFile.exists()) {
            try {
                Media video = new Media(videoFile.toURI().toString());
                MediaPlayer player = new MediaPlayer(video);
                MediaView mediaView = new MediaView(player);

                Stage videoStage = new Stage();
                videoStage.setTitle("Play Video - Life Skills for Data Protection");

                BorderPane videoLayout = new BorderPane(mediaView);
                Scene videoScene = new Scene(videoLayout, 800, 450);

                videoStage.setScene(videoScene);
                videoStage.show();

                player.play();
            } catch (Exception e) {
                System.out.println("Error playing video: " + e.getMessage());
            }
        } else {
            System.out.println("Video file not found: " + videoFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}