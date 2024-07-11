package com.example.stephen_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lower_UpdateOptions {
    @FXML
    private Parent root;
    @FXML
    private Stage stage;

    public void beginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("bot_updateLower.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void mid_term(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("mid_updateLower.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void end_of_term(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("end_updateLower.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void back(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data.fxml"));
        root = loader.load();
        dataController controller = loader.getController();
        controller.collectData(welcomeController.pupil_class);
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
