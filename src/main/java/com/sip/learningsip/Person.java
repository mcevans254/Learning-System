package com.sip.learningsip;

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
// Model class for the table rows
class PersonModel {
    private String person1;
    private String person2;
    private String person3;

    public PersonModel(String person1, String person2, String person3) {
        this.person1 = person1;
        this.person2 = person2;
        this.person3 = person3;
    }

    public String getPerson1() {
        return person1;
    }

    public String getPerson2() {
        return person2;
    }

    public String getPerson3() {
        return person3;
    }
}


   public class Person extends Application {

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("JavaFX Table Example");

            // Create a TableView with the PersonModel model
            TableView<PersonModel> tableView = new TableView<>();

            // Define columns for the table
            TableColumn<PersonModel, String> person1Column = new TableColumn<>("PersonModel 1");
            person1Column.setCellValueFactory(new PropertyValueFactory<>("person1")); // Match the model property name

            TableColumn<PersonModel, String> person2Column = new TableColumn<>("PersonModel 2");
            person2Column.setCellValueFactory(new PropertyValueFactory<>("person2"));

            TableColumn<PersonModel, String> person3Column = new TableColumn<>("PersonModel 3");
            person3Column.setCellValueFactory(new PropertyValueFactory<>("person3"));

            // Add the columns to the TableView
            tableView.getColumns().addAll(person1Column, person2Column, person3Column);

            // Add sample data to the table
            ObservableList<PersonModel> data = FXCollections.observableArrayList(
                    new PersonModel("Emil", "Tobias", "Linus"), // First row
                    new PersonModel("16", "14", "10")           // Second row
            );
            tableView.setItems(data);

            // Set column width policy
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Define the layout
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(20, 20, 20, 20));
            vbox.getChildren().add(tableView);

            // Create the scene and set the stage
            Scene scene = new Scene(vbox, 600, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {

        }
    }
