package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Liquid extends Food {
    public Liquid(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Liquid);
    }
}
