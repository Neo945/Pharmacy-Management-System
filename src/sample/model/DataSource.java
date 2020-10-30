package sample.model;

import sample.UserData;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


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
    public void search(String string) {
//        PreparedStatement preparedStatement = conn.prepareStatement();
//        preparedStatement.setString(1,"Thriller");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        try{
//            List<> songList = new LinkedList<>();
//            while(resultSet.next()){
//                artist.setID(resultSet.getInt(DB_TABLE_INDEX_ARTISTS_ID));
//                artist.setName(resultSet.getString());
//                List.add();
//            }
//            return songList;
//        }catch (SQLException e){
//            System.out.println("Exception: " + e.getMessage());
//            System.out.println("Stack: " + Arrays.toString(e.getStackTrace()));
//            return null;
//        }
    }
    //queries

}
