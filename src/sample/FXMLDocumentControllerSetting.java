package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.DataSource;

import java.io.IOException;

public class FXMLDocumentControllerSetting {
    @FXML
    private Label name;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private TextField number;
    @FXML
    private Button back;
    @FXML
    private Button save;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label confirmPassLabel;
    public void onSaveClicked(ActionEvent actionEvent){
        if(!email.getText().isEmpty())DataSource.loginBoy.setEmail(email.getText().toLowerCase().strip());
        if(!address.getText().isEmpty())DataSource.loginBoy.setEmp_add(address.getText().toLowerCase().strip());
        if(!number.getText().isEmpty())DataSource.loginBoy.setContact(number.getText().strip());
        if(!password.getText().strip().isEmpty()){
            if(!password.getText().equals(this.confirmPassword.getText().strip())){
                confirmPassLabel.setText("*Password not matched");
                confirmPassLabel.setTextFill(Color.RED);
                return;
            }
            DataSource.loginBoy.setEmp_pass(password.getText().strip());
        }
        System.out.println(DataSource.loginBoy.getEmp_add());
        System.out.println(DataSource.loginBoy.getEmail());
        DataSource dataSource = new DataSource();
        dataSource.connectionOpen();
        dataSource.updateValue();
        dataSource.connectionClose();
        System.out.println("Changes saved");
        try {
            Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
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
