package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Employee;

import java.io.IOException;
import java.util.Arrays;

public class FXMLDocumentControllerRegistration {
    public Label label;
    private DataSource dataSource;

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
    @FXML
    private Hyperlink login;

    public void onButtonClicked(ActionEvent e){
        if(checkValues()){
            allFieldsAreRequired.setText("");
            allFieldsAreRequired.setTextFill(Color.WHITE);
            try{
                //get all the values and insert it into the database
                Employee employee = new Employee();
                employee.setEmp_name(this.name.getText());
                employee.setEmail(this.emailID.getText().toLowerCase().strip());
                employee.setEmp_add(this.address.getText().toLowerCase().strip());
                String password = this.password.getText().strip();
                if(!password.equals(this.confirmPassword.getText().strip())){
                    confirmPassLabel.setText("*Password not matched");
                    confirmPassLabel.setTextFill(Color.RED);
                    return;
                }
                employee.setEmp_pass(password);
//                employee.getContact().add(this.contact.getText());
                String[] contArray = this.contact.getText().split(";");
//                employee.getContact().addAll(Arrays.asList(contArray));
                for (String s : contArray) {
                    employee.setContact(s);
                    System.out.println(s);
                }
                employee.setEmp_role(this.combobox.getEditor().getText().strip());
//                Date date = new Date("");
                String date = this.datePicker.getEditor().getText().strip();
                DataSource dataSource = new DataSource();
                dataSource.connectionOpen();
                dataSource.Registration(employee);
                dataSource.connectionClose();
                toLoginPage(e);
            }catch (Exception exception){
                System.out.println("Exception: (onButtonClicked)" + exception);
                System.out.println("Exception: (onButtonClicked)" + Arrays.toString(exception.getStackTrace()));
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
    public void toLoginPage(ActionEvent e) {
        try{
            Stage primaryStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//            primaryStage.setTitle("Hello ");
            primaryStage.setScene(new Scene(root, 750, 600));
            primaryStage.show();
        }catch (IOException exception){
            System.out.println("Exception: (toLoginPage)" + exception);
        }
    }
}