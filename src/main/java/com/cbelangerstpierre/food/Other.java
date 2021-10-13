package com.cbelangerstpierre.food;

import java.time.LocalDate;

public class Other extends Food {
    public Other(String name, LocalDate expirationDate) {
        super(name, expirationDate, FoodType.Other);
    }
}
