package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.DataSource;
import sample.model.Medicines;

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
        for (Medicines m :
                DataSource.medicinesArrayList) {
            if(DataSource.medicineHashMap.get(m.getName())>0) {
                m.setQuant(DataSource.medicineHashMap.get(m.getName()));
                list.add(m);
            }
        }
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
}
