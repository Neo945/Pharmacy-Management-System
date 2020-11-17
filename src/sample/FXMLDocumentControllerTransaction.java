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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Medicines;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class FXMLDocumentControllerTransaction {
    @FXML
    private TextField searchMed;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<BorderPane> MedicineList;
    @FXML
    private Label totalCost;
    @FXML
    private Button proceedButton;
    @FXML
    private Label emptyLabel;
    private final ArrayList<Medicines> list;
    private int totalCostValue = 0;

    public FXMLDocumentControllerTransaction() {
        this.list = new ArrayList<>();
    }

    public void onClickSearchMedicine(ActionEvent actionEvent) {
        try {
            MedicineList.getItems().clear();
            String search = searchMed.getText();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            ArrayList<Medicines> searchList = dataSource.searchMed(search);
            for (Medicines m : searchList) {
                BorderPane bp = new BorderPane();
                Button plusButton = new Button("+");
                plusButton.setOnAction(this::onClickPlusButton);
                plusButton.setId(m.getName());
                System.out.println(m.getName());
//                System.out.println(m.);
                Label medName = new Label(m.getName());
                bp.setLeft(medName);
                bp.setRight(plusButton);
                MedicineList.getItems().add(bp);
            }
            dataSource.connectionClose();
        } catch (Exception e) {
            System.out.println("Exception:(onClickSearchMedicine) " + e.getMessage());
        }
    }

    private void onClickPlusButton(ActionEvent actionEvent) {
        System.out.println((actionEvent.getSource().toString().split("=")[1].split(",")[0]));
        try{
            String idMedName = actionEvent.getSource().toString().split("=")[1].split(",")[0];
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            totalCostValue += dataSource.getPrice(idMedName);
            Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
            totalCost.setText("Total Cost - " + indiaCurrency.getSymbol() + " " + totalCostValue);
            dataSource.decrementQuant(idMedName);
            list.add(dataSource.getMed(idMedName));
        }catch (Exception e){
            System.out.println("Exception:(onClickPlusButton) " + e.getMessage());
        }
    }
    @FXML
    private void onClickProceed(ActionEvent actionEvent){
        DataSource.val= list;
            for (Medicines m:
                    DataSource.val) {
                System.out.println(m.getName() + "\t" + m.getMed_id()+ "\t"  + m.getQuantity());
            }
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Add Patient.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException exception){
            System.out.println("Exception: (toLoginPage)" + exception);
        }

    }
}