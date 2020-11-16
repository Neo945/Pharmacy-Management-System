package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.model.DataSource;
import sample.model.Medicines;
import sample.model.Patient;

import java.net.URL;
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
    private Patient selectedPat;
    @FXML
    public void initialize() {
        ArrayList<Medicines> medicinesArrayList = DataSource.val;
        double sum = 0;
        for (Medicines m:
             medicinesArrayList) {
            BorderPane borderPane = new BorderPane();
            sum  += Double.parseDouble(m.getMed_price());
            Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
            borderPane.setLeft(new Label(m.getName()));
            borderPane.setRight(new Label(indiaCurrency.getSymbol() + " " + m.getMed_price()));
            medList.getItems().add(borderPane);
        }
        BorderPane borderPane = new BorderPane();
        Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
        borderPane.setLeft(new Label("TOTAL"));
        borderPane.setRight(new Label(indiaCurrency.getSymbol() + " " + sum));
        medList.getItems().add(borderPane);
    }

    public void onSearchClick(ActionEvent actionEvent){
        try{
            String searchString = searchPatient.getText();
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            ArrayList<Patient> patientArrayList = dataSource.searchPat(searchString);
            for (Patient patient : patientArrayList) {
                searchList.getItems().add(patient);
            }
            dataSource.connectionClose();
        }catch (Exception e){
            System.out.println("Exception:(onSearchClick) " + e.getMessage());
        }
    }
    public void fillData(MouseEvent actionEvent){
        try{
            Patient selectedItemsPatient= searchList.getSelectionModel().getSelectedItem();
            PatientName.setText(selectedItemsPatient.getPat_name());
            patAdd.setText(selectedItemsPatient.getPat_add());
            patAge.setText("" + selectedItemsPatient.getPat_age());
            String gender = "" + selectedItemsPatient.getPat_age();
            if(gender.equals("m")){
                male.selectedProperty().setValue(true);
                female.selectedProperty().setValue(false);
                others.selectedProperty().setValue(false);
            }else if(gender.equals("f")){
                male.selectedProperty().set(false);
                female.selectedProperty().set(true);
                others.selectedProperty().set(false);
            }else {
                male.selectedProperty().set(false);
                female.selectedProperty().set(false);
                others.selectedProperty().set(true);
            }
            this.selectedPat = selectedItemsPatient;
        }catch (Exception e){
            System.out.println("Exception:(fillData) " + e.getMessage());
        }
    }
    public void generateBill(){
        try{
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            if(false){

            }else {
                Patient newPat= new Patient();
                newPat.setPat_name(PatientName.getText());
                newPat.setPat_add(patAdd.getText());
                newPat.setPat_age(Integer.parseInt(patAge.getText()));
                if(female.isSelected()) newPat.setPat_gender("f");
                else if(male.isSelected()) newPat.setPat_gender("m");
                else newPat.setPat_gender("o");
                dataSource.addPatient(newPat,DataSource.pharmacist);
            }
        }catch (Exception e){
            System.out.println("Exception:(generateBill) " + Arrays.toString(e.getStackTrace()));
        }
    }

}
