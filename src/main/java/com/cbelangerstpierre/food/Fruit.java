package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Fruit extends Food {
    public Fruit(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Fruit);
    }
}
