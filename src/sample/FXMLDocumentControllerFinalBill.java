package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Medicines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FXMLDocumentControllerFinalBill {
    @FXML
    private TableView<Medicines> medicinesTableView;
    @FXML
    private TableColumn<Medicines,String> name;
    @FXML
    private TableColumn<Medicines,Double> med_price;
    @FXML
    private TableColumn<Medicines,Integer> quant;
//    private final
    @FXML
    private Button save;
    @FXML
    private Button print;
//    private HashMap<Medicines,>
    public void initialize() {
//        ArrayList<Medicines> medicinesArrayList = new ArrayList<>();
        ObservableList<Medicines> list = FXCollections.observableArrayList();
        DataSource.MedNameHashMap.forEach((k,v)-> {
            if(v.getQuant()>0) {
                list.add(v);
            }
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        med_price.setCellValueFactory(new PropertyValueFactory<>("med_price"));
        quant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        medicinesTableView.setItems(list);
//        medicinesTableView.getColumns().addAll(name,med_price);
    }
//    public onSave
    public void onSaveClicked(ActionEvent actionEvent){
        try {
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            dataSource.addToBill();
            dataSource.connectionClose();
        }catch (Exception e){
            System.out.println("Exception:(onSaveClicked) " + e.getMessage());
        }
    }
    public void onBackClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Add Patient.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException e){
            System.out.println("Exception:(onBackClicked) " + e.getMessage());
        }
    }
}
