package sample.model;

import java.util.ArrayList;

public class Bill {
    private String bill_id;
    private ArrayList<Medicines> med_id = new ArrayList<>();
    private String bill_date;
    private double bill_amount;
    private String pat_name;
    private String status = "Paid";

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public ArrayList<Medicines> getMed_id() {
        return med_id;
    }

    public void setMed_id(ArrayList<Medicines> med_id) {
        this.med_id = med_id;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bil_date) {
        this.bill_date = bil_date;
    }

    public double getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(double bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getPat_name() {
        return pat_name;
    }

    public void setPat_name(String pat_name) {
        this.pat_name = pat_name;
    }

    public String getStatus() {
        return status;
    }
}
