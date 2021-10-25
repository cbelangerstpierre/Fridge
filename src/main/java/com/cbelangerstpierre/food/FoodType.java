package com.cbelangerstpierre.food;

public enum FoodType {
    AnimalProduct("Animal product"),
    Condiment("Condiment"),
    Fruit("Fruit"),
    Grain("Grain"),
    Liquid("Liquid"),
    Vegetable("Vegetable"),
    Other("Other");

    FoodType(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    private final String name;
}
