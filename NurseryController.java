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

public class NurseryController implements Initializable {
    private Parent root;
    private Stage stage;

    @FXML
    private TextField search_bar;
    @FXML
    private TableView<NurseryClass> nursery_table;
    @FXML
    private TableColumn<NurseryClass, String> student_id;
    @FXML
    private TableColumn<NurseryClass, String> student_name;
    @FXML
    private TableColumn<NurseryClass, String> student_class;
    @FXML
    private TableColumn<NurseryClass, String> ec;
    @FXML
    private TableColumn<NurseryClass, String> es;
    @FXML
    private TableColumn<NurseryClass, String> err;
    @FXML
    private TableColumn<NurseryClass, String> num;
    @FXML
    private TableColumn<NurseryClass, String> al;
    @FXML
    private TableColumn<NurseryClass, String> reading;
    @FXML
    private TableColumn<NurseryClass, String> lrr;
    @FXML
    private TableColumn<NurseryClass, String> singing;
    @FXML
    private TableColumn<NurseryClass, String> painting;
    @FXML
    private TableColumn<NurseryClass, String> cleanliness;
    @FXML
    private TableColumn<NurseryClass, String> gb;
    @FXML
    private TableColumn<NurseryClass, String> th;

    ObservableList<NurseryClass> nurseryObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connected = connect.getConnected();
        String query = "select * from pupil,nursery where ID = pupil_ID and Level = 1";
        try{
            Statement state = connected.createStatement();
            ResultSet rs = state.executeQuery(query);

            while(rs.next()){
                String queryID = rs.getString("ID");
                String queryName = rs.getString("Name");
                String queryClass = rs.getString("Class");
                String queryEc = rs.getString("comprehension");
                String queryEs = rs.getString("spoken");
                String queryErr = rs.getString("RReadiness");
                String queryNum = rs.getString("numbers");
                String queryReading = rs.getString("reading");
                String queryAl = rs.getString("alphabet");
                String queryLug = rs.getString("luganda");
                String querySinging = rs.getString("singing");
                String queryDrawing = rs.getString("drawing");
                String queryCl = rs.getString("cleanliness");
                String queryGb = rs.getString("GBehaviour");
                String queryTh = rs.getString("THabits");

                nurseryObservableList.add(new NurseryClass(queryID,queryName,queryClass,queryEc,queryEs,queryErr,queryNum,queryReading,queryAl,queryLug,querySinging,queryDrawing,queryCl,queryGb,queryTh));
            }

            student_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            student_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            student_class.setCellValueFactory(new PropertyValueFactory<>("StdClass"));
            ec.setCellValueFactory(new PropertyValueFactory<>("ec"));
            es.setCellValueFactory(new PropertyValueFactory<>("es"));
            err.setCellValueFactory(new PropertyValueFactory<>("err"));
            num.setCellValueFactory(new PropertyValueFactory<>("num"));
            reading.setCellValueFactory(new PropertyValueFactory<>("reading"));
            al.setCellValueFactory(new PropertyValueFactory<>("al"));
            lrr.setCellValueFactory(new PropertyValueFactory<>("lrr"));
            singing.setCellValueFactory(new PropertyValueFactory<>("singing"));
            painting.setCellValueFactory(new PropertyValueFactory<>("drawing"));
            cleanliness.setCellValueFactory(new PropertyValueFactory<>("cleanliness"));
            gb.setCellValueFactory(new PropertyValueFactory<>("gb"));
            th.setCellValueFactory(new PropertyValueFactory<>("th"));

            nursery_table.setItems(nurseryObservableList);

            FilteredList<NurseryClass> filteredData = new FilteredList<>(nurseryObservableList, b -> true);

            search_bar.textProperty().addListener((observable,oldValue,newValue) -> {
                filteredData.setPredicate(NurseryClass -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if(NurseryClass.getId().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(NurseryClass.getName().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else if(NurseryClass.getStdClass().toLowerCase().indexOf(searchKeyword) > -1) return true;
                    else return false;
                });
            });

            SortedList<NurseryClass> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(nursery_table.comparatorProperty());

            nursery_table.setItems(sortedData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void backHome(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        root = loader.load();
        String class_value = welcomeController.pupil_class;
        welcomeController welcomeController = loader.getController();
        welcomeController.collectData(class_value);
        //root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
