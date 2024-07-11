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

public class Bot_LowerPrimaryController implements Initializable {
    private Parent root;
    private Stage stage;

    @FXML
    private TextField search_bar;
    @FXML
    private TableView<LowerClass> lower_table;
    @FXML
    private TableColumn<LowerClass, String> student_id;
    @FXML
    private TableColumn<LowerClass, String> student_name;
    @FXML
    private TableColumn<LowerClass, String> student_class;
    @FXML
    private TableColumn<LowerClass, Integer> math;
    @FXML
    private TableColumn<LowerClass, Integer> lit_1;
    @FXML
    private TableColumn<LowerClass, Integer> lit_2;
    @FXML
    private TableColumn<LowerClass, Integer> english;
    @FXML
    private TableColumn<LowerClass, Integer> re;
    @FXML
    private TableColumn<LowerClass, Integer> reading;
    @FXML
    private TableColumn<LowerClass, Integer> aggregates;
    @FXML
    private TableColumn<LowerClass, Double> average;
    @FXML
    private TableColumn<LowerClass, Integer> division;

    ObservableList<LowerClass> lowerClassObservableList = FXCollections.observableArrayList();

    public int getAgg(int num){
        if(num >= 90) return 1;
        else if(num >= 85) return 2;
        else if(num >= 75) return 3;
        else if(num >= 65) return 4;
        else if(num >= 60) return 5;
        else if(num >= 55) return 6;
        else if(num >= 50) return 7;
        else if(num >= 45) return 8;
        else return 9;
    }

    public String getDiv(int num){
        if(num >= 34) return "E";
        else if(num >= 30) return "D";
        else if(num >= 25) return "C";
        else if(num >= 13) return "B";
        else return "A";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connected = connect.getConnected();
        String query = "select * from pupil,bot_l where ID = pupil_ID and Level = 2";

        try{
            Statement state = connected.createStatement();
            ResultSet rs = state.executeQuery(query);

            while(rs.next()){
                String queryID = rs.getString("ID");
                String queryName = rs.getString("Name");
                String queryClass = rs.getString("Class");
                Integer queryMath = rs.getInt("math");
                Integer queryLit1 = rs.getInt("lit1");
                Integer queryLit2 = rs.getInt("lit2");
                Integer queryEnglish = rs.getInt("eng");
                Integer queryRe = rs.getInt("re");
                Integer queryReading = rs.getInt("reading");

                int aggregateValue = getAgg(queryMath) + getAgg(queryLit1) + getAgg(queryReading) + getAgg(queryEnglish);
                int av_value = (queryEnglish+queryMath+queryLit1+queryLit2+queryRe+queryReading)/6;
                String div = getDiv(aggregateValue);

                lowerClassObservableList.add(new LowerClass(queryID,queryName,queryClass,queryMath,queryEnglish,queryLit1,queryLit2,queryRe,queryReading,aggregateValue,av_value,div));
            }

            student_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            student_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            student_class.setCellValueFactory(new PropertyValueFactory<>("std_class"));
            math.setCellValueFactory(new PropertyValueFactory<>("math"));
            english.setCellValueFactory(new PropertyValueFactory<>("english"));
            lit_1.setCellValueFactory(new PropertyValueFactory<>("lit_1"));
            lit_2.setCellValueFactory(new PropertyValueFactory<>("lit_2"));
            re.setCellValueFactory(new PropertyValueFactory<>("re"));
            reading.setCellValueFactory(new PropertyValueFactory<>("reading"));
            aggregates.setCellValueFactory(new PropertyValueFactory<>("agg"));
            average.setCellValueFactory(new PropertyValueFactory<>("average"));
            division.setCellValueFactory(new PropertyValueFactory<>("division"));

            lower_table.setItems(lowerClassObservableList);

            FilteredList<LowerClass> filteredData = new FilteredList<>(lowerClassObservableList, b -> true);

            search_bar.textProperty().addListener((observable,oldValue,newValue) -> {
                filteredData.setPredicate(LowerClass -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if(LowerClass.getId().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(LowerClass.getName().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(LowerClass.getStd_class().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(LowerClass.getDivision().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else return false;
                });
            });

            SortedList<LowerClass> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(lower_table.comparatorProperty());

            lower_table.setItems(sortedData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("lower_option.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
