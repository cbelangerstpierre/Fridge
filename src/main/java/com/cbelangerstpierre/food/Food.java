package com.cbelangerstpierre.food;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public abstract class Food implements Serializable {
    private final String name;
    private final LocalDate expirationDate;
    private final FoodType type;

    Food(String name, LocalDate expirationDate, FoodType type) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.type = type;
    }

    public boolean isExpired(Food food) {
        return LocalDate.now().isAfter(food.expirationDate);
    }

    public String daysSinceExpired(Food food) {
        Period p = Period.between(food.expirationDate, LocalDate.now());
        String str = "";
        int days = p.getDays();
        int months = p.getMonths();
        int years = p.getYears();
        String dayPlural = days > 1 ? "s" : "";
        String monthPlural = months > 1 ? "s" : "";
        String yearPlural = years > 1 ? "s" : "";
        if (years > 0) {
            String strChanger = ", ";
            if (days == 0 ^ months == 0) {
                strChanger = " and ";
            }
            str += years + " year" + yearPlural + strChanger;
            if (strChanger.equals(" and ")) {
                if (months != 0) {
                    str += months + " month" + monthPlural;
                } else {
                    str += days + " day" + dayPlural;
                }

            } else {
                str += months + " month" + monthPlural + " and " + days + " day" + dayPlural;
            }

        } else {
            if (months > 0 & days > 0) {
                str += months + " month" + monthPlural + " and " + days + " day" + dayPlural;
            } else {
                if (months > 0) {
                    str += months + " month" + monthPlural;
                } else {
                    str += days + " day" + dayPlural;
                }
            }
        }
        return str;
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
