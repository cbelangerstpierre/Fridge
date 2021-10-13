package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Condiment extends Food {
    public Condiment(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Condiment);
    }
}
