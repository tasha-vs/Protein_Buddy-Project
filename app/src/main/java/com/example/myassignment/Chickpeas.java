
package com.example.myassignment;

public class Chickpeas extends VegetarianProtein {

    private static double chickpeasProteinPerGram = 0.19;

    public static double getChickpeasProteinPerGram() {
        return chickpeasProteinPerGram;
    }

    public static void setChickpeasProteinPerGram(double chickpeasProteinPerGram) {
        Chickpeas.chickpeasProteinPerGram = chickpeasProteinPerGram;
    }

    public Chickpeas(double weight) {
        super(weight);
    }
}