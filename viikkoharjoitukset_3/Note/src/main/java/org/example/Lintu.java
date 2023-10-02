package org.example;

public class Lintu extends Elain {
    public Lintu(String aNimi) {
        super(aNimi);
    }

    // Overwrite toimi
    @Override
    public void toimi() {
        // super.toimi();
        System.out.println(getNimi() + " lentää.");
    }
}
