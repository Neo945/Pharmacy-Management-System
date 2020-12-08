package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.AppData;
import sample.model.Bill;
import sample.model.DataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FXMLDocumentControllerRecordHistory {
    public ChoiceBox<String> combobox;
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
//    @FXML
//    ComboBox<String> comboBox;
    ObservableList<Bill> list = FXCollections.observableArrayList();
    public void initialize(){
        record.getItems().removeAll();
        DataSource dataSource = new DataSource();
        dataSource.connectionOpen();
        dataSource.generateBillList();
        dataSource.connectionClose();
        list.addAll(AppData.bills);
        pat_name.setCellValueFactory(new PropertyValueFactory<>("pat_name"));
        bill_amount.setCellValueFactory(new PropertyValueFactory<>("bill_amount"));
        bill_Date.setCellValueFactory(new PropertyValueFactory<>("bill_date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        record.setItems(list);
    }
    public void showBill(MouseEvent actionEvent){
        try {
            AppData.bill = record.getSelectionModel().getSelectedItem();
//        DataSource.caller = "record";
        Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("billRecord.fxml"));
//        primaryStage.setTitle("Hello ");
        primaryStage.setScene(new Scene(root, 750, 600));
        primaryStage.show();
        } catch (Exception exception) {
            System.out.println("showBill");
            exception.printStackTrace();
        }
    }
    public void onBackClicked(ActionEvent actionEvent){
        try {
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
    }

    public void onChoiceSelected(ActionEvent mouseEvent) {
        System.out.println(this.combobox.getSelectionModel().getSelectedItem());
        list.clear();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");
        LocalDateTime now = LocalDateTime.now();
        int current_month = Integer.parseInt(dtf.format(now));
        dtf = DateTimeFormatter.ofPattern("yyyy");
        int current_year = Integer.parseInt(dtf.format(now));
        if(this.combobox.getSelectionModel().getSelectedItem().equals("Monthly")){
            for (Bill b :
                    AppData.bills) {
                if(Integer.parseInt(b.getBill_date().split("-")[0])==current_year &&
                        Integer.parseInt(b.getBill_date().split("-")[1])>=current_month){
                    list.add(b);
                }
            }
        }else if (this.combobox.getSelectionModel().getSelectedItem().equals("Yearly")){
            for (Bill b :
                    AppData.bills) {
                if(Integer.parseInt(b.getBill_date().split("-")[0])==current_year){
                    list.add(b);
                }
            }
        }
    }
}
//Integer.parseInt(b.getBill_date().split("-")[1])<=(current_month-4)