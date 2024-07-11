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
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateNurseryController implements Initializable {
    private Parent root;
    private Stage stage;

    @FXML
    private TextField id;
    @FXML
    private ComboBox<String> subject;
    @FXML
    private ComboBox<String> result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> subj = FXCollections.observableArrayList("English Comprehension","English Spoken","English Reading Readiness","Sharing","Numbers","Alphabet","Shadings","Reading","Luganda Reading Readiness","Singing","Drawing/Painting","Games","Cleanliness","General Behaviour","Toilet Habits","Book Working");
        subject.setItems(subj);
        ObservableList<String> res = FXCollections.observableArrayList("Excellent","V.Good","Good","Fair","Poor");
        result.setItems(res);
    }

    public String getSubj(String value){
        if(value.equals("English Comprehension")) return "comprehension";
        else if(value.equals("English Spoken")) return "spoken";
        else if(value.equals("English Reading Readiness")) return "RReadiness";
        else if(value.equals("Numbers")) return "numbers";
        else if(value.equals("Reading")) return "reading";
        else if(value.equals("Alphabet")) return "alphabet";
        else if(value.equals("Luganda Reading Readiness")) return "luganda";
        else if(value.equals("Singing")) return "singing";
        else if(value.equals("Drawing/Painting")) return "drawing";
        else if(value.equals("Cleanliness")) return "cleanliness";
        else if(value.equals("Sharing")) return "sharing";
        else if(value.equals("Shadings")) return "shading";
        else if(value.equals("Book Working")) return "BWorking";
        else if(value.equals("Games")) return "games";
        else if(value.equals("General Behaviour")) return "GBehaviour";
        else return "THabits";
    }


    public void updateValue(ActionEvent actionEvent) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.getConnected();
        try{
            Statement state = con.createStatement();
            String query = "select * from pupil where ID = '"+id.getText()+"'";
            ResultSet rs = state.executeQuery(query);
            if(rs.next()){
                if(subject.getValue() != null && result.getValue() != null){
                    String q = "update nursery set "+getSubj(subject.getValue())+" = '"+result.getValue()+"' where pupil_ID = '"+id.getText()+"'";
                    state.executeUpdate(q);
                    JOptionPane.showMessageDialog(null,"Results Updated successfully");
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a subject and result ");
                }
            }else{
                JOptionPane.showMessageDialog(null,"ID not found");
                id.clear();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void backHome(ActionEvent event) throws IOException {
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
