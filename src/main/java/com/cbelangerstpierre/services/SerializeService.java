package com.cbelangerstpierre.services;

import com.cbelangerstpierre.Fridge;
import com.cbelangerstpierre.food.FoodType;

import java.io.*;
import java.util.HashMap;

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

    public static void saveFoodTypeDataBase(HashMap<String, FoodType> foodTypeDataBase, String SAVED_FOOD_TYPE_DATA_PATH) {
        try {
            ObjectOutputStream os = new ObjectOutputStream (new FileOutputStream (SAVED_FOOD_TYPE_DATA_PATH));
            os.writeObject(foodTypeDataBase);
            os.close();
        }
        catch (Exception e) {
            System.out.println ("Error in saving model " + SAVED_FOOD_TYPE_DATA_PATH + ": " + e);
            e.printStackTrace();
        }
    }

    public static HashMap<String, FoodType> loadFoodTypeDataBase(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        HashMap<String, FoodType> foodTypeDataBase = (HashMap<String, FoodType>) in.readObject();
        in.close();
        fileIn.close();
        return foodTypeDataBase;
    }
}
