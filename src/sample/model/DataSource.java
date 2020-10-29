package sample.model;

import sample.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {
    private Connection conn;
    public boolean connectionOpen() {
        try {
            conn = DriverManager.getConnection(UserData.getCONNECTION(),UserData.getUserName(),UserData.getPassword());
            return true;
        }catch (SQLException sqlException){
            System.out.println("Exception:" + sqlException);
            return false;
        }
    }
    public void connectionClose() {
        try {
            if(conn!=null)
                conn.close();
        }catch (SQLException sqlException){
            System.out.println("Exception:" + sqlException);
        }
    }
}
