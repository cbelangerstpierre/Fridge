package com.cbelangerstpierre.services;

import com.cbelangerstpierre.Fridge;

import java.io.*;

public class SerializeService {
    public static void saveFridge(Fridge fridge, String path) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(fridge);
        out.close();
        fileOut.close();
    }

    public static Fridge loadFridge(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Fridge fridge = (Fridge) in.readObject();
        in.close();
        fileIn.close();
        return fridge;
    }
}
