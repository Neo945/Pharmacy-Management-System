package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sample.model.DataSource;
import sample.model.MedicineInBill;
import sample.model.Medicines;
import sample.model.Patient;

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
            if(DataSource.MedNameHashMap.isEmpty()) dataSource.createMedicineList();
            DataSource.MedNameHashMap.forEach((k,v)-> {
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
            DataSource.MedNameHashMap.forEach((k,v)-> {
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
            quantity.setText("" + selectedItemsMedicine.getQuantity());
            price.setText(selectedItemsMedicine.getMed_price() + "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onClickProceed(ActionEvent actionEvent){
        try {
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            Medicines selectedItemsMedicine = medicinesListView.getSelectionModel().getSelectedItem();
            if(selectedItemsMedicine!=null){
                DataSource.selectedMedicine = selectedItemsMedicine;
            }else {
                Medicines newMed= new Medicines();
                newMed.setName(med_name.getText());
                newMed.setQuantity(Integer.parseInt(quantity.getText()));
                newMed.setMed_price(Double.parseDouble(quantity.getText()));
                newMed.setCompany(company_name.getText());
                dataSource.addMedicine(newMed);
                DataSource.selectedMedicine = newMed;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
