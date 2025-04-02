package com.sip.learningsip;

import com.sip.learningsip.models.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;




public class UserTableApp extends Application {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sip"; // Replace with your DB name
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = ""; // Replace with your password

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Users Table");

        // Create a TableView
        TableView<User> tableView = new TableView<>();

        // Define columns for TableView
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> firstnameColumn = new TableColumn<>("Firstname");
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        TableColumn<User, String> lastnameColumn = new TableColumn<>("Lastname");
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add columns to TableView
        tableView.getColumns().addAll(idColumn, usernameColumn, emailColumn, firstnameColumn, lastnameColumn, roleColumn);

        // Fetch data from the database and set it to the TableView
        tableView.setItems(fetchUserData());

        // Layout
        VBox layout = new VBox(tableView);
        layout.setPadding(new Insets(20));

        // Scene and Stage setup
        Scene scene = new Scene(layout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Fetch data from the users table
    private ObservableList<User> fetchUserData() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id, username, email, firstname, lastname, role FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String role = resultSet.getString("role");



            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching user data: " + e.getMessage());
        }

        return userList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}