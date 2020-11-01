package registration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.model.DataSource;

public class FXMLDocumentController {
    DataSource dataSource;

    public FXMLDocumentController() {
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
        boolean flag = checkValues();
        if(flag){
            allFieldsAreRequired.setText("");
            allFieldsAreRequired.setTextFill(Color.WHITE);
            try{
                //get all the values and insert it into the database
                System.out.println();
            }catch (Exception exception){
                //
            }
        }else {
            allFieldsAreRequired.setText("*All Fields Are Required");
            allFieldsAreRequired.setTextFill(Color.RED);
        }
    }
    public boolean checkValues(){
        if(name.getText().isEmpty()){//textField
            userLabel.setText("*Required");//label
            userLabel.setTextFill(Color.RED);
            return false;
        }else{
            userLabel.setText("");
        }if(emailID.getText().isEmpty()){
            emailLabel.setText("*Required");
            emailLabel.setTextFill(Color.RED);
            return false;
        }else{
            emailLabel.setText("");
        }if(contact.getText().isEmpty()){
            contactLabel.setText("*Required");
            contactLabel.setTextFill(Color.RED);
            return false;
        }else{
            contactLabel.setText("");
        }if(password.getText().isEmpty()){
            passLabel.setText("*Required");
            passLabel.setTextFill(Color.RED);
            return false;
        }else{
            passLabel.setText("");
        }if(confirmPassword.getText().isEmpty()){
            confirmPassLabel.setText("*Required");
            confirmPassLabel.setTextFill(Color.RED);
            return false;
        }else{
            confirmPassLabel.setText("");
        }if(combobox.getEditor().getText().isEmpty()){
            jobLabel.setText("*Required");
            jobLabel.setTextFill(Color.RED);
            return false;
        }else{
            jobLabel.setText("");
        }if(address.getText().isEmpty()) {
            addressLabel.setText("*Required");
            addressLabel.setTextFill(Color.RED);
            return false;
        }else{
            addressLabel.setText("");
        }
        if(datePicker.getEditor().getText().isEmpty()) {
            userLabel.setText("*Required");
            userLabel.setTextFill(Color.RED);
            return false;
        }else{
            userLabel.setText("");
        }
        return true;
    }
}