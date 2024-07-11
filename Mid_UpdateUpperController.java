package com.example.stephen_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Mid_UpdateUpperController {

    private Parent root;
    private Stage stage;

    @FXML
    private TextField id;
    @FXML
    private TextField math;
    @FXML
    private TextField science;
    @FXML
    private TextField sst;
    @FXML
    private TextField english;

    public void updateValue(ActionEvent event){
        String q = "select * from pupil where ID = '"+id.getText()+"'";
        String query = "";
        DatabaseConnection connect = new DatabaseConnection();
        Connection connection = connect.getConnected();
        try{
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(q);
            if(rs.next()){
                String check = "select * from mid_u where pupil_ID = '"+id.getText()+"'";
                ResultSet checked = state.executeQuery(check);
                if(checked.next()){
                    query = "update mid_u set math = "+Integer.parseInt(math.getText())+",sst = "+Integer.parseInt(sst.getText())+",eng = "+Integer.parseInt(english.getText())+",science = "+Integer.parseInt(science.getText())+" where pupil_ID = '"+id.getText()+"'";
                }else{
                    query =  "insert into mid_u values ("+Integer.parseInt(math.getText())+","+Integer.parseInt(science.getText())+","+Integer.parseInt(english.getText())+","+Integer.parseInt(sst.getText())+",'"+id.getText()+"')";
                }
                state.executeUpdate(query);
                JOptionPane.showMessageDialog(null,"Data Updated");
            }else{
                JOptionPane.showMessageDialog(null,"ID doesn't exit");
            }
            math.clear();
            english.clear();
            sst.clear();
            science.clear();
            id.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void backHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("upper_update_options.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
