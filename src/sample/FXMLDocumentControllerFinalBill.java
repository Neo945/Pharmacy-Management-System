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

public class FXMLDocumentControllerFinalBill {
    @FXML
    private TableView<Medicines> medicinesTableView;
    @FXML
    private TableColumn<Medicines,String> name;
    @FXML
    private TableColumn<Medicines,Integer> med_price;
    private final ObservableList<Medicines> list = FXCollections.observableArrayList(DataSource.val);
    @FXML
    private Button save;
    @FXML
    private Button print;

    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        med_price.setCellValueFactory(new PropertyValueFactory<>("med_price"));
        medicinesTableView.setItems(list);
    }
//    public onSave
}
