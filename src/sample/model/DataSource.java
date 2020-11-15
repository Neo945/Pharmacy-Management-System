package sample.model;

import sample.UserData;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class DataSource {
    private Connection conn;
    private final String register = "INSERT INTO employee (emp_id,emp_name, emp_email, emp_pass,emp_add,emp_role) VALUES(";
    private final String LoginSearchQ = "select emp_pass from employee where emp_email ='";

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
    public void Registration(String name,String add,String email,String pass,String contact,String role){
        try{
            Statement regState = conn.createStatement();
            ResultSet result = regState.executeQuery("Select max(emp_id) from employee;");
            result.next();
            String emp = result.getString("max(emp_id)");
            System.out.println(emp);
            if(emp.isEmpty()){
                emp = "E0";
            }
            String[] val = emp.split("E");//{0112}
            int emp_id = Integer.parseInt(val[1]);
            emp_id++;
            emp = "E" + emp_id;
            regState.execute(register + "'" + emp + "','" + name + "','" + email + "','" + pass + "','" + add + "','" + role + "');");
            regState.execute("Insert into employee_num(emp_id,emp_num) values('" + emp + "','"  + contact + "');");
            if(role.equals("Pharmacist")){
                regState.execute("INSERT INTO pharmacist(pemp_id) values('" + emp + " ');");
            }
            else{
                regState.execute("INSERT INTO cashier(cemp_id) values('" + emp + "');");
            }
            regState.close();
        }catch (SQLException e){
            System.out.println("Exception:" + e);
        }
    }
    public boolean loginSearch(String email,String pass){
        try{
            Statement loginState = conn.createStatement();
            ResultSet passSet = loginState.executeQuery(LoginSearchQ + email + "';");
            passSet.next();
            String checkPass = passSet.getString("emp_pass");
            passSet.close();
            loginState.close();
            return checkPass.equals(pass);
        }catch (SQLException e){
            System.out.println("Exception: " + e);
        }
        return false;
    }































//    public void search(String string,String table_name) {
//        PreparedStatement preparedStatement = conn.prepareStatement();
//        preparedStatement.setString(1,"",2,"");
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
//    }

}
