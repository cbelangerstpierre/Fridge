package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Vegetable extends Food {
    public Vegetable(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Vegetable);
    }
}
