package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
        primaryStage.setTitle("Pharmacy Management System");
        primaryStage.resizableProperty().set(false);
        primaryStage.setScene(new Scene(root, 750, 600));
        primaryStage.show();
        }catch (Exception e){
            System.out.println("start");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
