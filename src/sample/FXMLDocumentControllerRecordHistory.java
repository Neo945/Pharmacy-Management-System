package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.Bill;
import sample.model.DataSource;

public class FXMLDocumentControllerRecordHistory {
    @FXML
    private TableView<Bill> record;
    @FXML
    private TableColumn<Bill,String> pat_name;
    @FXML
    private TableColumn<Bill,Double> bill_amount;
    @FXML
    private TableColumn<Bill,String> bill_Date;
    @FXML
    private TableColumn<Bill,String> status;
    public void initialize(){
        record.getItems().removeAll();
        DataSource dataSource = new DataSource();
        dataSource.connectionOpen();
        dataSource.generateBillList();
        dataSource.connectionClose();
        ObservableList<Bill> list = FXCollections.observableArrayList();
        list.addAll(DataSource.bills);
        pat_name.setCellValueFactory(new PropertyValueFactory<>("pat_name"));
        bill_amount.setCellValueFactory(new PropertyValueFactory<>("bill_amount"));
        bill_Date.setCellValueFactory(new PropertyValueFactory<>("bill_date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        record.setItems(list);
    }

    public void onBackClicked(ActionEvent actionEvent){
        try {
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
    }
}
