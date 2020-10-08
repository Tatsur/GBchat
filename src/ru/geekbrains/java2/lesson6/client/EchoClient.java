package ru.geekbrains.java2.lesson6.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;


public class EchoClient extends Application {

    public static final List<String> USERS_TEST_DATA = List.of("Oleg", "Alexey", "Peter");


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EchoClient.class.getResource("view.fxml"));

        Parent root = loader.load();

        primaryStage.setTitle("Messenger");
        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.show();

        Network network = new Network();
        if (!network.connect()) {
            showNetworkError("", "Failed to connect to server");
        }

        ViewController viewController = loader.getController();
        viewController.setNetwork(network);

        network.waitMessages(viewController);

        primaryStage.setOnCloseRequest(event -> {
            network.close();
        });
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}