package com.cbelangerstpierre.container;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    public String getContentInfo() {
        StringBuilder str = new StringBuilder();

        if (getContent().size() == 0) {
            str.append("\n\nThere's nothing in the ").append(toString()).append(".");
        } else {
            str.append("\n\nHere's the content of the ").append(toString()).append(":\n");
            for (Food food : getContent()) {
                str.append("\n- ").append(food.getName()).append(" expires the ").append(food.getExpirationDate().format(DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.FULL)));
            }
        }
        return str.append("\n\n").toString();
    }
}
