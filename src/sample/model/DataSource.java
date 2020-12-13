package sample.model;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;


public class DataSource {

    private static final String getEmpIdSQL = "Select max(" + UserData.DB_EMP_EMP_ID + ") from "
            + UserData.DB_EMP_NAME + ";";
    private static final String regIntoContact = "INSERT INTO " + UserData.DB_EMP_CONTACT_NAME + " VALUES (?,?);";

    private static final String register = "INSERT INTO "  + UserData.DB_EMP_NAME + " (" + UserData.DB_EMP_EMP_ID +
                                            "," + UserData.DB_EMP_EMP_NAME + "," + UserData.DB_EMP_EMAIL + "," + UserData.DB_EMP_PASS + "," + UserData.DB_EMP_ADD +
                                            "," + UserData.DB_EMP_ROLE + ") VALUES(?,?,?,?,?,?);";
//    private  static final String updateEmpSelectQuery = "Select * from " + UserData.DB_EMP_NAME + " where "+ UserData.DB_EMP_EMP_ID
//            + " = ?;";
    private static final String LoginSearchQ = "SELECT " + UserData.DB_EMP_PASS + " FROM " + UserData.DB_EMP_NAME + " WHERE " + UserData.DB_EMP_EMAIL + " =?";
    private static final String selectEmployee = "Select * from employee;";
    private static final String selectMed = "SELECT * FROM " + UserData.DB_MED_NAME + " join company on company.com_id = medicine.com_id;";
    private static final String insertBill = "Insert into bill(bill_date,bill_id,pat_id,bill_amount) values(?,?,?,?);";
    private static final String insertMedInBill = "Insert into med_in_bill values(?,?,?);";
    private static final String decrementQuant = "Update " + UserData.DB_MED_NAME + " set " + UserData.DB_MED_QUANTITY
            + " = " + UserData.DB_MED_QUANTITY + " - ? where " + UserData.DB_MED_MED_ID + " = ?;";
    private static final String insertPatient = "Insert into " + UserData.DB_PAT_NAME + "(" + UserData.DB_PAT_ADD + "," + UserData.DB_PAT_PAT_NAME + "," +
            UserData.DB_PAT_AGE + "," + UserData.DB_PAT_GENDER + "," + UserData.DB_PAT_PAT_ID + ") values(?,?,?,?,?);";
    private static final String insertMed = "insert into " + UserData.DB_MED_NAME + "(" + UserData.DB_MED_MED_NAME +
            ","+ UserData.DB_MED_MED_ID +"," + UserData.DB_MED_PRICE + "," + UserData.DB_MED_EXP_DATE +
            "," + UserData.DB_MED_MFG_DATE + ","  + UserData.DB_MED_QUANTITY + "," + UserData.DB_MED_COM_ID + ") " +
            "values(?,?,?,?,?,?,?);";
    private PreparedStatement preparedStatement;
    private Connection conn;
    private Statement statement;



    public boolean connectionOpen() {
        try {
//            conn = DriverManager.getConnection(UserData.getCONNECTION(),UserData.getUserName(),UserData.getPassword());
            conn = DriverManager.getConnection(UserData.getCONNECTION(),UserData.getUserName(),UserData.getPassword());
            return  true;
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
            ResultSet resultSet = statement.executeQuery(selectEmployee);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setEmp_pass(resultSet.getString(UserData.DB_EMP_PASS));
                employee.setEmp_role(resultSet.getString(UserData.DB_EMP_ROLE));
                employee.setEmp_id(resultSet.getString(UserData.DB_EMP_EMP_ID));
                employee.setEmp_add(resultSet.getString(UserData.DB_EMP_ADD));
                employee.setEmail(resultSet.getString(UserData.DB_EMP_EMAIL));
                employee.setEmp_name(resultSet.getString(UserData.DB_EMP_EMP_NAME));
                insertContact(employee);
                AppData.employees.add(employee);
            }
        }catch (SQLException e){
            System.out.println("Exception:(createEmployeeList) " + e.getMessage());
        }
    }

    private void insertContact(Employee e){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee_num where emp_id ='"  + e.getEmp_id() + "';");
            while (resultSet.next()){
                e.setContact(resultSet.getString("emp_num"));
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public Employee searchEmp(String email){
        for (Employee e:
                AppData.employees) {
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
            System.out.println(preparedStatement);
            addRole(employee.getEmp_role(),emp);
            addContact(employee);
            AppData.employees.add(employee);
        }catch (SQLException e){
            System.out.println("Exception: (Registration)" + e);
        }
    }//done
//    public Patient
    private void addRole(String role,String emp){
        String role1;
        try{
            if (role.equals("Pharmacist")) {
                role1 = UserData.DB_EMP_PEMP_ID;
            } else {
                role1 = UserData.DB_EMP_CEMP_ID;
            }
            String insertRole = "INSERT INTO " + role + "(" + role1 + ") values(?);";
            preparedStatement = conn.prepareStatement(insertRole);
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
            String[] val = emp.split("E");//{0112}//E1//[,1]
            int emp_id = Integer.parseInt(val[1]);
            emp_id++;
            emp = "E" + emp_id;
            return emp;//1:2:3 == ['1','2','3']
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
            preparedStatement = conn.prepareStatement(selectMed);
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
                AppData.MedNameHashMap.put(medicines.getName(),medicines);
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }//done
    public Medicines getMed(String name)  {
        try{
            return AppData.MedNameHashMap.get(name);
        }catch (Exception e){
            System.out.println("Exception:(getMed) " + e.getMessage());
            return null;
        }
    }//done
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
                patient.setPat_gender(resultSet.getString(UserData.DB_PAT_GENDER));
                AppData.patientArrayList.add(patient);
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }//later


    public void addPatient(Patient newPat){
        try {
            statement = conn.createStatement();
            String patId = generatePatID();
            newPat.setPat_id(patId);
            preparedStatement = conn.prepareStatement(insertPatient);
            preparedStatement.setString(1,newPat.getPat_num());
            preparedStatement.setString(2,newPat.getPat_name());
            preparedStatement.setInt(3,newPat.getPat_age());
            preparedStatement.setString(4,newPat.getPat_gender());
            preparedStatement.setString(5,patId);
            System.out.println(preparedStatement.toString());
            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Exception:(addPatient) " + e.getMessage());
        }
    }//done

    public String generatePatID(){
        try{
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select max(" + UserData.DB_PAT_PAT_ID + ") from " + UserData.DB_PAT_NAME + ";");
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
    //            String query = "Insert into bill(bill_date,bill_id,pat_id,bill_amount) values('" +
//                    dtf.format(now) + "','" + id + "','" + AppData.selectedPatient.getPat_id() + "'," + AppData.amount + ");";
//            System.out.println(query);
//            statement.execute(query);
//                        statement.execute("Update medicine set quant_med = quant_med - " + v.getQuant() + " where med_id = '" + v.getMed_id() + "';");
//                        statement.execute("Insert into med_in_bill values('" + id + "','" + v.getMed_id() +
//                                "'," + v.getQuant() + ");");

    public void addToBill(){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            statement = conn.createStatement();
            String id = getBillId();
            preparedStatement = conn.prepareStatement(insertBill);
            preparedStatement.setString(1,dtf.format(now));
            preparedStatement.setString(2,id);
            preparedStatement.setString(3,AppData.selectedPatient.getPat_id());
            preparedStatement.setDouble(4,AppData.amount);
            preparedStatement.execute();
//            for (:) {
//
//            }
            AppData.MedNameHashMap.forEach((k,v)->{
                if(v.getQuant()>0){
                    try {
                        preparedStatement = conn.prepareStatement(insertMedInBill);
                        preparedStatement.setString(1,id);
                        preparedStatement.setString(2,v.getMed_id());
                        preparedStatement.setInt(3,v.getQuant());
                        System.out.println(preparedStatement.toString());
                        preparedStatement.execute();
                        preparedStatement = conn.prepareStatement(decrementQuant);
                        preparedStatement.setInt(1,v.getQuant());
                        preparedStatement.setString(2,v.getMed_id());
                        System.out.println(preparedStatement.toString());
                        preparedStatement.execute();
                        v.setQuantity(v.getQuantity()-v.getQuant());
                    } catch (SQLException sqlException) {
                        System.out.println("Exception:(addToBil  l) " + sqlException.getMessage());
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
            ResultSet result = statement.executeQuery("Select bill_id from bill;");
            ArrayList<Integer> billArray = new ArrayList<>();
            while(result.next()){
                billArray.add(Integer.parseInt(result.getString("bill_id").split("B")[1]));
            }
            if(billArray.isEmpty()){
                return "B1";
            }
            int max = 0;
            for (int b : billArray) if(b>max) max = b;
            return  "B" + ++max;
        }catch (SQLException e){
            System.out.println("Exception: (getEmpId)"  + e);
        }
        return null;
    }
    public void updateValue() {
        try{
            statement = conn.createStatement();
//            preparedStatement.setString(1,AppData.loginBoy.getEmp_id());
            ResultSet resultSet = statement.executeQuery("Select * from " + UserData.DB_EMP_NAME + " where "+ UserData.DB_EMP_EMP_ID
                    + " = '" + AppData.loginBoy.getEmp_id() + "';");
            resultSet.next();
            if((!resultSet.getString(UserData.DB_EMP_EMAIL).equals(AppData.loginBoy.getEmail()))){
                statement.execute("Update " + UserData.DB_EMP_NAME + "  set " + UserData.DB_EMP_EMAIL + "  = '" +
                        AppData.loginBoy.getEmail() + "' where "+ UserData.DB_EMP_EMP_ID +
                        " = '" + AppData.loginBoy.getEmp_id() + "';");
            }
            resultSet = statement.executeQuery("Select * from " + UserData.DB_EMP_NAME + " where "+ UserData.DB_EMP_EMP_ID
                    + " = '" + AppData.loginBoy.getEmp_id() + "';");
            resultSet.next();
            if(!(resultSet.getString(UserData.DB_EMP_ADD).equals(AppData.loginBoy.getEmp_add()))){
                statement.execute("Update " + UserData.DB_EMP_NAME + " set " + UserData.DB_EMP_ADD + " = '" +
                        AppData.loginBoy.getEmp_add() + "' where "+ UserData.DB_EMP_EMP_ID +
                        " = '" + AppData.loginBoy.getEmp_id() + "';");
            }
            resultSet = statement.executeQuery("Select * from " + UserData.DB_EMP_NAME + " where "+ UserData.DB_EMP_EMP_ID
                    + " = '" + AppData.loginBoy.getEmp_id() + "';");
            resultSet.next();
            if(!(resultSet.getString(UserData.DB_EMP_PASS).equals(AppData.loginBoy.getEmp_pass()))){
                statement.execute("Update " + UserData.DB_EMP_NAME + " set " + UserData.DB_EMP_PASS + " = '" +
                        AppData.loginBoy.getEmp_pass() + "' where "+ UserData.DB_EMP_EMP_ID +
                        " = '" + AppData.loginBoy.getEmp_id() + "';");
            }
            statement.execute("delete from employee_num where " + UserData.DB_EMP_EMP_ID
                    + " = '" + AppData.loginBoy.getEmp_id() + "';");
            for (String s :
                    AppData.loginBoy.getContact()) {
                statement.execute("insert into employee_num values('" + AppData.loginBoy.getEmp_id() + "','" + s + "');");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateBillList(){
        try {
            AppData.bills.clear();
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from bill join patient on patient.pat_id = bill.pat_id;");
            while (resultSet.next()){
                Bill bill = new Bill();
                bill.setBill_date(resultSet.getDate("bill_date").toString());
                bill.setBill_id(resultSet.getString("bill_id"));
                bill.setPat_name(resultSet.getString("pat_name"));
                bill.setBill_amount(resultSet.getDouble("bill_amount"));
                System.out.println(bill.getPat_name());
                insertMed(bill);
                AppData.bills.add(bill);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    private void insertMed(Bill bill) throws SQLException {
        statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from med_in_bill " +
                "join medicine on medicine.med_id = med_in_bill.med_id " +
                "where bill_id = '" + bill.getBill_id() + "';");
        while(resultSet.next()){
            Medicines medicines = new Medicines();
            medicines.setName(resultSet.getString(UserData.DB_MED_MED_NAME));
            medicines.setMed_id(resultSet.getString(UserData.DB_MED_MED_ID));
            medicines.setExp_date(resultSet.getString(UserData.DB_MED_EXP_DATE));
            medicines.setMed_price(resultSet.getDouble(UserData.DB_MED_PRICE));
            medicines.setMfg_date(resultSet.getString(UserData.DB_MED_MFG_DATE));
            medicines.setQuant(resultSet.getInt("quantity"));
            System.out.println(medicines.getName());
            bill.getMed_id().add(medicines);
        }
    }

    public void addMedicine() {
        try{
            statement = conn.createStatement();
            String comp_id;
            Medicines addMed = AppData.MedNameHashMap.get(AppData.selectedMedicine.getName());
            if(addMed==null){
                ResultSet resultSet = statement.executeQuery("select * from company where " +
                        "comp_name like '" + AppData.selectedMedicine.getCompany() + "';");
                if(resultSet.next()){
                    comp_id = resultSet.getString("com_id");
                }else {
                    comp_id = generateCompId();
                    statement.execute("insert into company(com_id,comp_name) " +
                            "values ('" + comp_id + "','" + AppData.selectedMedicine.getCompany() + "');");
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                AppData.selectedMedicine.setMfg_date(dtf.format(now));
                String[] arrStr = AppData.selectedMedicine.getMfg_date().split("-");
                int year = Integer.parseInt(arrStr[0]);
                Random random = new Random();
                int addYear = 1 + random.nextInt(9);
                year+=addYear;
                AppData.selectedMedicine.setExp_date(year + "-" + arrStr[1] + "-" + arrStr[2]);
                AppData.selectedMedicine.setMed_id(generateMedId());
                AppData.MedNameHashMap.put(AppData.selectedMedicine.getName(),AppData.selectedMedicine);
                preparedStatement = conn.prepareStatement(insertMed);
                preparedStatement.setString(1,AppData.selectedMedicine.getName());
                preparedStatement.setString(2,AppData.selectedMedicine.getMed_id());
                preparedStatement.setDouble(3,AppData.selectedMedicine.getMed_price());
                preparedStatement.setString(4,AppData.selectedMedicine.getExp_date());
                preparedStatement.setString(5,AppData.selectedMedicine.getMfg_date());
                preparedStatement.setInt(6,AppData.selectedMedicine.getQuantity());
                preparedStatement.setString(7,comp_id);
                preparedStatement.execute();
            }else{
                System.out.println(AppData.selectedMedicine.getQuantity());
                System.out.println(AppData.selectedMedicine.getMed_id());
                statement.execute("Update " + UserData.DB_MED_NAME + " set " + UserData.DB_MED_QUANTITY + " = " + AppData.selectedMedicine.getQuantity()
                        + " where " + UserData.DB_MED_MED_ID + " = '" + AppData.selectedMedicine.getMed_id() + "';");
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

    public String findPatnum(String pat_name) {
        for (Patient p :
                AppData.patientArrayList) {
            if(p.getPat_name().equals(pat_name)) return p.getPat_num();
        }
        return null;
    }
}
