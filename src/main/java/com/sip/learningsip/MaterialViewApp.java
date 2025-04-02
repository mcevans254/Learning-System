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

public class MaterialViewApp extends Application {

    // Model class for Material
    public static class Material {
        private final int courseId;
        private final String pdf;
        private final String video;
        private final String courseName;

        public Material(int courseId, String pdf, String video, String courseName) {
            this.courseId = courseId;
            this.pdf = pdf;
            this.video = video;
            this.courseName = courseName;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getPdf() {
            return pdf;
        }

        public String getVideo() {
            return video;
        }

        public String getCourseName() {
            return courseName;
        }
    }

    @Override
    public void start(Stage stage) {
        TableView<Material> tableView = new TableView<>();

        // Define TableColumns
        TableColumn<Material, Integer> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<Material, String> pdfCol = new TableColumn<>("PDF");
        pdfCol.setCellValueFactory(new PropertyValueFactory<>("pdf"));

        TableColumn<Material, String> videoCol = new TableColumn<>("Video");
        videoCol.setCellValueFactory(new PropertyValueFactory<>("video"));

        TableColumn<Material, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        // Add columns to TableView
        tableView.getColumns().addAll(courseIdCol, pdfCol, videoCol, courseNameCol);

        // Fetch data and set it to the TableView
        ObservableList<Material> data = getDataFromMaterialView();
        if (data.isEmpty()) {
            showAlert("No Data Available", "There is no data to display in the table.");
        } else {
            tableView.setItems(data);
        }

        // Create the Scene
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Material View");
        stage.show();
    }

    // Method to fetch data from the material_view
    private ObservableList<Material> getDataFromMaterialView() {
        ObservableList<Material> materialList = FXCollections.observableArrayList();
        String query = "SELECT * FROM material_view"; // Query the view

        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String pdf = rs.getString("pdf");
                String video = rs.getString("video");
                String courseName = rs.getString("name");

                // Add the data to the list
                materialList.add(new Material(courseId, pdf, video, courseName));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching data from material_view: " + e.getMessage());
        }

        return materialList;
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Database connection helper
    private Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sip"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        launch();
    }
}