package org.example;

public class Kala extends Elain {
    public Kala(String aNimi) {
        super(aNimi);
    }

    // Overwrite toimi
    @Override
    public void toimi() {
        // super.toimi();
        System.out.println(getNimi() + " ui.");
    }

}
