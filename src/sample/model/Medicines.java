package sample.model;

import javafx.collections.ObservableArray;

public class Medicines  {
    public   String name;
    private  String med_id;
    public   double med_price;
    private  String exp_date;
    private  int quantity;
    private  String mfg_date;
    private  String company;
    private int quant;

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public void setMed_price(double med_price) {
        this.med_price = med_price;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMfg_date(String mfg_date) {
        this.mfg_date = mfg_date;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getName() {
        return name;
    }

    public String getMed_id() {
        return med_id;
    }

    public double getMed_price() {
        return med_price;
    }

    public String getExp_date() {
        return exp_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMfg_date() {
        return mfg_date;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
