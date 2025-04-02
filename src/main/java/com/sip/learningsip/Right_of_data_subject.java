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

public class Right_of_data_subject extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cybersecurity Learning Application");
        Button showModalButton = new Button("Learn About Data Subject Rights");
        showModalButton.setOnAction(e -> showModal(primaryStage));

        VBox mainLayout = new VBox(showModalButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showModal(Stage ownerStage) {
        Stage modalStage = new Stage();
        modalStage.initOwner(ownerStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Rights of Data Subjects");

        // Modal Header
        HBox header = new HBox();
        header.setPadding(new Insets(16, 32, 16, 32));
        header.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0; -fx-background-color: #f5f5f5;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label headerTitle = new Label("Data Subject Rights");
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
        bodyScroll.setStyle("-fx-background-color: white;");
        bodyScroll.setFitToWidth(true);

        VBox bodyContent = new VBox(10);
        bodyContent.setPadding(new Insets(0, 0, 32, 0));
        bodyContent.setStyle("-fx-font-size: 14px; -fx-line-spacing: 0.2em;");

        // Full enriched content from the initial requirements
        bodyContent.getChildren().addAll(
                createHeading("What Are Data Subject Rights?"),
                createParagraph(
                        "Data Subject Rights are fundamental principles granted under data protection regulations like the General Data Protection Regulation (GDPR), "
                                + "enabling individuals (data subjects) to understand and control how their personal data is collected, processed, shared, and stored. "
                                + "These rights are anchored in promoting transparency, accountability, and fairness in data processing activities conducted by organizations, "
                                + "ensuring better protection of privacy and personal freedoms.\n\n"
                                + "**Why These Rights Matter:**\n"
                                + "As organizations increasingly rely on collecting and processing personal data to drive innovation, personalization, and analytics, "
                                + "there is a heightened risk of data misuse, privacy violations, and ethical concerns. Data Subject Rights provide a legal mechanism "
                                + "for individuals to stay informed and exercise control over their digital identities."
                ),

                createHeading("The 8 Core Data Subject Rights Under GDPR"),
                createParagraph(
                        "GDPR establishes eight core rights for data subjects to ensure their personal data is treated with care and respect. Below is a detailed discussion "
                                + "of these rights, their legal basis, and their practical applications."
                ),

                createHeading("1. The Right of Access (Article 15)"),
                createParagraph(
                        "The Right of Access ensures transparency in data processing by allowing individuals to access their personal data and verify how it is being used. "
                                + "This right forms the foundation for other GDPR rights because access to data gives individuals insights into the organization's activities.\n\n"
                                + "**Key Entitlements:**\n"
                                + "• Individuals can confirm whether their personal data is being processed.\n"
                                + "• They can request a copy of their data to review its content and format.\n"
                                + "• They can demand additional information such as:\n"
                                + "  - The purpose of processing their data.\n"
                                + "  - Categories of data involved (e.g., contact details, browsing activity).\n"
                                + "  - Recipients (external or internal) to whom the data has been disclosed.\n\n"
                                + "**Example:** A user requests a copy of their personal data from an online retail platform to understand how their browsing and purchase history "
                                + "is being used for marketing or personalization purposes. This request enables them to validate compliance with GDPR and demand corrections if discrepancies are found."
                ),

                createHeading("2. The Right to Rectification (Article 16)"),
                createParagraph(
                        "This right allows individuals to correct any inaccuracies in their personal data or complete incomplete information. Accurate data is critical for ensuring "
                                + "fair outcomes in decision-making processes.\n\n"
                                + "**Relevance in Real Life:**\n"
                                + "- A healthcare provider might use inaccurate medical records to make critical treatment decisions.\n"
                                + "- A bank relying on outdated address details could deliver sensitive documents to the wrong recipient.\n\n"
                                + "**Example:** A customer notices incorrect employment details mentioned in their credit report from a financial service provider. Under GDPR, they can "
                                + "demand that the organization promptly correct such misinformation."
                ),

                createHeading("3. The Right to Erasure (‘Right to Be Forgotten’) (Article 17)"),
                createParagraph(
                        "The Right to Erasure empowers individuals to request the deletion of their personal data under specific conditions. This right balances the individual's "
                                + "privacy against the organization's need to retain data for legal or operational purposes. Circumstances include:\n\n"
                                + "• Personal data no longer serves its original purpose.\n"
                                + "• Consent to data usage has been withdrawn (if consent was the processing basis).\n"
                                + "• The organization processes the data unlawfully (e.g., without meeting legal obligations).\n\n"
                                + "**Exceptions to Erasure Requests:**\n"
                                + "• Where data is vital for compliance with legal obligations.\n"
                                + "• Where public interest or public health considerations exist.\n"
                                + "• Where the organization needs data for archival or research purposes.\n\n"
                                + "**Real-World Example:** A former social media user requests the complete deletion of their account, which includes posts, images, comments, and browsing interactions."
                ),

                createHeading("4. The Right to Restrict Processing (Article 18)"),
                createParagraph(
                        "The Right to Restrict Processing offers individuals the ability to temporarily limit how their personal data is used. This is particularly significant during disputes "
                                + "or reviews involving the data's accuracy or legality.\n\n"
                                + "**Situations to Restrict Processing:**\n"
                                + "• When an individual challenges the accuracy of their personal data.\n"
                                + "• When the processing is unlawful but the individual prefers restriction over erasure.\n"
                                + "• When data stored is no longer needed by the organization, but the individual requires it for legal claims.\n\n"
                                + "**Practical Implications:** Companies must clearly label data under restriction and refrain from processing it except for storage, legal purposes, or explicit user consent."
                ),

                createHeading("5. The Right to Data Portability (Article 20)"),
                createParagraph(
                        "This right simplifies transferring personal data from one organization to another, enabling seamless transitions between service providers. "
                                + "Data should be provided in a machine-readable format and must not affect other users' data while being ported.\n\n"
                                + "**Key Use Case:** A telecom user moving data (e.g., call records, personal details) from one provider to another without loss of information, enhancing customer freedom."
                ),

                createHeading("6. The Right to Object (Article 21)"),
                createParagraph(
                        "This right allows individuals to object to data processing based on legitimate interests, public tasks, or direct marketing. Organizations "
                                + "must cease these activities unless they can validate a legal basis exceeding the individual’s interests.\n\n"
                                + "**Marketing Example:** If a user no longer wishes to receive promotional emails or targeted ads, they can object to the processing of marketing data, and companies "
                                + "are required to immediately honor this request."
                ),

                createHeading("7. Rights Related to Automated Decision-Making (Article 22)"),
                createParagraph(
                        "With increased reliance on AI and automated systems, individuals are entitled to safeguards against decisions made solely by automated means, such as loan approvals, hiring, "
                                + "or insurance claims.\n\n"
                                + "**Case Study Example:** An automated credit scoring system denies a credit application. The user can invoke this right to seek an explanation for the algorithm's decision-making logic or request human intervention."
                ),

                createHeading("8. The Right to Be Informed (Article 12, 13, and 14)"),
                createParagraph(
                        "Transparency is at the heart of data protection laws. Organizations must clearly outline how and why personal data is being collected and used. Privacy notices "
                                + "must be concise, accessible, and written in plain language to ensure individuals fully understand their rights and organizational responsibilities.\n\n"
                                + "**Example:** A streaming platform informs users about the types of viewing data it collects to recommend movies, reassuring users about compliance and fostering trust."
                )
        );
        bodyScroll.setContent(bodyContent);

        // Modal Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(16, 32, 16, 32));
        footer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1 0 0 0; -fx-background-color: #f5f5f5;");
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setSpacing(10);

        // "Download PDF" Button
        Button downloadPdfButton = new Button("Download PDF");
        downloadPdfButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");
        downloadPdfButton.setOnAction(e -> downloadPdf());

        // "Play Video" Button
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

    // Utility method to create a heading
    private Label createHeading(String text) {
        Label heading = new Label(text);
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return heading;
    }

    // Utility method to create a paragraph
    private Label createParagraph(String text) {
        Label paragraph = new Label(text);
        paragraph.setWrapText(true);
        return paragraph;
    }

    // Method for handling PDF download
    private void downloadPdf() {
        String pdfPath = "material/rights of data subject.pdf"; // Update file location appropriately
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
                System.out.println("Error opening the PDF file: " + e.getMessage());
            }
        } else {
            System.out.println("PDF file not found at: " + pdfFile.getAbsolutePath());
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

    // Method for playing a video
    private void playVideo() {
        String videoPath = "videos/rights of data subject.mp4"; // Update file location appropriately
        File videoFile = new File(videoPath);

        if (videoFile.exists()) {
            try {
                Media video = new Media(videoFile.toURI().toString());
                MediaPlayer player = new MediaPlayer(video);
                MediaView view = new MediaView(player);

                Stage videoStage = new Stage();
                videoStage.setTitle("Play Video: Data Subject Rights");

                BorderPane videoLayout = new BorderPane(view);
                Scene videoScene = new Scene(videoLayout, 800, 450);

                videoStage.setScene(videoScene);
                videoStage.show();

                player.play();

            } catch (Exception e) {
                System.out.println("Unable to play video: " + e.getMessage());
            }
        } else {
            System.out.println("Video file not found!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}