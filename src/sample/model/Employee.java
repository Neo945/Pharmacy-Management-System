package sample.model;

import java.util.ArrayList;

public class Employee {
    private  String emp_id;
    private  String emp_name;
    private  String email;
    private  String emp_add;
    private  String emp_pass;
    private  String emp_role;
    private final ArrayList<String> contact;

    public Employee() {
        this.contact = new ArrayList<>();
    }

    public ArrayList<String> getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.add(contact);
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmp_add() {
        return emp_add;
    }

    public void setEmp_add(String emp_add) {
        this.emp_add = emp_add;
    }

    public String getEmp_pass() {
        return emp_pass;
    }

    public void setEmp_pass(String emp_pass) {
        this.emp_pass = emp_pass;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }
}
