package com.cbelangerstpierre;

import java.util.List;

import com.cbelangerstpierre.container.Door;
import com.cbelangerstpierre.container.Drawer;
import com.cbelangerstpierre.container.Freezer;
import com.cbelangerstpierre.container.Palette;


public class Fridge {
    private final List<Palette> palettes;
    private final List<Drawer> drawers;
    private final Door door;
    private final Freezer freezer;

    public Fridge(List<Palette> palettes, List<Drawer> drawers, Door door, Freezer freezer) {
        this.palettes = palettes;
        this.drawers = drawers;
        this.door = door;
        this.freezer = freezer;
    }

    public List<Palette> getPalettes() {
        return this.palettes;
    }

    public List<Drawer> getDrawers() {
        return this.drawers;
    }
    public Door getDoor() {
        return this.door;
    }

    public Freezer getFreezer() {
        return this.freezer;
    }
}