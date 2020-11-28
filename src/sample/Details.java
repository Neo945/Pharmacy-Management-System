package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.AppData;
import sample.model.DataSource;

public class Details {
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label number;
    @FXML
    private Button back;
    public void initialize(){
        try{
            name.setText(AppData.loginBoy.getEmp_name());
            email.setText(AppData.loginBoy.getEmail());
            address.setText(AppData.loginBoy.getEmp_add());
            number.setText(AppData.loginBoy.getContact().get(0));
        }catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
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
