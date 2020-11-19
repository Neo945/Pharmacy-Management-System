package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.DataSource;
import sample.model.Medicines;

import java.util.ArrayList;

public class FXMLDocumentControllerFinalBill {
    @FXML
    private TableView<Medicines> medicinesTableView;
    @FXML
    private TableColumn<Medicines,String> name;
    @FXML
    private TableColumn<Medicines,Double> med_price;
//    private final
    @FXML
    private Button save;
    @FXML
    private Button print;

    public void initialize() {
//        ArrayList<Medicines> medicinesArrayList = new ArrayList<>();
        ObservableList<Medicines> list = FXCollections.observableArrayList();
        for (Medicines m :
                DataSource.medicinesArrayList) {
            if(DataSource.medicineHashMap.get(m.getName())>0) list.add(m);
        }
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        med_price.setCellValueFactory(new PropertyValueFactory<>("med_price"));
        medicinesTableView.setItems(list);
//        medicinesTableView.getColumns().addAll(name,med_price);
    }
//    public onSave
}
