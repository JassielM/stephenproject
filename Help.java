package com.example.stephen_app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Help {
    private Parent root;
    private Stage stage;

    public void backHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        root = loader.load();
        String class_value = welcomeController.pupil_class;
        welcomeController welcomeController = loader.getController();
        welcomeController.collectData(class_value);
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void close(ActionEvent e){
        Platform.exit();
    }
}

