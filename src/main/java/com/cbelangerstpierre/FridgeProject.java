package com.cbelangerstpierre;

import java.util.Arrays;

import com.cbelangerstpierre.container.Door;
import com.cbelangerstpierre.container.Drawer;
import com.cbelangerstpierre.container.Freezer;
import com.cbelangerstpierre.container.Palette;
import com.cbelangerstpierre.services.InteractionService;

public class FridgeProject {
    public static boolean fridgeOpen = true;

    public static void main(String[] args) {

        Fridge fridge = new Fridge(
            Arrays.asList(new Palette("First palette"), new Palette("Second palette"), new Palette("Third palette")),
            Arrays.asList(new Drawer("First drawer"), new Drawer("Second drawer")),
            new Door("Door"),
            new Freezer("Freezer")
        );
        InteractionService interactionService = new InteractionService();
        while (fridgeOpen) {
            interactionService.askWhatToDo(fridge);
        }
    }
}
