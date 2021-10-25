package com.cbelangerstpierre.container;

import com.cbelangerstpierre.food.Food;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Container implements Serializable {
    private final String name;
    private final ArrayList<Food> content = new ArrayList<>();


    public Container(String name) {
        this.name = name;
    }

    public ArrayList<Food> getContent() {
        return content;
    }

    public void addFood(Food food) {
        content.add(food);
        System.out.println("\n---Food added to the fridge---\n\n\n");
    }

    public void removeFood(Food food) {
        content.remove(food);
        System.out.println("\n---Food removed from the fridge---\n\n\n");
    }

    @Override
    public String toString() {
        return name;
    }
}
