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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.model.AppData;
import sample.model.DataSource;

import java.io.IOException;
import java.util.*;

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
    private double totalCostValue = 0;
    public void initialize(){
        try {
//            MedicineList.getItems().clear();
            String search = searchMed.getText();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            /*if(DataSource.MedNameHashMap.isEmpty())*/ dataSource.createMedicineList();
            AppData.MedNameHashMap.forEach((k, v)-> {
                if(!(v.getQuantity()<1)){
                BorderPane bp = new BorderPane();
                HBox hbx = new HBox();

                Button minusButton = new Button("-");
                minusButton.setOnAction(this::onClickMinusButton);
                minusButton.setId(k + "m");

                Button plusButton = new Button("+");
                plusButton.setOnAction(this::onClickPlusButton);
                plusButton.setId(k + "p");


                Label quant = new Label(String.format("%2d ",v.getQuant()));
                quant.setId(k + "q");

                Label medName = new Label(k);

                hbx.getChildren().add(minusButton);
                hbx.getChildren().add(quant);
                hbx.getChildren().add(plusButton);


                bp.setLeft(medName);
                bp.setRight(hbx);
                MedicineList.getItems().add(bp);
                }
            });
            dataSource.connectionClose();
        } catch (Exception e) {
            System.out.println("Exception:(onClickSearchMedicine) " + e.getMessage());
        }
    }
    public void onClickSearchMedicine(ActionEvent actionEvent) {
        try {
            MedicineList.getItems().clear();
            String search = searchMed.getText();
            DataSource dataSource = new DataSource();
            AppData.MedNameHashMap.forEach((k,v)-> {
                if(k.contains(search)){
                    BorderPane bp = new BorderPane();
                    HBox hbx = new HBox();

                    Button minusButton = new Button("-");
                    minusButton.setOnAction(this::onClickMinusButton);
                    minusButton.setId(k + "m");

                    Button plusButton = new Button("+");
                    plusButton.setOnAction(this::onClickPlusButton);
                    plusButton.setId(k + "p");


                    Label quant = new Label(String.format("%2d ",v.getQuant()));
                    quant.setId(k + "q");

                    Label medName = new Label(k);

                    hbx.getChildren().add(minusButton);
                    hbx.getChildren().add(quant);
                    hbx.getChildren().add(plusButton);


                    bp.setLeft(medName);
                    bp.setRight(hbx);
                    MedicineList.getItems().add(bp);
                }
            });
            dataSource.connectionClose();
        } catch (Exception e) {
            System.out.println("Exception:(onClickSearchMedicine) " + e.getMessage());
        }
    }

    private void onClickPlusButton(ActionEvent actionEvent) {
        try{
            String idMedName = actionEvent.getSource().toString().split("=")[1].split(",")[0].split("p")[0];
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            if(AppData.MedNameHashMap.get(idMedName).getQuant()>8) return;
            if((AppData.MedNameHashMap.get(idMedName).getQuantity())<=(AppData.MedNameHashMap.get(idMedName).getQuant()))return;
            totalCostValue += AppData.MedNameHashMap.get(idMedName).getMed_price();
            Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
            totalCost.setText("Total Cost - " + indiaCurrency.getSymbol() + " " + totalCostValue);
            int count = AppData.MedNameHashMap.get(idMedName).getQuant();
            AppData.MedNameHashMap.get(idMedName).setQuant(count+1);
            for(int i = 0;i<MedicineList.getItems().size();i++){
                if(((HBox)(MedicineList.getItems().get(i).getRight())).getChildren().get(1).getId().split("q")[0].equals(idMedName)){
                    ((Label)((HBox)(MedicineList.getItems().get(i).getRight())).getChildren().
                            get(1)).setText(String.format("%2d ",AppData.MedNameHashMap.get(idMedName).getQuant()));
                }
            }
        }catch (Exception e){
            System.out.println("Exception:(onClickPlusButton) " + e.getMessage());
        }
    }
    private void onClickMinusButton(ActionEvent actionEvent) {
        try{
            String idMedName = actionEvent.getSource().toString().split("=")[1].split(",")[0].split("m")[0];
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            if(totalCostValue==0)return;
//            if(DataSource.medicineHashMap.get(idMedName)<1) return;
            if(AppData.MedNameHashMap.get(idMedName).getQuant()<1) return;
            totalCostValue -= AppData.MedNameHashMap.get(idMedName).getMed_price();
            Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
            totalCost.setText("Total Cost - " + indiaCurrency.getSymbol() + " " + totalCostValue);
//            dataSource.decrementQuant(idMedName);
//            list.add(dataSource.getMed(idMedName));
            int count = AppData.MedNameHashMap.get(idMedName).getQuant();
            AppData.MedNameHashMap.get(idMedName).setQuant(count-1);
            for(int i = 0;i<MedicineList.getItems().size();i++){
                if(((HBox)(MedicineList.getItems().get(i).getRight())).getChildren().get(1).getId().split("q")[0].equals(idMedName)){
                    ((Label)((HBox)(MedicineList.getItems().get(i).getRight())).getChildren().get(1))
                            .setText(String.format("%2d ",AppData.MedNameHashMap.get(idMedName).getQuant()));
                }
            }
        }catch (Exception e){
            System.out.println("Exception:(onClickPlusButton) " + e.getMessage());
        }
    }
        @FXML
    private void onClickProceed(ActionEvent actionEvent) {
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Add Patient.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException exception){
            System.out.println("Exception: (onClickProceed)" + exception);
        }
    }
    public void onClickBack(ActionEvent actionEvent) {
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException exception){
            System.out.println("Exception: (onClickProceed)" + exception);
        }
    }
}