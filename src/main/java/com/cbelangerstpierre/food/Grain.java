package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Grain extends Food {
    public Grain(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Grain);
    }
}
