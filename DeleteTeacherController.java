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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DeleteTeacherController implements Initializable {

    @FXML
    private Parent root;
    @FXML
    private Stage stage;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> class_of_teacher;




    public void deleteAction(ActionEvent event) throws IOException, SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnected();
        Statement statement = connection.createStatement();
        String query = "select * from teachers where T_name = '"+name.getText()+"' and Class = '"+class_of_teacher.getValue()+"'";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()){
            String q = "delete from teachers where T_name = '"+name.getText()+"' and Class = '"+class_of_teacher.getValue()+"'";
            int prompt = JOptionPane.showConfirmDialog(null,"Are you sure?");
            if(prompt == 0){
                statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Deleted successfully");
                name.setText("");
                class_of_teacher.setValue("Class");
            }else{
                JOptionPane.showMessageDialog(null,"Process terminated");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Data not found");
        }
    }

    public void backHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("admin_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> classes = FXCollections.observableArrayList("P1","P2","P3","P4","P5","P6","P7","Baby","Middle","Top");
        class_of_teacher.setItems(classes);
    }
}
