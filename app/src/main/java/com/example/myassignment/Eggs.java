package com.example.myassignment;

public class Eggs extends VegetarianProtein {
    private static int eggProteinPerEgg = 6;

    public static int getEggProteinPerEgg() {
        return eggProteinPerEgg;
    }

    public static void setEggProteinPerEgg(int eggProteinPerEgg) {
        Eggs.eggProteinPerEgg = eggProteinPerEgg;
    }

    public Eggs(double weight){
        super(weight);
    }
}
