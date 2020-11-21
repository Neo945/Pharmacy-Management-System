package sample.model;

public class Patient {
    private  String pat_id;
    private  String pat_name;
    private  String pat_num;
    private int pat_age;
    private  String pat_gender;
    private  String pemp_id;

    public String getPat_id() {
        return pat_id;
    }

    @Override
    public String toString() {
        return pat_name;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }

    public String getPat_name() {
        return pat_name;
    }

    public void setPat_name(String pat_name) {
        this.pat_name = pat_name;
    }

    public String getPat_num() {
        return pat_num;
    }

    public void setPat_num(String pat_num) {
        this.pat_num = pat_num;
    }

    public int getPat_age() {
        return pat_age;
    }

    public void setPat_age(int pat_age) {
        this.pat_age = pat_age;
    }

    public String getPat_gender() {
        return pat_gender;
    }

    public void setPat_gender(String pat_gender) {
        this.pat_gender = pat_gender;
    }

    public String getPemp_id() {
        return pemp_id;
    }

    public void setPemp_id(String pemp_id) {
        this.pemp_id = pemp_id;
    }
}
