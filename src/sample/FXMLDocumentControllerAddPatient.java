package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Medicines;
import sample.model.Patient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class FXMLDocumentControllerAddPatient {
    @FXML
    private TextField searchPatient;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Patient> searchList;
    @FXML
    private TextField PatientName;
    @FXML
    private TextField patAdd;
    @FXML
    private TextField patAge;
    @FXML
    private ToggleGroup genderToggle;
    @FXML
    private ListView<BorderPane> medList;
    @FXML
    private CheckBox robotCheck;
    @FXML
    private Button back;
    @FXML
    private Button generateBill;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton others;
    private double sum = 0;
    public void initialize(){
        try{
            Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
            for (Medicines m :
                    DataSource.medicinesArrayList) {
                if(DataSource.medicineHashMap.get(m.getName())>0){
                    BorderPane bp = new BorderPane();
                    bp.setRight(new Label( indiaCurrency.getSymbol() + " " + m.getMed_price()));
                    bp.setLeft(new Label(m.getName()));
                    bp.setCenter(new Label("X " + DataSource.medicineHashMap.get(m.getName())));
                    sum+=m.getMed_price()*DataSource.medicineHashMap.get(m.getName());
                    medList.getItems().add(bp);
                }
            }
            BorderPane bp = new BorderPane();
            bp.setLeft(new Label("Total"));
            Label labsum = new Label(indiaCurrency.getSymbol() + " " + sum);
            labsum.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            bp.setRight(labsum);
            medList.getItems().add(bp);
        }catch (Exception e){
            System.out.println("Exception:(initialize) " + e.getMessage());
        }
        try{
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            dataSource.searchPat();
            for (Patient patient : DataSource.patientArrayList) {
                searchList.getItems().add(patient);
            }
            dataSource.connectionClose();
        }catch (Exception e){
            System.out.println("Exception:(onSearchClick) " + e.getMessage());
        }
    }

    public void onSearchClick(ActionEvent actionEvent){
        try{
            searchList.getItems().clear();
            String searchString = searchPatient.getText();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            dataSource.searchPat();
            for (Patient patient : DataSource.patientArrayList) {
                if(patient.getPat_name().contains(searchString)) searchList.getItems().add(patient);
            }
            dataSource.connectionClose();
        }catch (Exception e){
            System.out.println("Exception:(onSearchClick) " + e.getMessage());
        }
    }
//    public void fillData(MouseEvent actionEvent){
//        try{
//            Patient selectedItemsPatient= searchList.getSelectionModel().getSelectedItem();
//            PatientName.setText(selectedItemsPatient.getPat_name());
//            patAdd.setText(selectedItemsPatient.getPat_add());
//            patAge.setText("" + selectedItemsPatient.getPat_age());
//            String gender = "" + selectedItemsPatient.getPat_age();
//            if(gender.equals("m")){
//                male.selectedProperty().setValue(true);
//                female.selectedProperty().setValue(false);
//                others.selectedProperty().setValue(false);
//            }else if(gender.equals("f")){
//                male.selectedProperty().set(false);
//                female.selectedProperty().set(true);
//                others.selectedProperty().set(false);
//            }else {
//                male.selectedProperty().set(false);
//                female.selectedProperty().set(false);
//                others.selectedProperty().set(true);
//            }
//            this.selectedPat = selectedItemsPatient;
//        }catch (Exception e){
//            System.out.println("Exception:(fillData) " + e.getMessage());
//        }
//    }
//    public void generateBill(ActionEvent actionEvent){
//        try{
//            DataSource dataSource = new DataSource();
//            dataSource.connectionOpen();
//            if(false){
////                Patient selectedItemsPatient= searchList.getSelectionModel().getSelectedItem();
//
//            }else {
//                Patient newPat= new Patient();
//                newPat.setPat_name(PatientName.getText());
//                newPat.setPat_add(patAdd.getText());
//                newPat.setPat_age(Integer.parseInt(patAge.getText()));
//                if(female.isSelected()) newPat.setPat_gender("f");
//                else if(male.isSelected()) newPat.setPat_gender("m");
//                else newPat.setPat_gender("o");
//                dataSource.addPatient(newPat,DataSource.pharmacist);
//            }
//            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
//            Parent root = FXMLLoader.load(getClass().getResource("FinalBill.fxml"));
//            primaryStage.setTitle("Hello ");
//            primaryStage.setScene(new Scene(root, 750, 600));
//            primaryStage.show();
//        }catch (Exception e){
//            System.out.println("Exception:(generateBill) " + Arrays.toString(e.getStackTrace()));
//        }
//    }
//
}
