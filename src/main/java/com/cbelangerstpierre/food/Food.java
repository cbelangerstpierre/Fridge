package com.cbelangerstpierre.food;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Food implements Serializable {
    private final String name;
    private final LocalDate expirationDate;
    private final FoodType type;

    Food(String name, LocalDate expirationDate, FoodType type) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.type = type;
    }

    public static boolean isExpired(LocalDate expirationDate) {
        return LocalDate.now().isAfter(expirationDate);
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public FoodType getType() {
        return type;
    }
}
