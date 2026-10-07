package com.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TextField nameField;
    private ComboBox<String> provinceBox;
    private TableView<Customer> customerTable;
    private ObservableList<Customer> customers;

    @Override
    public void start(Stage stage) {

        // =========================
        // TITLE
        // =========================

        Label title = new Label("CUSTOMER MANAGER");
        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );


        // =========================
        // NAME
        // =========================

        Label nameLabel = new Label("Customer Name:");

        nameField = new TextField();
        nameField.setPromptText("Enter customer name");


        // =========================
        // PROVINCE
        // =========================

        Label provinceLabel = new Label("Province:");

        provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Lusaka",
                "Copperbelt",
                "Central",
                "Eastern",
                "Luapula",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );

        provinceBox.setPromptText("Select province");

        provinceBox.setMaxWidth(Double.MAX_VALUE);


        // =========================
        // ADD BUTTON
        // =========================

        Button addButton = new Button("Add Customer");

        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setStyle(
                "-fx-font-weight: bold;"
        );

        addButton.setOnAction(event -> addCustomer());


        // =========================
        // TABLE
        // =========================

        customerTable = new TableView<>();

        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Customer Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        nameColumn.setPrefWidth(250);


        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");

        provinceColumn.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );

        provinceColumn.setPrefWidth(200);


        customerTable.getColumns().addAll(
                nameColumn,
                provinceColumn
        );


        // =========================
        // CUSTOMER LIST
        // =========================

        customers =
                FXCollections.observableArrayList();

        customerTable.setItems(customers);

        customerTable.setPrefHeight(250);


        // =========================
        // DELETE BUTTON
        // =========================

        Button deleteButton =
                new Button("Delete Selected");

        deleteButton.setMaxWidth(
                Double.MAX_VALUE
        );

        deleteButton.setOnAction(
                event -> deleteCustomer()
        );


        // =========================
        // LAYOUT
        // =========================

        VBox layout = new VBox(12);

        layout.setPadding(
                new Insets(25)
        );

        layout.getChildren().addAll(
                title,

                nameLabel,
                nameField,

                provinceLabel,
                provinceBox,

                addButton,

                customerTable,

                deleteButton
        );


        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(layout, 600, 600);


        stage.setTitle(
                "Customer Manager"
        );

        stage.setScene(scene);

        stage.show();


        // =========================
        // KEYBOARD ACCESS
        // =========================

        nameField.setOnAction(
                event -> addCustomer()
        );


        customerTable.setOnKeyPressed(event -> {

            if (event.getCode().toString()
                    .equals("DELETE")) {

                deleteCustomer();
            }
        });
    }


    // =================================================
    // ADD CUSTOMER
    // =================================================

    private void addCustomer() {

        String name =
                nameField.getText().trim();

        String province =
                provinceBox.getValue();


        if (name.isEmpty()) {

            showAlert(
                    "Input Error",
                    "Please enter the customer's name."
            );

            nameField.requestFocus();

            return;
        }


        if (province == null) {

            showAlert(
                    "Input Error",
                    "Please select a province."
            );

            provinceBox.requestFocus();

            return;
        }


        Customer customer =
                new Customer(
                        name,
                        province
                );


        customers.add(customer);


        nameField.clear();

        provinceBox.setValue(null);

        nameField.requestFocus();
    }


    // =================================================
    // DELETE CUSTOMER
    // =================================================

    private void deleteCustomer() {

        Customer selectedCustomer =
                customerTable
                        .getSelectionModel()
                        .getSelectedItem();


        if (selectedCustomer == null) {

            showAlert(
                    "Delete Customer",
                    "Please select a customer to delete."
            );

            return;
        }


        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );


        confirmation.setTitle(
                "Delete Customer"
        );

        confirmation.setHeaderText(
                "Delete selected customer?"
        );

        confirmation.setContentText(
                "Customer: "
                        + selectedCustomer.getName()
        );


        confirmation.showAndWait()
                .ifPresent(response -> {

                    if (response ==
                            ButtonType.OK) {

                        customers.remove(
                                selectedCustomer
                        );
                    }
                });
    }


    // =================================================
    // ALERT
    // =================================================

    private void showAlert(
            String title,
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }


    // =================================================
    // MAIN
    // =================================================

    public static void main(
            String[] args) {

        launch(args);
    }
}