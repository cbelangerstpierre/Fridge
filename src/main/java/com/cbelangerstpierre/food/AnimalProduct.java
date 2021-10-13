package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class AnimalProduct extends Food {
    public AnimalProduct(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.AnimalProduct);
    }
}
