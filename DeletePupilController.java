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

public class DeletePupilController {
    private Parent root;
    private Stage stage;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField p_class;

    String value = "";

    public void deleteData(ActionEvent event) throws IOException{
        DatabaseConnection data = new DatabaseConnection();
        String q1 = "";
        String q2 = "";
        String q3 = "";
        String q4 = "";
        Connection connection = data.getConnected();
        try{
            Statement statement = connection.createStatement();
            String dataTest = "select * from pupil where ID = '"+id.getText()+"' and Name = '"+name.getText()+"' and Class = '"+value+"'";
            ResultSet rs = statement.executeQuery(dataTest);
            if (rs.next()){
                if(value.equalsIgnoreCase("p1")||value.equalsIgnoreCase("p2")||value.equalsIgnoreCase("p3")){
                    q1 = "delete from bot_l where pupil_ID = '"+id.getText()+"'";
                    q2 = "delete from mid_l where pupil_ID = '"+id.getText()+"'";
                    q3 = "delete from end_l where pupil_ID = '"+id.getText()+"'";
                    q4 = "delete from pupil where Id = '"+id.getText()+"'";
                }else if(value.equalsIgnoreCase("p4")||value.equalsIgnoreCase("p5")||value.equalsIgnoreCase("p6")||value.equalsIgnoreCase("p7")){
                    q1 = "delete from bot_u where pupil_ID = '"+id.getText()+"'";
                    q2 = "delete from mid_u where pupil_ID = '"+id.getText()+"'";
                    q3 = "delete from end_u where pupil_ID = '"+id.getText()+"'";
                    q4 = "delete from pupil where Id = '"+id.getText()+"'";
                }else if(value.equalsIgnoreCase("baby")||value.equalsIgnoreCase("middle")||value.equalsIgnoreCase("top")){
                    q1 = "delete from nursery where pupil_ID = '"+id.getText()+"'";
                    q2 = "delete from pupil where Id = '"+id.getText()+"'";
                    q3 = "delete from pupil where Id = '"+id.getText()+"'";
                    q4 = "delete from pupil where Id = '"+id.getText()+"'";
                }
                statement.executeUpdate(q1);
                statement.executeUpdate(q2);
                statement.executeUpdate(q3);
                statement.executeUpdate(q4);
                JOptionPane.showMessageDialog(null,"Delete successfully");
                id.clear();
                name.clear();
                p_class.clear();
            }
            else{
                JOptionPane.showMessageDialog(null,"Pupil not found");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deactivateClass(String c_value){
        if(!welcomeController.pupil_class.equalsIgnoreCase("admin")){
            p_class.setDisable(true);
            p_class.setText(c_value);
            value = c_value;
        }else{
            value = p_class.getText();
        }

    }

    public void backHome(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data.fxml"));
        root = loader.load();
        dataController controller = loader.getController();
        controller.collectData(value);
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

