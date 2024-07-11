package com.example.stephen_app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Mid_UpperPrimaryController implements Initializable {
    private Parent root;
    private Stage stage;

    @FXML
    private TextField search_bar;
    @FXML
    private TableView<UpperClass> upper_table;
    @FXML
    private TableColumn<UpperClass, String> student_id;
    @FXML
    private TableColumn<UpperClass, String> student_name;
    @FXML
    private TableColumn<UpperClass, String> student_class;
    @FXML
    private TableColumn<UpperClass, Integer> math;
    @FXML
    private TableColumn<UpperClass, Integer> science;
    @FXML
    private TableColumn<UpperClass, Integer> sst;
    @FXML
    private TableColumn<UpperClass, Integer> english;
    @FXML
    private TableColumn<UpperClass, Integer> aggregates;
    @FXML
    private TableColumn<UpperClass, Integer> division;
    @FXML
    private TableColumn<UpperClass, Double> average;

    ObservableList<UpperClass> upperClassObservableList = FXCollections.observableArrayList();

    public int getAgg(int num){
        if(num >= 90) return 1;
        else if(num >= 80) return 2;
        else if(num >= 70) return 3;
        else if(num >= 60) return 4;
        else if(num >= 55) return 5;
        else if(num >= 50) return 6;
        else if(num >= 45) return 7;
        else if(num >= 40) return 8;
        else return 9;
    }

    public String getDiv(int num){
        if(num >= 34) return "V";
        else if(num >= 30) return "IV";
        else if(num >= 25) return "III";
        else if(num >= 13) return "II";
        else return "I";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connected = connect.getConnected();
        String query = "select * from pupil,mid_u where ID = pupil_ID and Level = 3";

        try{
            Statement state = connected.createStatement();
            ResultSet rs = state.executeQuery(query);

            while(rs.next()){
                String queryID = rs.getString("ID");
                String queryName = rs.getString("Name");
                String queryClass = rs.getString("Class");
                Integer queryMath = rs.getInt("math");
                Integer queryScience = rs.getInt("science");
                Integer querySST = rs.getInt("sst");
                Integer queryEnglish = rs.getInt("eng");

                int aggregateValue = getAgg(queryMath) + getAgg(queryScience) + getAgg(querySST) + getAgg(queryEnglish);
                int av_value = (queryEnglish+queryMath+queryScience+querySST)/4;
                String div = getDiv(aggregateValue);


                upperClassObservableList.add(new UpperClass(queryID,queryName,queryClass,queryMath,queryScience,querySST,queryEnglish,aggregateValue,av_value,div));
            }

            student_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            student_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            student_class.setCellValueFactory(new PropertyValueFactory<>("std_class"));
            math.setCellValueFactory(new PropertyValueFactory<>("math"));
            science.setCellValueFactory(new PropertyValueFactory<>("science"));
            sst.setCellValueFactory(new PropertyValueFactory<>("sst"));
            english.setCellValueFactory(new PropertyValueFactory<>("english"));
            aggregates.setCellValueFactory(new PropertyValueFactory<>("agg"));
            average.setCellValueFactory(new PropertyValueFactory<>("average"));
            division.setCellValueFactory(new PropertyValueFactory<>("division"));

            upper_table.setItems(upperClassObservableList);

            FilteredList<UpperClass> filteredData = new FilteredList<>(upperClassObservableList, b -> true);

            search_bar.textProperty().addListener((observable,oldValue,newValue) -> {
                filteredData.setPredicate(UpperClass -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if(UpperClass.getId().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(UpperClass.getName().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(UpperClass.getStd_class().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else return false;
                });
            });

            SortedList<UpperClass> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(upper_table.comparatorProperty());

            upper_table.setItems(sortedData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("upper_option.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
