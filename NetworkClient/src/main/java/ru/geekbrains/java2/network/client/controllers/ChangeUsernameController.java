package ru.geekbrains.java2.network.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.geekbrains.java2.network.client.NetworkChatClient;
import ru.geekbrains.java2.network.client.models.Network;

import java.io.IOException;

public class ChangeUsernameController {
    @FXML
    public TextField newUsernameField;
    public Button applyNewUsernameButton;

    private static Network network;

    @FXML
    public void initialize(){
        applyNewUsernameButton.setOnAction(event->changeUsername());
    }
    public static void setNetwork(Network network) {
        ChangeUsernameController.network = network;
    }

    private void changeUsername() {
        try {
            String newUsername = newUsernameField.getText();
            if(!newUsername.isBlank()) {
                Stage stage = NetworkChatClient.changeNameDialogStage;
                stage.close();
                network.changeUsername(newUsername);
            }
        } catch (IOException e) {
            String errorMessage = "Failed to change username";
            NetworkChatClient.showNetworkError(e.getMessage(), errorMessage);
            e.printStackTrace();
        }
    }
}
