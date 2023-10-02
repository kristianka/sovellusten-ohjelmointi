package org.example;

import java.util.ArrayList;

// No need to import Note since they are in same
public class Main {
    public static void main(String[] args) {
        Note note1 = new Note();
        Note note2 = new Note("Muista Android- -tunnit", Color.BLUE);

        note1.printNote();
        note2.printNote();

        // Tehtävä 2
        ArrayList<Elain> elaimet = new ArrayList<>();
        elaimet.add(new Elain("Kissa"));
        elaimet.add(new Lintu("Haukka"));
        elaimet.add(new Kala("Kilpikonna"));

        for (Elain elain : elaimet) {
            elain.heraa();
            elain.toimi();
            elain.lepaa();
        }
    }
}