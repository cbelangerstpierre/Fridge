package com.cbelangerstpierre.food;

import java.io.Serializable;

public enum FoodType implements Serializable {
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
