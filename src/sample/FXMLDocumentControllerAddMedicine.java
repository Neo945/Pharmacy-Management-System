package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.AppData;
import sample.model.DataSource;
import sample.model.Medicines;
import sample.model.UserData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FXMLDocumentControllerAddMedicine {
    @FXML
    private TextField med_name;
    @FXML
    private TextField company_name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private ListView<Medicines> medicinesListView;
    @FXML
    private TextField searchMed;
    @FXML
    private Button searchButton;
    public void initialize(){
        try {
            medicinesListView.getItems().clear();
            String search = searchMed.getText();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            if(AppData.MedNameHashMap.isEmpty()) dataSource.createMedicineList();
            AppData.MedNameHashMap.forEach((k,v)-> {
                medicinesListView.getItems().add(v);
            });
            dataSource.connectionClose();
        } catch (Exception e) {
            System.out.println("Exception:(onClickSearchMedicine) " + e.getMessage());
        }
    }
    public void onClickSearchMedicine(ActionEvent actionEvent) {
        try {
            medicinesListView.getItems().clear();
            String search = searchMed.getText();
            DataSource dataSource = new DataSource();
            AppData.MedNameHashMap.forEach((k,v)-> {
                if(k.contains(search)){
                    medicinesListView.getItems().add(v);
                }
            });
            dataSource.connectionClose();
        } catch (Exception e) {
            System.out.println("Exception:(onClickSearchMedicine) " + e.getMessage());
        }
    }
    public void fillData(){
        try {
            Medicines selectedItemsMedicine = medicinesListView.getSelectionModel().getSelectedItem();
            med_name.setText(selectedItemsMedicine.getName());
            company_name.setText(selectedItemsMedicine.getCompany());
            quantity.setText(String.format("%d",selectedItemsMedicine.getQuantity()));
            price.setText(String.format("%f",selectedItemsMedicine.getMed_price()));
            med_name.setEditable(false);
            company_name.setEditable(false);
            price.setEditable(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onClickProceed(ActionEvent actionEvent){
        try {
            Medicines newMed= new Medicines();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            Medicines selectedItemsMedicine = medicinesListView.getSelectionModel().getSelectedItem();
            if(selectedItemsMedicine!=null){
                AppData.selectedMedicine = selectedItemsMedicine;
                AppData.selectedMedicine.setQuantity(Integer.parseInt(quantity.getText()));
            }else {
                newMed.setName(med_name.getText());
                newMed.setQuantity(Integer.parseInt(quantity.getText()));
                newMed.setMed_price(Double.parseDouble(price.getText()));
                newMed.setCompany(company_name.getText());
                AppData.selectedMedicine = newMed;
            }
            dataSource.addMedicine();

            TimeUnit.SECONDS.sleep(2);
//            if(AppData.notificationList.get(0).getText().equals("No Notification")) AppData.notificationList.clear();
            AppData.notificationList.add(new Label(AppData.selectedMedicine.getName() + "\t Added!!"));
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
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
    public void onTransactionCLicked(ActionEvent event){
        try{
            Stage primaryStage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("recordhistory.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void onDetailsClicked(ActionEvent event){
        try{
            Stage primaryStage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("details.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
