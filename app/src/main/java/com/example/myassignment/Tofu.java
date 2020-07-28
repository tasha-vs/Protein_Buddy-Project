package com.example.myassignment;

public class Tofu extends VegetarianProtein {
    private static double tofuProteinPerGram = 0.08;

    public static double getTofuProteinPerGram() {
        return tofuProteinPerGram;
    }

    public static void setTofuProteinPerGram(double tofuProteinPerGram) {
        Tofu.tofuProteinPerGram = tofuProteinPerGram;
    }

    public Tofu(double weight){
        super(weight);
    }

}
