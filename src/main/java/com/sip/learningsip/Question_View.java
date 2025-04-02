package com.sip.learningsip;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Question_View extends Application {

    // Model class for Question
    public static class Question {
        private final int id;
        private final String questionText;
        private final int courseId;
        private final String courseName;

        public Question(int id, String questionText, int courseId, String courseName) {
            this.id = id;
            this.questionText = questionText;
            this.courseId = courseId;
            this.courseName = courseName;
        }

        public int getId() {
            return id;
        }

        public String getQuestionText() {
            return questionText;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }
    }

    @Override
    public void start(Stage stage) {
        TableView<Question> tableView = new TableView<>();

        // Define TableColumns
        TableColumn<Question, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Question, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("questionText"));

        TableColumn<Question, Integer> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<Question, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        // Add columns to TableView
        tableView.getColumns().addAll(idColumn, questionColumn, courseIdColumn, courseNameColumn);

        // Fetch data and set it to the TableView
        ObservableList<Question> data = getDataFromDatabase();

        // Check if no data is fetched
        if (data.isEmpty()) {
            showAlert("No Data Available", "There is no data to display in the table.");
        } else {
            tableView.setItems(data);
        }

        // Create the Scene
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Question View");
        stage.show();
    }

    // Method to fetch data from the question_view
    public ObservableList<Question> getDataFromDatabase() {
        ObservableList<Question> questionList = FXCollections.observableArrayList();
        String query = "SELECT * FROM question_view"; // Fetch directly from the view

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question_text");
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("name");

                questionList.add(new Question(id, questionText, courseId, courseName));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging purposes
        }

        return questionList;
    }

    // Method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility class for connecting to the database
    public static class DatabaseUtil {
        private static final String URL = "jdbc:mysql://localhost:3306/sip"; // Update with your database URL
        private static final String USER = "root"; // Replace with your MySQL username
        private static final String PASSWORD = ""; // Replace with your MySQL password

        public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}