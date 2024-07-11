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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginPageLower implements Initializable {
    @FXML
    private TextField full_name;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> classes;

    private Parent root;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<String> termList = FXCollections.observableArrayList("Admin","P1","P2","P3","P4","P5","P6","P7","Baby","Middle","Top");
        classes.setItems(termList);
    }

    public void verifyTeacher(ActionEvent event) throws Exception{
        DatabaseConnection data = new DatabaseConnection();
        Connection connection = data.getConnected();
        String query = "select * from teachers where T_Name = '"+full_name.getText()+"' and Password = '"+password.getText()+"' and Class = '"+classes.getValue()+"'";
        try{
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if(rs.next()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
                root = loader.load();
                welcomeController welcomeController = loader.getController();
                welcomeController.collectData(classes.getValue());
                //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }else{
                if(full_name.getText().equalsIgnoreCase("")||password.getText().equalsIgnoreCase("")){
                    JOptionPane.showMessageDialog(null,"Fill in all the required fields");
                }else if(classes.getValue() == null){
                    JOptionPane.showMessageDialog(null,"Select a class");
                }else{
                    JOptionPane.showMessageDialog(null,"Invalid Details entered");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
