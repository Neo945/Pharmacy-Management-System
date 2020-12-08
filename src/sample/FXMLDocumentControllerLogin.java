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
import sample.model.AppData;
import sample.model.DataSource;

import java.io.IOException;

public class FXMLDocumentControllerLogin {

    @FXML
    private Button login;
    @FXML
    private TextField email_id;
    @FXML
    private PasswordField password;

    @FXML
    private Label email_idLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label allField;

    public void onButtonCLicked(ActionEvent e){
        if(checkValues()){
            allField.setText("");
            allField.setTextFill(Color.WHITE);
            try{
                //get all the values and insert it into the database
                String email_id = this.email_id.getText().toLowerCase().strip();
                String password = this.password.getText().strip();
                DataSource dataSource = new DataSource();
                dataSource.connectionOpen();
                dataSource.createEmployeeList();
                if(dataSource.loginSearch(email_id,password)){
                    //scene change
                    System.out.println("Login Successful");
                    AppData.loginBoy = dataSource.searchEmp(email_id);
                    for (String m :
                            AppData.loginBoy.getContact()) {
                        System.out.println(m);
                    }
                    try{
                        Stage primaryStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
                        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
//                        primaryStage.setTitle("Hello ");
                        primaryStage.setScene(new Scene(root, 750, 600));
                        primaryStage.show();
                    }catch (IOException exception){
                        System.out.println("Exception: (login->homePage)" + exception);
                    }
                }else{
                    allField.setText("INCORRECT PASSWORD OR EMAIL ID");
                    allField.setTextFill(Color.RED);
                }
                dataSource.connectionClose();
            }catch (Exception exception){
                //
            }
        }else {
            allField.setText("*All Fields Are Required");
            allField.setTextFill(Color.RED);
        }
    }
    public boolean checkValues(){
        boolean flag = true;
        if(this.email_id.getText().isEmpty()){//textField
            email_idLabel.setText("*Required");//label
            email_idLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            email_idLabel.setText("");
        }if(this.password.getText().isEmpty()){
            passwordLabel.setText("*Required");
            passwordLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            passwordLabel.setText("");
        }
        return flag;
    }
    public void signup(ActionEvent actionEvent){
        try{
            try{
                Stage primaryStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
                Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
//                primaryStage.setTitle("Hello ");
                primaryStage.setScene(new Scene(root, 750, 600));
                primaryStage.show();
            }catch (IOException exception){
                System.out.println("Exception: (login->homePage)" + exception);
            }
        }catch (Exception e){
            System.out.println("Exception(onLogoutClicked):" + e.getMessage());
        }
    }
}
