package sample;

public class UserData {
    public static final String DBname = "Pharmacy";
    private static final String UserName = "root";
    private static final String Password = "Shree$h2";
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
