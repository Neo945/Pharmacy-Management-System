package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.model.DataSource;

import java.util.Date;

public class FXMLDocumentControllerRegistration {
    public Label label;
    DataSource dataSource;

    public FXMLDocumentControllerRegistration() {
        this.dataSource = new DataSource();
    }

    @FXML
    private Button registerButton;
    @FXML
    private Label userLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label confirmPassLabel;
    @FXML
    private Label passLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label allFieldsAreRequired;


    @FXML
    private TextField name;
    @FXML
    private TextField emailID;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField contact;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField address;

    public void onButtonClicked(ActionEvent e){
        if(checkValues()){
            allFieldsAreRequired.setText("");
            allFieldsAreRequired.setTextFill(Color.WHITE);
            try{
                //get all the values and insert it into the database
                String name = this.name.getText();
                String emailID = this.emailID.getText();
                String add = this.address.getText();
                String password = this.password.getText();
                String confirmPasswordText = this.confirmPassword.getText();
                if(!confirmPasswordText.equals(password)){
                    confirmPassLabel.setText("*Password not matched");
                    confirmPassLabel.setTextFill(Color.RED);
                    return;
                }
                String contact = this.contact.getText();
                String job = this.combobox.getEditor().getText();
//                Date date = new Date("");
                String date = this.datePicker.getEditor().getText();
                DataSource dataSource = new DataSource();
                dataSource.connectionOpen();
                dataSource.Registration(name,add,emailID,password,contact,job);
                dataSource.connectionClose();
            }catch (Exception exception){
                //
            }
        }else {
            allFieldsAreRequired.setText("*All Fields Are Required");
            allFieldsAreRequired.setTextFill(Color.RED);
        }
    }
    public boolean checkValues(){
        boolean flag = true;
        if(name.getText().isEmpty()){//textField
            userLabel.setText("*Required");//label
            userLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            userLabel.setText("");
        }if(emailID.getText().isEmpty()){
            emailLabel.setText("*Required");
            emailLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            emailLabel.setText("");
        }if(contact.getText().isEmpty()){
            contactLabel.setText("*Required");
            contactLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            contactLabel.setText("");
        }if(password.getText().isEmpty()){
            passLabel.setText("*Required");
            passLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            passLabel.setText("");
        }if(confirmPassword.getText().isEmpty()){
            confirmPassLabel.setText("*Required");
            confirmPassLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            confirmPassLabel.setText("");
        }if(combobox.getEditor().getText().isEmpty()){
            jobLabel.setText("*Required");
            jobLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            jobLabel.setText("");
        }if(address.getText().isEmpty()) {
            addressLabel.setText("*Required");
            addressLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            addressLabel.setText("");
        }
        if(datePicker.getEditor().getText().isEmpty()) {
            userLabel.setText("*Required");
            userLabel.setTextFill(Color.RED);
            flag = false;
        }else{
            userLabel.setText("");
        }
        return flag;
    }
}