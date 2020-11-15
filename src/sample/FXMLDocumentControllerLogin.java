package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sample.model.DataSource;

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

    public void onButtonCLicked(){
        if(checkValues()){
            allField.setText("");
            allField.setTextFill(Color.WHITE);
            try{
                //get all the values and insert it into the database
                String email_id = this.email_id.getText();
                String password = this.password.getText();
                DataSource dataSource = new DataSource();
                dataSource.connectionOpen();
                if(dataSource.loginSearch(email_id,password)){
                    //scene change
                    System.out.println("Login Successful");
                }else{
                    allField.setText("INCORRECT PASSWORD");
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
}
