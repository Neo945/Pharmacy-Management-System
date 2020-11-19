package sample.model;

import java.net.UnknownServiceException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;


public class DataSource {
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
    public static ArrayList<Medicines> val = new ArrayList<>();
    public static ArrayList<Medicines> medicinesArrayList = new ArrayList<>();
    public static HashMap<String,Integer> medicineHashMap = new HashMap<>();

    public boolean connectionOpen() {
        try {
            conn = DriverManager.getConnection(UserData.getCONNECTION(),UserData.getUserName(),UserData.getPassword());
            return true;
        }catch (SQLException sqlException){
            System.out.println("Exception: (connectionOpen)" + sqlException);
            return false;
        }
    }
    public void connectionClose() {
        try {
            if(conn!=null)
                conn.close();
        }catch (SQLException sqlException){
            System.out.println("Exception: (connectionOpen)" + sqlException);
        }
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
        }catch (SQLException e){
            System.out.println("Exception: (Registration)" + e);
        }
    }
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
    }
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
    }
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
    }




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
    }


    public ArrayList<Medicines> searchMed(String medName){
        try{
            if(medName!=null){
                preparedStatement = conn.prepareStatement(medSearch);
                medName = "%" +  medName + "%";
                preparedStatement.setString(1,medName);
            }else{
                preparedStatement = conn.prepareStatement("SELECT * FROM " + UserData.DB_MED_NAME + ";");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Medicines> medicinesArrayList = new ArrayList<>();
            while(resultSet.next()){
                Medicines medicines = new Medicines();
                medicines.setName(resultSet.getString(UserData.DB_MED_MED_NAME));
                medicines.setMed_id(resultSet.getString(UserData.DB_MED_MED_ID));
                medicines.setExp_date(resultSet.getString(UserData.DB_MED_EXP_DATE));
                medicines.setMed_price(resultSet.getString(UserData.DB_MED_PRICE));
                medicines.setMfg_date(resultSet.getString(UserData.DB_MED_MFG_DATE));
                medicines.setQuantity(resultSet.getString(UserData.DB_MED_QUANTITY));
                medicines.setCompany(resultSet.getString(UserData.DB_MED_COM_ID));
                medicinesArrayList.add(medicines);
            }
            resultSet.close();
            preparedStatement.close();
            return medicinesArrayList;
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Patient> searchPat(String patName){
        try{
            preparedStatement = conn.prepareStatement(patSearch);
            patName = "%" +  patName + "%";
            preparedStatement.setString(1,patName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Patient> patientArrayList = new ArrayList<>();
            while(resultSet.next()){
                Patient patient = new Patient();
                patient.setPat_name(resultSet.getString(UserData.DB_PAT_PAT_NAME));
                patient.setPat_id(resultSet.getString(UserData.DB_PAT_PAT_ID));
                patient.setPat_add(resultSet.getString(UserData.DB_PAT_ADD));
                patient.setPat_age(resultSet.getInt(UserData.DB_PAT_AGE));
                patient.setPemp_id(resultSet.getString(UserData.DB_PAT_PEMP_ID));
                patient.setPat_gender(resultSet.getString(UserData.DB_PAT_GENDER));
                patientArrayList.add(patient);
            }
            resultSet.close();
            preparedStatement.close();
            return patientArrayList;
        }catch (SQLException e){
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public int getPrice(String name){
        try{
            preparedStatement = conn.prepareStatement(getPriceSQL);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return  resultSet.getInt("med_price");
        }catch (SQLException e){
            System.out.println("Exception:(getPrice) "  + e);
            return 0;
        }
    }
    public boolean decrementQuant(String name) {
        try{
            preparedStatement = conn.prepareStatement(buyMed);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.out.println("Exception:(decrementQuant) " + e.getMessage());
            return false;
        }
    }

    public Medicines getMed(String name) throws SQLException {
        try{
            preparedStatement = conn.prepareStatement(getMedSQL);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Medicines medicines = new Medicines();
                medicines.setName(resultSet.getString(UserData.DB_MED_MED_NAME));
                medicines.setMed_id(resultSet.getString(UserData.DB_MED_MED_ID));
                medicines.setExp_date(resultSet.getString(UserData.DB_MED_EXP_DATE));
                medicines.setMed_price(resultSet.getString(UserData.DB_MED_PRICE));
                medicines.setMfg_date(resultSet.getString(UserData.DB_MED_MFG_DATE));
                medicines.setQuantity(resultSet.getString(UserData.DB_MED_QUANTITY));
                medicines.setCompany(resultSet.getString(UserData.DB_MED_COM_ID));
                return medicines;
            }else return null;
        }catch (SQLException e){
            System.out.println("Exception:(getMed) " + e.getMessage());
            return null;
        }

    }
//    public void addMedicine(Medicines medicines){
//
//    }
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

    /**
     * create table patient(
     * pat_add varchar(255) not null,
     * pat_name varchar(50),
     * pat_age integer not null,
     * pat_gender char(1) check(pat_gender in('f','m')),
     * pat_id char(6) primary key check(pat_id like 'P%'),
     * pemp_id char(6),
     * foreign key(pemp_id) references pharmacist(pemp_id)
     * );
     * @param newPat
     * @param pharma
     */
    public void addPatient(Patient newPat, String pharma){
        try {
            statement = conn.createStatement();
            String patId = generatePatID(newPat);
            String sql = "Insert into patient(pat_add,pat_name,pat_age,pat_gender,pat_id) values('"
                    + newPat.getPat_add() + "','" + newPat.getPat_name() + "'," + newPat.getPat_age() + ",'" + newPat.getPat_gender()
                    + "','" + patId + "');";
            System.out.println(sql);
            statement.execute(sql);
        }catch (SQLException e){
            System.out.println("Exception:(addPatient) " + e.getMessage());
        }
    }
    public String generatePatID(Patient pat){
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
