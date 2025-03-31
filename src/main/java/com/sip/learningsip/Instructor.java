package com.sip.learningsip;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Instructor extends Application {

    // Database connection details - Replace with your own credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sip";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private TableView<InstructorModel> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Instructor Information");

        // TableView layout
        table = new TableView<>();
        setupTable();

        // Fetch data and load into the table
        ObservableList<InstructorModel> instructorData = fetchInstructorData();
        table.setItems(instructorData);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(table);

        Scene scene = new Scene(layout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Set up the TableView columns
    private void setupTable() {
        TableColumn<InstructorModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setMinWidth(100);

        TableColumn<InstructorModel, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.setMinWidth(200);

        TableColumn<InstructorModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMinWidth(250);

        TableColumn<InstructorModel, Integer> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseIdColumn.setMinWidth(100);

        table.getColumns().addAll(idColumn, fullNameColumn, emailColumn, courseIdColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Fetch instructor data from the database
    private ObservableList<InstructorModel> fetchInstructorData() {
        ObservableList<InstructorModel> instructorList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT Id, FullName, email, courseId FROM instructor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String fullName = resultSet.getString("FullName");
                String email = resultSet.getString("email");
                int courseId = resultSet.getInt("courseId");

                instructorList.add(new InstructorModel(id, fullName, email, courseId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching instructor data: " + e.getMessage());
        }

        return instructorList;
    }
}