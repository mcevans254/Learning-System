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

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DataProtection extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main window to launch the modal
        primaryStage.setTitle("Cybersecurity Learning Application");
        Button showModalButton = new Button("Learn About Data Protection");
        showModalButton.setOnAction(e -> showModal(primaryStage));

        VBox mainLayout = new VBox(showModalButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create and show modal with cybersecurity and data protection content
    private void showModal(Stage ownerStage) {
        // Modal Stage
        Stage modalStage = new Stage();
        modalStage.initOwner(ownerStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Learn About Data Protection");

        // Modal Header
        HBox header = new HBox();
        header.setPadding(new Insets(16, 32, 16, 32));
        header.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0; -fx-background-color: #f5f5f5;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label headerTitle = new Label("Data Protection and Cybersecurity Basics");
        headerTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> modalStage.close());
        closeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 14px; -fx-cursor: hand;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(headerTitle, spacer, closeButton);

        // Modal Body (Scrollable content)
        ScrollPane bodyScroll = new ScrollPane();
        bodyScroll.setPadding(new Insets(16, 32, 16, 32));
        bodyScroll.setStyle("-fx-background-color: white");
        bodyScroll.setFitToWidth(true);

        VBox bodyContent = new VBox(10);
        bodyContent.setPadding(new Insets(0, 0, 32, 0));
        bodyContent.setStyle("-fx-font-size: 14px; -fx-line-spacing: 0.2em;");

        // Content Sections - Detailed explanation of data protection topics
        bodyContent.getChildren().addAll(
                createHeading("What is Data Protection?"),
                createParagraph(
                        "Data protection refers to practices and methodologies that safeguard personal and sensitive information "
                                + "against unauthorized access, corruption, loss, or theft. These measures are essential in maintaining "
                                + "privacy, securing digital assets, and ensuring that the rights of individuals are protected under privacy laws."
                ),

                createHeading("1. Importance of Data Protection"),
                createParagraph(
                        "Data protection preserves the integrity, confidentiality, and availability of data. It is critically important in "
                                + "today’s world due to the following reasons:\n\n"
                                + "• Preventing **identity theft** and scams.\n"
                                + "• Securing sensitive organizational data, including trade secrets.\n"
                                + "• Complying with international laws such as GDPR, HIPAA, and CCPA.\n"
                                + "• Boosting consumer trust by demonstrating a commitment to privacy protection."
                ),

                createHeading("2. Key Principles of Data Protection"),
                createParagraph(
                        "Data protection is governed by core principles outlined in privacy laws like the **General Data Protection Regulation (GDPR)**: \n\n"
                                + "• **Lawfulness, Fairness, and Transparency**: Data must be collected and processed lawfully and fairly.\n"
                                + "• **Purpose Limitation**: Personal data must only be collected for specific, legitimate purposes.\n"
                                + "• **Data Minimization**: Collect only the data necessary for the purpose.\n"
                                + "• **Accuracy**: Keep data accurate and up to date.\n"
                                + "• **Storage Limitation**: Retain data only as long as necessary.\n"
                                + "• **Integrity and Confidentiality**: Protect data against unauthorized access and breaches."
                ),

                createHeading("3. Common Data Protection Techniques"),
                createParagraph(
                        "There are several key techniques used to protect data effectively. Every cybersecurity professional should be familiar with these practices:\n\n"
                                + "• **Encryption**: Ensures data is only readable to authorized parties by encoding it.\n"
                                + "• **Access Control**: Restricts data access based on roles and permissions.\n"
                                + "• **Data Masking**: Obscures sensitive data until needed by authorized users.\n"
                                + "• **Backup Strategies**: Regularly backing up data to prevent loss during disasters or breaches.\n"
                                + "• **Data Redaction**: Hides or removes confidential information from documents."
                ),

                createHeading("4. Types of Cyberattacks Targeting Data"),
                createParagraph(
                        "Cybercriminals often target sensitive data through various methods. Here are the most common types of cyberattacks:\n\n"
                                + "• **Phishing**: Fraudulent emails or websites trick individuals into disclosing sensitive information.\n"
                                + "• **Ransomware**: Malicious software encrypts data and demands payment to unlock it.\n"
                                + "• **Man-in-the-Middle (MITM) Attacks**: Intercepting communications to steal data during transfer.\n"
                                + "• **SQL Injection**: Exploiting database vulnerabilities to access sensitive information.\n"
                                + "• **Malware**: Programs like viruses and spyware designed to steal, corrupt, or delete data."
                ),

                createHeading("5. Your Role in Data Protection"),
                createParagraph(
                        "Each individual or employee plays a vital role in ensuring data protection. Here are simple yet effective steps:\n\n"
                                + "• Use **strong passwords** and enable multi-factor authentication (MFA).\n"
                                + "• Avoid clicking on unknown links or downloading suspicious files.\n"
                                + "• Recognize and report suspicious emails or activities immediately.\n"
                                + "• Regularly update software and patches on all devices.\n"
                                + "• Treat sensitive information, such as passwords, bank details, or personal identifiers, with strict confidentiality."
                ),

                createHeading("6. Global Data Protection Laws"),
                createParagraph(
                        "Data protection is reinforced internationally by various laws and regulations. Understanding these will help you stay compliant:\n\n"
                                + "• **GDPR (General Data Protection Regulation)**: Protects the privacy of individuals in the European Union.\n"
                                + "• **HIPAA (Health Insurance Portability and Accountability Act)**: Protects medical records in the United States.\n"
                                + "• **CCPA (California Consumer Privacy Act)**: Enhances privacy rights for residents of California.\n"
                                + "• **PIPEDA (Personal Information Protection and Electronic Documents Act)**: Governs data in Canada.\n\n"
                ),
                createHeading("\tAdvanced Data Protection Safeguards"),
                createParagraph(
                        "Data protection safeguards play a critical role in maintaining digital privacy, trust, and security across personal and professional platforms. "
                                + "With the growing complexity of cyber threats, these safeguards must be robust, scalable, and aligned with modern challenges faced by organizations and individuals. "
                                + "Effective data protection strategies ensure compliance with international privacy regulations while reducing risks of breaches and misconduct.\n\n"
                                + "Below are some advanced safeguards designed to enhance the confidentiality, availability, and integrity of sensitive data:"
                ),

                createHeading("1. End-to-End Encryption (E2EE)"),
                createParagraph(
                        "Encryption remains one of the most effective safeguards for preventing unauthorized access to sensitive data during storage or transmission. "
                                + "End-to-End Encryption ensures that only the sender and recipient can decrypt communication, as the information is encrypted before it leaves the sender’s system.\n\n"
                                + "**Use Cases:**\n"
                                + "• Protecting sensitive emails, messaging platforms, and financial transactions.\n"
                                + "• Encrypting medical records in compliance with HIPAA.\n"
                                + "• Securing payment processing systems (e.g., PCI DSS compliance for financial data).\n\n"
                                + "**Real-World Example:** Messaging applications like WhatsApp use End-To-End Encryption to safeguard sensitive conversations from interception or tampering."
                ),

                createHeading("2. Zero Trust Architecture"),
                createParagraph(
                        "Zero Trust is a modern cybersecurity model designed to assume that no user or device—whether internal or external—can be trusted by default. "
                                + "Instead, access must be explicitly verified before being granted, leveraging advanced authentication and granular control.\n\n"
                                + "**Features of Zero Trust:**\n"
                                + "• Multi-Factor Authentication (MFA) across all access points.\n"
                                + "• Micro-segmentation techniques that separate and isolate sensitive data assets.\n"
                                + "• Continuous monitoring of user behavior to detect anomalies in real time.\n\n"
                                + "**Practical Application:** Organizations implement Zero Trust to protect cloud-based systems, especially when employees access resources remotely through unmanaged devices."
                ),

                createHeading("3. Secure Access and Identity Management (IAM)"),
                createParagraph(
                        "Identity and Access Management (IAM) encompasses policies, technologies, and processes used to authenticate and authorize users in accessing sensitive data or systems. "
                                + "IAM ensures that users have the right access levels without compromising data protection measures, minimizing the likelihood of insider threats.\n\n"
                                + "**Key Components:**\n"
                                + "• Role-Based Access Control (RBAC): Users only access the data necessary for their roles.\n"
                                + "• Single Sign-On (SSO): Reduces misuse of passwords by simplifying login processes.\n"
                                + "• Biometric Verification: Provides advanced, secure authentication for critical workflows (e.g., facial recognition, fingerprint scanning).\n\n"
                                + "**Practical Use Case:** A healthcare organization uses IAM to limit patient data access only to authorized doctors and nurses, ensuring compliance with data privacy laws."
                ),

                createHeading("4. Anomaly Detection and Threat Intelligence"),
                createParagraph(
                        "Anomaly detection systems monitor user behavior and detect unusual patterns that may indicate a cyberattack, unauthorized access, or insider threats. "
                                + "These systems integrate with **Artificial Intelligence (AI)** and **Threat Intelligence Platforms** to assess security incidents in real time.\n\n"
                                + "**Key Features:**\n"
                                + "• Automatically detect suspicious traffic or login attempts.\n"
                                + "• Integrate with SIEM (Security Information and Event Management) tools.\n"
                                + "• Provide predictive analytics to identify future threats and address vulnerabilities proactively.\n\n"
                                + "**Example:** A financial institution uses anomaly detection to flag and block unusual large international transactions from client accounts."
                ),

                createHeading("5. Data Loss Prevention (DLP) Mechanisms"),
                createParagraph(
                        "Data Loss Prevention tools help monitor, detect, and prevent unauthorized attempts to access or transfer sensitive data outside an organization. "
                                + "DLP techniques protect against accidental data breaches caused by employee negligence, phishing, or malware attacks.\n\n"
                                + "**Core Strategies in DLP:**\n"
                                + "• Monitoring and restricting file transfers through USB drives and email attachments.\n"
                                + "• Identifying sensitive data within files (e.g., Social Security Numbers, credit card information).\n"
                                + "• Educating employees through policies and ongoing training about proper data handling.\n\n"
                                + "**Example:** A corporate DLP system prevents employees from uploading files containing sensitive intellectual property to external cloud storage."
                ),

                createHeading("6. Regular Security Assessments and Testing"),
                createParagraph(
                        "Routine testing helps assess the effectiveness of organizational data protection measures and identify potential vulnerabilities in systems or processes. "
                                + "Frequent audits ensure that an organization's cybersecurity defenses are up-to-date and in line with evolving threat landscapes.\n\n"
                                + "**Types of Testing:**\n"
                                + "• **Penetration Testing (Pen Testing):** Simulates real-world attacks to identify security weaknesses.\n"
                                + "• **Vulnerability Scanning:** Detects potential entry points for unauthorized access.\n"
                                + "• **Employee Phishing Simulations:** Tests employee awareness on phishing tactics to measure risk.\n\n"
                                + "**Implementation:** Organizations conduct penetration testing at least twice a year to evaluate the security of deployed services and networks."
                ),

                createHeading("7. Privacy-Enhancing Technologies (PETs)"),
                createParagraph(
                        "Privacy-enhancing technologies provide innovative solutions that protect personal data at every stage of its lifecycle. They ensure compliance with "
                                + "GDPR’s ‘privacy by design’ principle while enabling organizations to conduct necessary analytics without risking privacy violations.\n\n"
                                + "**Examples of PETs:**\n"
                                + "• **Homomorphic Encryption:** Allows computations to be performed on encrypted data without exposing the underlying plaintext.\n"
                                + "• **Data Anonymization:** Removes identifiable information from datasets to maintain privacy in analytics.\n"
                                + "• **Differential Privacy:** Minimizes the impact of individual records in datasets, ensuring insights can be derived while protecting anonymity.\n\n"
                                + "**Industrial Application:** Research institutions use differential privacy to study healthcare trends without exposing patient details."
                ),

                createHeading("8. Security Policies and Training Programs"),
                createParagraph(
                        "Even the most powerful technology solutions can fail without well-defined security policies and trained personnel. Organizations must educate both employees "
                                + "and users on how to safely handle sensitive information and respond to potential cybersecurity incidents.\n\n"
                                + "**Components of Effective Training:**\n"
                                + "• Cyber hygiene practices (e.g., strong password policies and MFA adoption).\n"
                                + "• Recognizing and reporting phishing attempts.\n"
                                + "• Understanding incident response procedures (e.g., immediately reporting security incidents).\n\n"
                                + "**Example:** A critical organization conducts monthly workshops covering cybersecurity trends and internal reminders to educate employees on identifying phishing schemes."
                )
        );



        bodyScroll.setContent(bodyContent);


        // Modal Footer with Download PDF and Play Video Buttons
        HBox footer = new HBox();
        footer.setPadding(new Insets(16, 32, 16, 32));
        footer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1 0 0 0; -fx-background-color: #f5f5f5;");
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setSpacing(10);

        Button backToWelcomeButton = new Button("Back to Welcome");
        backToWelcomeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        backToWelcomeButton.setOnAction(e -> {
            modalStage.close(); // Close the current modal
            openWelcomeForm();  // Open the WelcomeForm
        });


        Button downloadPdfButton = new Button("Download PDF");
        downloadPdfButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");
        downloadPdfButton.setOnAction(e -> downloadPdf());

        Button playVideoButton = new Button("Play Video");
        playVideoButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        playVideoButton.setOnAction(e -> playVideo());

        footer.getChildren().addAll(downloadPdfButton, playVideoButton, backToWelcomeButton
        );

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
        String pdfPath = "material/dataprotection and cyber security.pdf"; // Path to the PDF file
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
    //back to welcomeform
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
        String videoPath = "videos/cybersecurity.mp4"; // Path to the video file
        File videoFile = new File(videoPath);

        if (videoFile.exists()) {
            try {
                Media video = new Media(videoFile.toURI().toString());
                MediaPlayer player = new MediaPlayer(video);
                MediaView mediaView = new MediaView(player);

                Stage videoStage = new Stage();
                videoStage.setTitle("Play Video - Data Protection");

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