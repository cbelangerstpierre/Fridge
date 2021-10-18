package com.cbelangerstpierre.container;

import java.util.ArrayList;

import com.cbelangerstpierre.food.Food;


public abstract class Container {
    private String name;

    public ArrayList<Food> getContent() {
        return content;
    }

    private ArrayList<Food> content = new ArrayList<>();

    public Container(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addFood(Food food) {
        content.add(food);
        System.out.println("\n---Food added to the fridge---\n\n\n");
    }

    @Override
    public String toString() {
        return name;
    }
}
