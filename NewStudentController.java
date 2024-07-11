package com.example.stephen_app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NewStudentController implements Initializable {
    public Parent root;
    public Stage stage;
    @FXML
    public ComboBox<String> gender;
    @FXML
    public ComboBox<String> level;
    @FXML
    private TextField std_id;
    @FXML
    private TextField std_name;
    @FXML
    private TextField std_class;


    ObservableList<String> genderCombo = FXCollections.observableArrayList("M","F");
    ObservableList<String> levelCombo = FXCollections.observableArrayList("Lower Primary","Upper Primary","Nursery");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.setItems(genderCombo);
        level.setItems(levelCombo);
    }

    public int pupil_level(String value){
        return (value.equalsIgnoreCase("Lower Primary")) ? 2:(value.equalsIgnoreCase("Upper Primary")) ? 3 : 1;
    }

    public void saveAction(ActionEvent event){
        int x_level = pupil_level(level.getValue());
        String class_value = welcomeController.pupil_class.equalsIgnoreCase("admin") ? std_class.getText() : welcomeController.pupil_class;
        String query = "insert into pupil values('"+std_id.getText()+"','"+std_name.getText()+"','"+class_value+"','"+gender.getValue()+"','"+x_level+"')";
        DatabaseConnection connect = new DatabaseConnection();
        Connection connection = connect.getConnected();
        try{
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            if(x_level == 1){
                String x = "insert into nursery values ('','','','','','','','','','','','','','','','','"+std_id.getText()+"')";
                state.executeUpdate(x);
            }else if(x_level == 2){
                String x = "insert into bot_l values (0,0,0,0,0,0,'"+std_id.getText()+"')";
                String y = "insert into mid_l values (0,0,0,0,0,0,'"+std_id.getText()+"')";
                String z = "insert into end_l values (0,0,0,0,0,0,'"+std_id.getText()+"')";
                state.executeUpdate(x);
                state.executeUpdate(y);
                state.executeUpdate(z);
            }else {
                String x = "insert into bot_u values (0,0,0,0,'"+std_id.getText()+"')";
                String y = "insert into mid_u values (0,0,0,0,'"+std_id.getText()+"')";
                String z = "insert into end_u values (0,0,0,0,'"+std_id.getText()+"')";
                state.executeUpdate(x);
                state.executeUpdate(y);
                state.executeUpdate(z);
            }
            std_id.clear();
            //std_class.clear();
            std_name.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deactivateButton(){
        if(welcomeController.pupil_class.equalsIgnoreCase("admin")){

        }else{
            std_class.setDisable(true);
            std_class.setText(welcomeController.pupil_class);
        }
    }

    public void backHome(ActionEvent event) throws IOException{
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
