package org.example;

public class Elain {

    protected String mNimi;

    public Elain(String nimi) {
        this.mNimi = nimi;
    }

    public void heraa() {
        System.out.println(mNimi + " herää.");
    }

    public void lepaa() {
        System.out.println(mNimi + " lepää.");
    }

    public void toimi() {
        System.out.println(mNimi + " toimii.");
    }

    public String getNimi() {
        return this.mNimi;
    }

    public void setNimi(String nimi) {
        this.mNimi = nimi;
    }
}
