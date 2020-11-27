package sample.model;


import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class DataSource {
    public static ObservableList<Label> notificationList = FXCollections.observableArrayList();;
    public static Medicines selectedMedicine;
    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;
    public static String pharmacist;
    private static final String register = "INSERT INTO "  + UserData.DB_EMP_NAME + " (" + UserData.DB_EMP_EMP_ID +
                                            "," + UserData.DB_EMP_EMP_NAME + "," + UserData.DB_EMP_EMAIL + "," + UserData.DB_EMP_PASS + "," + UserData.DB_EMP_ADD +
                                            "," + UserData.DB_EMP_ROLE + ") VALUES(?,?,?,?,?,?);";
    private static final String LoginSearchQ = "SELECT " + UserData.DB_EMP_PASS + " FROM " + UserData.DB_EMP_NAME + " WHERE " + UserData.DB_EMP_EMAIL + " =?";
    private static final String medSearch = "SELECT * FROM " + UserData.DB_MED_NAME + " WHERE " + UserData.DB_MED_MED_NAME + " LIKE ?";
    private static final String patSearch = "SELECT * FROM " + UserData.DB_PAT_NAME + " WHERE " + UserData.DB_PAT_PAT_NAME + " LIKE ?";
    private static final String regIntoContact = "INSERT INTO " + UserData.DB_EMP_CONTACT_NAME + " VALUES (?,?);";
    private static final String addMedicine = "INSERT INTO " + UserData.DB_MED_NAME + "(" + UserData.DB_MED_MED_NAME +"," + UserData.DB_MED_MED_ID + ","  + UserData.DB_MED_PRICE + ","
                                            + "," + UserData.DB_MED_MFG_DATE + "," + UserData.DB_MED_EXP_DATE + "," + UserData.DB_MED_QUANTITY + "," + UserData.DB_MED_COM_ID + ") " +
                                                "VALUES(?,?,?,?,?,?);";
    private static final String buyMed = "UPDATE " + UserData.DB_MED_NAME + " SET " + UserData.DB_MED_QUANTITY + " = " + UserData.DB_MED_QUANTITY + " - 1 WHERE "
                                        + UserData.DB_MED_MED_NAME + " = ?";
    public static final String getEmpIdSQL = "Select max(" + UserData.DB_EMP_EMP_ID + ") from "
            + UserData.DB_EMP_NAME + ";";
    public static final String getPriceSQL = "SELECT " + UserData.DB_MED_PRICE + " FROM " + UserData.DB_MED_NAME
                                            + " WHERE " + UserData.DB_MED_MED_NAME + " = ?;";
    public static final String getMedSQL = "SELECT * FROM medicine WHERE med_name = ?;";

    public static Employee loginBoy;
    public static Patient selectedPatient;
    public static ArrayList<Patient> patientArrayList = new ArrayList<>();
    public static HashMap<String,Medicines> MedNameHashMap = new HashMap<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<Bill> bills = new ArrayList<>();
    public static double amount;


    public boolean connectionOpen() {
        try {
            conn = DriverManager.getConnection(UserData.getCONNECTION(),UserData.getUserName(),UserData.getPassword());
            return true;
        }catch (SQLException sqlException){
            System.out.println("Exception: (connectionOpen)" + sqlException);
            return false;
        }
    }//done
    public void connectionClose() {
        try {
            if(conn!=null)
                conn.close();
        }catch (SQLException sqlException){
            System.out.println("Exception: (connectionOpen)" + sqlException);
        }
    }//done



    public void createEmployeeList(){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from employee;");
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setEmp_pass(resultSet.getString("emp_pass"));
                employee.setEmp_role(resultSet.getString("emp_role"));
                employee.setEmp_id(resultSet.getString("emp_id"));
                employee.setEmp_add(resultSet.getString("emp_add"));
                employee.setEmail(resultSet.getString("emp_email"));
                employee.setEmp_name(resultSet.getString("emp_name"));
                employees.add(employee);
            }
        }catch (SQLException e){
            System.out.println("Exception:(createEmployeeList) " + e.getMessage());
        }
    }
    public Employee searchEmp(String email){
        for (Employee e:
             employees) {
            if(e.getEmail().equals(email)) return e;
        }
        return null;
    }

    public void Registration(Employee employee){
        try{
            String emp = getEmpId();
            employee.setEmp_id(emp);
            preparedStatement = conn.prepareStatement(register);
            preparedStatement.setString(1,emp);
            preparedStatement.setString(2,employee.getEmp_name());
            preparedStatement.setString(3,employee.getEmail());
            preparedStatement.setString(4,employee.getEmp_pass());
            preparedStatement.setString(5,employee.getEmp_add());
            preparedStatement.setString(6,employee.getEmp_role());
            preparedStatement.execute();
            addRole(employee.getEmp_role(),emp);
            addContact(employee);
            employees.add(employee);
        }catch (SQLException e){
            System.out.println("Exception: (Registration)" + e);
        }
    }//done
    private void addRole(String role,String emp){
        String role1;
        try{
            if (role.equals("Pharmacist")) {
                role1 = UserData.DB_EMP_PEMP_ID;
            } else {
                role1 = UserData.DB_EMP_CEMP_ID;
            }
            preparedStatement = conn.prepareStatement("INSERT INTO " + role + "(" + role1 + ") values(?);");
            preparedStatement.setString(1,emp);
            preparedStatement.execute();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: (addRole)" + e);
        }
    }//done
    private String getEmpId(){
        try{
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(getEmpIdSQL);
            result.next();
            String emp = result.getString("max(" + UserData.DB_EMP_EMP_ID + ")");
            System.out.println(emp);
            if(emp==null){
                emp = "E0";
            }
            String[] val = emp.split("E");//{0112}
            int emp_id = Integer.parseInt(val[1]);
            emp_id++;
            emp = "E" + emp_id;
            return emp;
        }catch (SQLException e){
            System.out.println("Exception: (getEmpId)"  + e);
        }
        return null;
    }//done
    private void addContact(Employee employee){
        try{
            preparedStatement = conn.prepareStatement(regIntoContact);
            for (int i = 0;i<employee.getContact().size();i++){
                preparedStatement.setString(1,employee.getEmp_id());
                System.out.println(employee.getEmp_id());
                preparedStatement.setString(2,employee.getContact().get(i));
                System.out.println(employee.getContact().get(i));
                preparedStatement.execute();
            }
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: (addContact)" + e);
        }
    }//done




    public boolean loginSearch(String email,String pass){
        try{
            preparedStatement = conn.prepareStatement(LoginSearchQ);
            preparedStatement.setString(1,email);
            ResultSet passSet = preparedStatement.executeQuery();
            passSet.next();
            String checkPass = passSet.getString(UserData.DB_EMP_PASS);
            passSet.close();
            preparedStatement.close();
            return checkPass.equals(pass);
        }catch (SQLException e){
            System.out.println("Exception: (loginSearch)" + e);
        }
        return false;
    }//done


    public void createMedicineList(){
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM " + UserData.DB_MED_NAME + " join company on company.com_id = medicine.com_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Medicines medicines = new Medicines();
                medicines.setName(resultSet.getString(UserData.DB_MED_MED_NAME));
                medicines.setMed_id(resultSet.getString(UserData.DB_MED_MED_ID));
                medicines.setExp_date(resultSet.getString(UserData.DB_MED_EXP_DATE));
                medicines.setMed_price(resultSet.getDouble(UserData.DB_MED_PRICE));
                medicines.setMfg_date(resultSet.getString(UserData.DB_MED_MFG_DATE));
                medicines.setQuantity(resultSet.getInt(UserData.DB_MED_QUANTITY));
                medicines.setCompany(resultSet.getString("comp_name"));
                MedNameHashMap.put(medicines.getName(),medicines);
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }//done



    public Medicines getMed(String name)  {
        try{
            return MedNameHashMap.get(name);
        }catch (Exception e){
            System.out.println("Exception:(getMed) " + e.getMessage());
            return null;
        }

    }//done

    public boolean existsDB(Patient patient){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM patient WHERE pat_id=" + patient.getPat_id());
            resultSet.next();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Exception:(existDB) " + e.getMessage());
            return false;
        }
    }

    public void searchPat(){
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM " + UserData.DB_PAT_NAME  + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Patient patient = new Patient();
                patient.setPat_name(resultSet.getString(UserData.DB_PAT_PAT_NAME));
                patient.setPat_id(resultSet.getString(UserData.DB_PAT_PAT_ID));
                patient.setPat_num(resultSet.getString(UserData.DB_PAT_ADD));
                patient.setPat_age(resultSet.getInt(UserData.DB_PAT_AGE));
                patient.setPemp_id(resultSet.getString(UserData.DB_PAT_PEMP_ID));
                patient.setPat_gender(resultSet.getString(UserData.DB_PAT_GENDER));
                patientArrayList.add(patient);
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }//later

    public void addPatient(Patient newPat, String pharma){
        try {
            statement = conn.createStatement();
            String patId = generatePatID();
            newPat.setPat_id(patId);
            String sql = "Insert into patient(pat_num,pat_name,pat_age,pat_gender,pat_id) values('"
                    + newPat.getPat_num() + "','" + newPat.getPat_name() + "'," + newPat.getPat_age() + ",'" + newPat.getPat_gender()
                    + "','" + patId + "');";
            System.out.println(sql);
            statement.execute(sql);
        }catch (SQLException e){
            System.out.println("Exception:(addPatient) " + e.getMessage());
        }
    }//done
    public String generatePatID(){
        try{
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select max(pat_id) from patient;");
            result.next();
            String patID = result.getString("max(" + UserData.DB_PAT_PAT_ID + ")");
            if(patID==null){
                patID = "P0";
            }
            String[] val = patID.split("P");
            int patIDVAL = Integer.parseInt(val[1]);
            patIDVAL++;
            patID = "P" + patIDVAL;
            return patID;
        }catch (SQLException e){
            System.out.println("Exception: (generatePatId)"  + e);
        }
        return null;
    }

    public void addToBill(){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            statement = conn.createStatement();
            String id = getBillId();
//            selectedPatient.setPat_id();
            String query = "Insert into bill(bill_date,bill_id,pat_id,bill_amount) values('" +
                    dtf.format(now) + "','" + id + "','" + selectedPatient.getPat_id() + "'," + amount + ");";
            System.out.println(query);
            statement.execute(query);
            MedNameHashMap.forEach((k,v)->{
                if(v.getQuantity()>0){
                    try {
                        statement.execute("Insert into med_in_bill values('" + id + "','" + v.getMed_id() +
                                "'," + v.getQuant() + ");");
                        statement.execute("Update medicine set quant_med = quant_med - " + v.getQuant() + " where med_id = '" + v.getMed_id() + "';");
                        v.setQuantity(v.getQuantity()-v.getQuant());
                    } catch (SQLException sqlException) {
                        System.out.println("Exception:(addToBill) " + sqlException.getMessage());
                    }
                }
            });
        }catch (SQLException e){
            System.out.println("Exception:(addToBill) " + e.getMessage());
        }
    }
        private String getBillId(){
            try{
                statement = conn.createStatement();
                ResultSet result = statement.executeQuery("Select max(bill_id) from bill;");
                result.next();
                String emp = result.getString("max(bill_id)");
                System.out.println(emp);
                if(emp==null){
                    emp = "B0";
                }
                String[] val = emp.split("B");//{0112}
                int emp_id = Integer.parseInt(val[1]);
                emp_id++;
                emp = "B" + emp_id;
                return emp;
            }catch (SQLException e){
                System.out.println("Exception: (getEmpId)"  + e);
            }
            return null;
        }

    public void updateValue() {
        try{
            String updateEmpSelectQuery = "Select * from employee where emp_id = '" + loginBoy.getEmp_id() + "';";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(updateEmpSelectQuery);
            resultSet.next();
            if((!resultSet.getString("emp_email").equals(loginBoy.getEmail()))){
                statement.execute("Update employee set emp_email = '" +
                        loginBoy.getEmail() + "' where emp_id = '" + loginBoy.getEmp_id() + "';");
            }
            resultSet = statement.executeQuery(updateEmpSelectQuery);
            resultSet.next();
            if(!(resultSet.getString("emp_add").equals(loginBoy.getEmp_add()))){
                statement.execute("Update employee set emp_add = '" +
                        loginBoy.getEmp_add() + "' where emp_id = '" + loginBoy.getEmp_id() + "';");
            }
            resultSet = statement.executeQuery(updateEmpSelectQuery);
            resultSet.next();
            if(!(resultSet.getString("emp_pass").equals(loginBoy.getEmp_pass()))){
                statement.execute("Update employee set emp_pass = '" +
                        loginBoy.getEmp_pass() + "' where emp_id = '" + loginBoy.getEmp_id() + "';");
            }
            statement.execute("delete from employee_num where emp_id = '" + loginBoy.getEmp_id() + "';");
//            resultSet = statement.executeQuery("Select * from employee where emp_id = '" + loginBoy.getEmp_id() + "';");
//            resultSet.next();
            for (String s :
                    loginBoy.getContact()) {
                statement.execute("insert into employee_num values('" + loginBoy.getEmp_id() + "','" + s + "');");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void generateBillList(){
        try {
            bills.clear();
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from bill join patient on patient.pat_id = bill.pat_id;");
            while (resultSet.next()){
                Bill bill = new Bill();
                bill.setBill_date(resultSet.getDate("bill_date").toString());
                bill.setBill_id(resultSet.getString("bill_id"));
                bill.setPat_name(resultSet.getString("pat_name"));
                bill.setBill_amount(resultSet.getDouble("bill_amount"));
//                resultSet = statement.executeQuery("select * from med_in_bill where bill_id = '" + bill.getBill_id() + "';");
//                while (resultSet.next()){
//                    MedicineInBill medicineInBill = new MedicineInBill();
//                    medicineInBill.setMed_id(resultSet.getString("med_id"));
//                    medicineInBill.setQuant(resultSet.getInt("quantity"));
//                    bill.getMed_id().add(medicineInBill);
//                }
                bills.add(bill);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void addMedicine() {
        try{
            statement = conn.createStatement();
            Medicines addMed = MedNameHashMap.get(selectedMedicine.getName());
            if(addMed==null){
                ResultSet resultSet = statement.executeQuery("select * from company where " +
                        "comp_name like '" + selectedMedicine.getCompany() + "';");
                if(resultSet.next()){
                    selectedMedicine.setCompany(resultSet.getString("com_id"));
                }else {
                    String comp_id = generateCompId();
                    statement.execute("insert into company(com_id,comp_name) " +
                            "values ('" + comp_id + "','" + selectedMedicine.getCompany() + "');");
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                selectedMedicine.setMfg_date(dtf.format(now));
                String[] arrStr = selectedMedicine.getMfg_date().split("-");
                int year = Integer.parseInt(arrStr[0]);
                Random random = new Random();
                int addYear = 1 + random.nextInt(9);
                year+=addYear;
                selectedMedicine.setExp_date(year + "-" + arrStr[1] + "-" + arrStr[2]);
                selectedMedicine.setMed_id(generateMedId());
                MedNameHashMap.put(selectedMedicine.getName(),selectedMedicine);
                statement.execute("insert into medicine(med_name,med_id,med_price,med_expdate,med_mfg,quant_med,com_id) " +
                        "values('" + selectedMedicine.getName() + "','" + selectedMedicine.getMed_id() + "','" + selectedMedicine.getMed_price() + "','" + selectedMedicine.getExp_date()
                + "','" + selectedMedicine.getMfg_date() + "','" + selectedMedicine.getQuantity() + "','" + selectedMedicine.getCompany() + "');");
            }else{
                System.out.println(selectedMedicine.getQuantity());
                System.out.println(selectedMedicine.getMed_id());
                statement.execute("Update medicine set quant_med = " + selectedMedicine.getQuantity()
                        + " where med_id = '" + selectedMedicine.getMed_id() + "';");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String generateMedId(){
        try{
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select max(med_id) from medicine;");
            result.next();
            String emp = result.getString("max(med_id)");
            System.out.println(emp);
            if(emp==null){
                emp = "M0";
            }
            String[] val = emp.split("M");//{0112}
            int emp_id = Integer.parseInt(val[1]);
            emp_id++;
            emp = "M" + emp_id;
            return emp;
        }catch (SQLException e){
            System.out.println("Exception: (getEmpId)"  + e);
        }
        return null;
    }
    private String generateCompId(){
        try{
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select max(com_id) from company;");
            result.next();
            String emp = result.getString("max(com_id)");
            System.out.println(emp);
            if(emp==null){
                emp = "C0";
            }
            String[] val = emp.split("C");//{0112}
            int emp_id = Integer.parseInt(val[1]);
            emp_id++;
            emp = "C" + emp_id;
            return emp;
        }catch (SQLException e){
            System.out.println("Exception: (getEmpId)"  + e);
        }
        return null;
    }
}
