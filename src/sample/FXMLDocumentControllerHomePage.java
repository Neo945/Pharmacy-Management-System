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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.DataSource;

import java.io.IOException;

public class FXMLDocumentControllerHomePage {
    @FXML
    private Label name;
    @FXML
    private Label email;
//    @FXML
//    private Button setting;
//    @FXML
//    private Button notify;
    @FXML
    private Button logout;
    @FXML
    private Button details;
    @FXML
    private Button transactionDetails;
    @FXML
    private Button AddMed;
    @FXML
    private Button searchMed;
    public void initialize(){
        try{
            name.setText(DataSource.loginBoy.getEmp_name());
            name.setTextFill(Color.WHITE);
            email.setText(DataSource.loginBoy.getEmail());
            email.setTextFill(Color.WHITE);
            details.setText(DataSource.loginBoy.getEmp_role() + " Details");
            if(DataSource.loginBoy.getEmp_role().equals("Pharmacist")){
                searchMed.setVisible(false);
            }else if (DataSource.loginBoy.getEmp_role().equals("Cashier")){
                AddMed.setVisible(false);
            }
        }catch (Exception e){
            System.out.println("Exception: " +e.getMessage());
        }
    }
    public void onLogOutClicked(ActionEvent actionEvent){
        try{
            try{
                DataSource.loginBoy = null;
                DataSource.employees.clear();
                DataSource.selectedPatient = null;
                DataSource.MedNameHashMap.clear();
                DataSource.amount = -1;
                Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
                Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
                primaryStage.setTitle("Hello ");
                primaryStage.setScene(new Scene(root, 750, 600));
                primaryStage.show();
            }catch (IOException exception){
                System.out.println("Exception: (login->homePage)" + exception);
            }
        }catch (Exception e){
            System.out.println("Exception(onLogoutClicked):" + e.getMessage());
        }
    }
    public  void onSettingCLicked(ActionEvent actionEvent){
        try{
            try{
                Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
                Parent root = FXMLLoader.load(getClass().getResource("SettingsPage.fxml"));
                primaryStage.setTitle("Hello ");
                primaryStage.setScene(new Scene(root, 750, 600));
                primaryStage.show();
            }catch (IOException exception){
                System.out.println("Exception: (login->homePage)" + exception);
            }
        }catch (Exception e){
            System.out.println("Exception(onSettingClicked):" + e.getMessage());
        }
    }
    public void onNotificationClicked(ActionEvent actionEvent){
        try{
            Stage notifyStage = new Stage();
            BorderPane bp = new BorderPane();
            ListView<String> stringListView = new ListView<>();
            stringListView.getItems().set(1,"");
            bp.setCenter(stringListView);
            notifyStage.setTitle("Hello ");
            notifyStage.setScene(new Scene(bp,750, 600));
            notifyStage.show();
        }catch (Exception e){
            System.out.println("Exception(onNotificationClicked):" + e.getMessage());
        }
    }
    public void onDetailsClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception(): " + e.getMessage() );
        }
    }
    public void onTransactionClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("RecordHistory.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception(): " + e.getMessage() );
        }
    }public void onAddMedicineClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("PharmacistAddMedicine.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception(): " + e.getMessage() );
        }
    }
    public void onSearchClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception(): " + e.getMessage() );
        }
    }
}
