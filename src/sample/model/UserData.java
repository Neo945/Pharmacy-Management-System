package sample.model;

//import java.security.PublicKey;

public class UserData {

    public static final String DB_EMP_NAME = "employee";
    public static final String DB_EMP_EMP_ID = "emp_id";
    public static final String DB_EMP_EMP_NAME = "emp_name";
    public static final String DB_EMP_EMAIL = "emp_email";
    public static final String DB_EMP_ADD = "emp_add";
    public static final String DB_EMP_PASS = "emp_pass";
    public static final String DB_EMP_ROLE = "emp_role";
    public static final String DB_EMP_PEMP_ID = "pemp_id";
    public static final String DB_EMP_CEMP_ID = "cemp_id";

    public static final String DB_MED_NAME = "medicine";
    public static final String DB_MED_MED_NAME = "med_name";
    public static final String DB_MED_MED_ID = "med_id";
    public static final String DB_MED_PRICE = "med_price";
    public static final String DB_MED_EXP_DATE = "med_expdate";
    public static final String DB_MED_QUANTITY = "quant_med";
    public static final String DB_MED_MFG_DATE = "med_mfg";
    public static final String DB_MED_COM_ID = "com_id";

    public static final String DB_PAT_NAME = "patient";
    public static final String DB_PAT_PAT_ID = "pat_id";
    public static final String DB_PAT_PAT_NAME = "pat_name";
    public static final String DB_PAT_ADD = "pat_num";
    public static final String DB_PAT_AGE = "pat_age";
    public static final String DB_PAT_GENDER = "pat_gender";
    public static final String DB_PAT_PEMP_ID = "pemp_id";

    public static final String DB_EMP_CONTACT_NAME  = "employee_num";
    public static final String DB_EMP_CONTACT_EMP_ID = "emp_id";
    public static final String DB_EMP_CONTACT_CONTACT = "emp_num";

//    public static final String

    private static final String DBname = "Pharmacy";
    private static final String UserName = "root";
    private static final String Password = "";
    private static final String CONNECTION = "jdbc:mysql://localhost/" + DBname;

    public static String getUserName() {
        return UserName;
    }

    public static String getPassword() {
        return Password;
    }

    public static String getCONNECTION() {
        return CONNECTION;
    }

}
