package com.cbelangerstpierre;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.cbelangerstpierre.container.Door;
import com.cbelangerstpierre.container.Drawer;
import com.cbelangerstpierre.container.Freezer;
import com.cbelangerstpierre.container.Palette;
import com.cbelangerstpierre.services.InteractionService;
import com.cbelangerstpierre.services.SerializeService;

public class FridgeProject {
    public static boolean fridgeOpen = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String SAVED_FRIDGE_PATH = "FridgeInfo.ser";
        Fridge fridge;

        File f = new File(SAVED_FRIDGE_PATH);
        if(f.exists() && !f.isDirectory()) {
            fridge = SerializeService.loadFridge(SAVED_FRIDGE_PATH);
        } else {
            fridge = new Fridge(
                    Arrays.asList(new Palette("First palette"), new Palette("Second palette"), new Palette("Third palette")),
                    Arrays.asList(new Drawer("First drawer"), new Drawer("Second drawer")),
                    new Door("Door"),
                    new Freezer("Freezer")
            );
        }

        InteractionService interactionService = new InteractionService();
        while (fridgeOpen) {
            interactionService.askWhatToDo(fridge, SAVED_FRIDGE_PATH);
        }
    }
}
