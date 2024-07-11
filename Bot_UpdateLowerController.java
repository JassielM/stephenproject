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

public class Bot_UpdateLowerController {

    private Parent root;
    private Stage stage;

    @FXML
    private TextField id;
    @FXML
    private TextField math;
    @FXML
    private TextField lit_1;
    @FXML
    private TextField lit_2;
    @FXML
    private TextField english;
    @FXML
    private TextField re;
    @FXML
    private TextField reading;

    public void updateValue(ActionEvent event){
        String q = "select * from pupil where ID = '"+id.getText()+"'";
        String query = "";
        DatabaseConnection connect = new DatabaseConnection();
        Connection connection = connect.getConnected();
        try{
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(q);
            if(rs.next()){
                String check = "select * from bot_l where pupil_ID = '"+id.getText()+"'";
                ResultSet checked = state.executeQuery(check);
                if(checked.next()){
                    query = "update bot_l set math = "+Integer.parseInt(math.getText())+", lit1 = "+Integer.parseInt(lit_1.getText())+",eng = "+Integer.parseInt(english.getText())+",lit2 = "+Integer.parseInt(lit_2.getText())+",re = "+Integer.parseInt(re.getText())+",reading = "+Integer.parseInt(reading.getText())+" where pupil_ID = '"+id.getText()+"'";
                }else{
                    query =  "insert into bot_l values ("+Integer.parseInt(math.getText())+","+Integer.parseInt(english.getText())+","+Integer.parseInt(re.getText())+","+Integer.parseInt(lit_1.getText())+",'"+Integer.parseInt(lit_2.getText())+","+Integer.parseInt(reading.getText())+","+id.getText()+"')";
                }
                state.executeUpdate(query);
                JOptionPane.showMessageDialog(null,"Data Updated");
            }else{
                JOptionPane.showMessageDialog(null,"ID doesn't exit");
            }
            math.clear();
            lit_2.clear();
            lit_1.clear();
            english.clear();
            re.clear();
            reading.clear();
            id.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void backHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("update_lower_options.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
