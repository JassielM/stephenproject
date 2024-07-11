package com.example.stephen_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class dataController {
    private Parent root;
    private Stage stage;

    @FXML
    private Button lower;
    @FXML
    private Button nursery;
    @FXML
    private Button upper;

    String value = "";

    public void homeBtnAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        root = loader.load();
        String class_value = value;
        welcomeController welcomeController = loader.getController();
        welcomeController.collectData(class_value);
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void newStudent(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newStudent.fxml"));
        root = loader.load();
        NewStudentController newStudentController = loader.getController();
        newStudentController.deactivateButton();
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void dataChangeUpperAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("upper_update_options.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void collectData(String class_pupil){
        value = class_pupil;
        if(class_pupil.equalsIgnoreCase("P1")||class_pupil.equalsIgnoreCase("P2")||class_pupil.equalsIgnoreCase("P3")){
            upper.setDisable(true);
            nursery.setDisable(true);
        }
        else if(class_pupil.equalsIgnoreCase("P4")||class_pupil.equalsIgnoreCase("P5")||class_pupil.equalsIgnoreCase("P6")||class_pupil.equalsIgnoreCase("P7")){
            lower.setDisable(true);
            nursery.setDisable(true);
        }else if(class_pupil.equalsIgnoreCase("baby")||class_pupil.equalsIgnoreCase("middle")||class_pupil.equalsIgnoreCase("top")){
            upper.setDisable(true);
            lower.setDisable(true);
        }else{

        }
    }

    public void lowerUpdateAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("update_lower_options.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteBtnAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("delete_pupil.fxml"));
        root = loader.load();
        DeletePupilController deletePupilController = loader.getController();
        deletePupilController.deactivateClass(value);
        //root = FXMLLoader.load(getClass().getResource("delete_pupil.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void nurseryBtnAction(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("updateNursery.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
