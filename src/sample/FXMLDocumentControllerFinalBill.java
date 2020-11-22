package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Medicines;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FXMLDocumentControllerFinalBill {
    @FXML
    private TableView<Medicines> medicinesTableView;
    @FXML
    private TableColumn<Medicines,String> name;
    @FXML
    private TableColumn<Medicines,Double> med_price;
    @FXML
    private TableColumn<Medicines,Integer> quant;
    @FXML
    private ImageView tick;
//    private final
    @FXML
    private Button save;
    @FXML
    private Button print;
    @FXML
    private Label price;
    public void initialize() {
//        ArrayList<Medicines> medicinesArrayList = new ArrayList<>();
        ObservableList<Medicines> list = FXCollections.observableArrayList();
        DataSource.MedNameHashMap.forEach((k,v)-> {
            if(v.getQuant()>0) {
                list.add(v);
            }
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        med_price.setCellValueFactory(new PropertyValueFactory<>("med_price"));
        quant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        medicinesTableView.setItems(list);
        Currency indiaCurrency = Currency.getInstance(new Locale("en","IN"));
        price.setText("Total - " + indiaCurrency.getSymbol() + " " + (DataSource.amount));
        price.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

//        medicinesTableView.getColumns().addAll(name,med_price);
    }
//    public onSave
    public void onSaveClicked(ActionEvent actionEvent){
        try {
            tick.setVisible(true);
//            fitHeight="38.0" fitWidth="42.0" layoutX="705.0" layoutY="527.0"
//            ImageView tick = new ImageView();
//            tick.setImage(new Image(new FileInputStream("C:\\Users\\91937\\IntelliJIDEAProjects\\PharmManagementSystem\\src\\Images\\tick-icon.png")));
//            tick.setFitHeight(38.0);
//            tick.setFitWidth(42.0);
//            tick.setLayoutX(705.0);
//            tick.setLayoutY(527.0);
            DataSource dataSource = new DataSource();
            dataSource.connectionOpen();
            dataSource.addToBill();
            dataSource.connectionClose();
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Saved Successfully");
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("transaction.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Exception:(onSaveClicked) " + e.getMessage());
        }
    }
    public void onBackClicked(ActionEvent actionEvent){
        try{
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Add Patient.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException e){
            System.out.println("Exception:(onBackClicked) " + e.getMessage());
        }
    }
}
