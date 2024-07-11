package com.example.stephen_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class welcomeController {
    private Parent root;
    private Stage stage;

    @FXML
    private Button lower;
    @FXML
    private Button nursery;
    @FXML
    private Button upper;
    @FXML
    private Button admin;

    static String pupil_class = "";

    public void dataBtnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data.fxml"));
        root = loader.load();
        dataController controller = loader.getController();
        controller.collectData(pupil_class);
        //root = FXMLLoader.load(getClass().getResource("data.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void helpBtnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("help.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void upperPrimaryBtnAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("upper_option.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void lowerPrimaryBtnAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("lower_option.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void logOut(ActionEvent event) throws IOException {
        int prompt = JOptionPane.showConfirmDialog(null,"Are you sure you want to leave");
        if(prompt == 0){
            root = FXMLLoader.load(getClass().getResource("login_page_lower.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    public void collectData(String class_pupil){
        pupil_class = class_pupil;
        if(class_pupil.equalsIgnoreCase("P1")||class_pupil.equalsIgnoreCase("P2")||class_pupil.equalsIgnoreCase("P3")){
            upper.setDisable(true);
            nursery.setDisable(true);
            admin.setDisable(true);
        }
        else if(class_pupil.equalsIgnoreCase("P4")||class_pupil.equalsIgnoreCase("P5")||class_pupil.equalsIgnoreCase("P6")||class_pupil.equalsIgnoreCase("P7")){
            lower.setDisable(true);
            nursery.setDisable(true);
            admin.setDisable(true);
        }else if(class_pupil.equalsIgnoreCase("baby")||class_pupil.equalsIgnoreCase("middle")||class_pupil.equalsIgnoreCase("top")){
            upper.setDisable(true);
            lower.setDisable(true);
            admin.setDisable(true);
        }else{

        }
    }

    public void nurseryBtnAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("nursery.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void adminPage(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("admin_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void reportsBtnAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("report_options.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
