package com.cbelangerstpierre.container;

import com.cbelangerstpierre.food.Fruit;

import java.util.ArrayList;
import java.util.List;

public class Drawer extends Container {
    private final List<Fruit> content = new ArrayList<>();

    public Drawer(String name) {
        super(name);
    }

    public void add(Fruit fruit) {
        content.add(fruit);
    }
}
