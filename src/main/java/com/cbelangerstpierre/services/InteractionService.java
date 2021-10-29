package com.cbelangerstpierre.services;

import com.cbelangerstpierre.Fridge;
import com.cbelangerstpierre.FridgeProject;
import com.cbelangerstpierre.container.*;
import com.cbelangerstpierre.food.AnimalProduct;
import com.cbelangerstpierre.food.Condiment;
import com.cbelangerstpierre.food.Food;
import com.cbelangerstpierre.food.FoodType;
import com.cbelangerstpierre.food.Fruit;
import com.cbelangerstpierre.food.Grain;
import com.cbelangerstpierre.food.Liquid;
import com.cbelangerstpierre.food.Other;
import com.cbelangerstpierre.food.Vegetable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;


public class InteractionService {
    private final Scanner scanner = new Scanner(System.in);
    String SAVED_FOOD_TYPE_DATA_PATH = "foodTypeData.properties";
    HashMap<String, FoodType> foodTypeDataBase;
    private final File f = new File(SAVED_FOOD_TYPE_DATA_PATH);
    
    public InteractionService() throws IOException, ClassNotFoundException {
        if (f.exists() && !f.isDirectory()) {
            foodTypeDataBase = SerializeService.loadFoodTypeDataBase(SAVED_FOOD_TYPE_DATA_PATH);
        } else {
            foodTypeDataBase = new HashMap<>();
        }
    }

    public void addFoodTo(Fridge fridge) {
        String foodName = askFoodName();
        FoodType foodType = askFoodType(foodName.toLowerCase());
        foodTypeDataBase.put(foodName.toLowerCase(), foodType);
        LocalDate foodExpirationDate = askFoodExpirationDate();
        Food food = createFood(foodType, foodExpirationDate, foodName);

        Container container = askContainer(fridge);

        container.addFood(food);
    }

    private Container askContainer(Fridge fridge) {
        ArrayList<Container> containers = new ArrayList<>(0);
        System.out.println("Which container do you want to put it in? (Please enter the integer corresponding)\n");

        containers.addAll(fridge.getPalettes());

        containers.addAll(fridge.getDrawers());
        
        containers.add(fridge.getDoor());
        containers.add(fridge.getFreezer());
        
        for (int i = 0; i < containers.size(); i++)
            System.out.printf("%d - %s\n", i + 1, containers.get(i));

        System.out.print("\nYour answer : ");

        Container c = containers.get(validOption(1, containers.size()) - 1);
        String str;
        if (c.equals(containers.get(0)) | c.equals(containers.get(1)) | c.equals(containers.get(2))) {
            str = "on";
        } else {
            str = "in";
        }
        System.out.printf("\nYour food will be %s the " + c.toString().toLowerCase() + ".\n\n", str);

        return c;
    }

    private FoodType askFoodType(String foodName) {
        FoodType ft;

        if (!foodTypeDataBase.containsKey(foodName)) {
            System.out.println("\n\nWhich type of food is it? (Please enter the integer corresponding)\n");

            for (int i = 0; i < FoodType.values().length; i++)
                System.out.printf("%d - %s\n", i + 1, FoodType.values()[i]);
            System.out.print("\nYour answer : ");
            ft = FoodType.values()[validOption(1, FoodType.values().length) - 1];

            String str;
            String str2 = "";
            if (ft.equals(FoodType.Other) | ft.equals(FoodType.AnimalProduct)) {
                str = "an";
                if (ft.equals(FoodType.Other)) {
                    str2 = " type";
                }
            } else {
                str = "a";
            }
            System.out.printf("\nYour food is %s " + ft.toString().toLowerCase() + "%s.\n\n\n", str, str2);
        } else {
            ft = foodTypeDataBase.get(foodName);
            System.out.println();
        }

        return ft;
    }

    private String askFoodName() {
        System.out.print("Please enter the name of the food : ");
        String foodName = scanner.next();
        foodName = foodName.substring(0, 1).toUpperCase() + foodName.substring(1);
        return foodName;
    }

    private LocalDate askFoodExpirationDate() {
        Locale userLocale = Locale.getDefault(Locale.Category.FORMAT);
        String dateFormat = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                FormatStyle.SHORT, null, IsoChronology.INSTANCE, userLocale);
        DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(userLocale);
        LocalDate exampleDate = LocalDate.now();
        System.out.print("Please enter the expiration date in format " + dateFormat
                + ", for example " + exampleDate.format(dateFormatter) + " : ");

        String foodExpirationDate;

        LocalDate validDate = null;
        do {
            foodExpirationDate = scanner.next();
            try {
                validDate = LocalDate.parse(foodExpirationDate, dateFormatter);
            } catch (DateTimeParseException exception) {
                System.out.println(foodExpirationDate + " is not in the correct format, please try again");
            }
        } while (validDate == null);

        System.out.println("The expiration date is the " + validDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(userLocale)) + ".\n\n");
        return validDate;
    }

    public void askWhatToDo(Fridge fridge, String SAVED_FRIDGE_PATH) throws IOException {
        System.out.println("What do you want to do? (Please enter the integer corresponding)\n");
        System.out.println("""
                0 - Close the fridge
                1 - Add a food in the fridge
                2 - Remove or see the content of the fridge
                """);
        System.out.print("Your answer : ");

        switch (validOption(0, 2)) {
            case 0 -> {
                System.out.println("\nFridge closed, see you next time.\n");
                SerializeService.saveFridge(fridge, SAVED_FRIDGE_PATH);
                SerializeService.saveFoodTypeDataBase(foodTypeDataBase, SAVED_FOOD_TYPE_DATA_PATH);
                FridgeProject.fridgeOpen = false;
            }
            case 1 -> {
                System.out.println("\nPerfect, we will add a food in the fridge\n\n");
                addFoodTo(fridge);
            }
            case 2 -> seeFridgeContentContainerType(fridge);
        }
    }

    public void seeFridgeContentContainerType(Fridge fridge) {
        final ArrayList<Container> containers = fridge.getContainers();

        System.out.println("\n\nWhich type of content do you want to see? (Please enter the integer corresponding)\n");
        System.out.println("1 - See content by the type of food");

        for (int i = 0; i < containers.size(); i++)
            System.out.printf("%d - See content of the %s\n", i + 2, containers.get(i).toString().toLowerCase());

        System.out.print("\nYour answer : ");

        int input = validOption(1, containers.size() + 1);

        if (input == 1) {
            seeFridgeContentFoodType(fridge);
        } else {
            // Print container content
            StringBuilder ContainerTypeContentOutput = new StringBuilder();
            Container selectedContainer = containers.get(input - 2);

            if (selectedContainer.getContent().size() == 0) {
                ContainerTypeContentOutput.append("\n\nThere's nothing ");
                if (input == 2 | input == 3 | input == 4) {
                    ContainerTypeContentOutput.append("on");
                } else {
                    ContainerTypeContentOutput.append("in");
                }
                ContainerTypeContentOutput.append(" the ")
                        .append(selectedContainer.toString().toLowerCase())
                        .append(".");
            } else {
                ContainerTypeContentOutput.append("\n\nHere's the content of the ").append(selectedContainer).append(":\n");
                int i = 1;
                for (Food food : selectedContainer.getContent()) {
                    ContainerTypeContentOutput.append(String.format("\n%s - ", i))
                        .append(food.getName())
                        .append(" of type '")
                        .append(food.getType())
                        .append("'");
                    if (food.isExpired(food)) {
                        ContainerTypeContentOutput.append(String.format(" that has been expired for %s.", food.daysSinceExpired(food)));
                    } else {
                        ContainerTypeContentOutput.append(" that expires the ")
                                .append(String.format("%s.", food.getExpirationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))));
                    }
                    i++;
                }

                ContainerTypeContentOutput.append("\n\nIf you want to remove a food, please enter the integer corresponding, else, press 0.\n\n");
                ContainerTypeContentOutput.append("Your answer : ");
            }
            System.out.print(ContainerTypeContentOutput);
            if (selectedContainer.getContent().size() != 0) {
                input = validOption(0, selectedContainer.getContent().size());
                if (input != 0) {
                    selectedContainer.removeFood(selectedContainer.getContent().get(input - 1));
                }
            }
            System.out.print("\n\n");
        }
    }

    private void seeFridgeContentFoodType(Fridge fridge) {
        ArrayList<FoodType> foods = new ArrayList<>(0);

        foods.addAll(Arrays.asList(FoodType.values()));

        System.out.println("\n\nWhich type of content do you want to see? (Please enter the integer corresponding)\n");
        System.out.println("1 - See content by the type of container");

        String str = "every";

        for (int i = 0; i < foods.size(); i++){
            if (foods.get(i).toString().equalsIgnoreCase("others")) {str = "";}
            System.out.printf("%d - See %s %s\n", i + 2, str, foods.get(i).toString().toLowerCase());
        }

        System.out.print("\nYour answer : ");

        int input = validOption(1, foods.size() + 1);

        if (input == 1) {
            seeFridgeContentContainerType(fridge);
        } else {
            StringBuilder foodTypeContentOutput = new StringBuilder();
            foodTypeContentOutput.append("\n\n");
            FoodType searchedFoodType = foods.get(input - 2);

            int foodCounter = 0;

            HashMap<Integer, Container> containerCorrespondingToFoodMap = new HashMap<>();
            HashMap<Integer, Food> foodToRemoveMap = new HashMap<>();

            for (Container c : fridge.getContainers()) {
                StringBuilder outputString = new StringBuilder();
                String preposition = "in";
                if (c.equals(fridge.getPalettes().get(0)) | c.equals(fridge.getPalettes().get(1)) | c.equals(fridge.getPalettes().get(2)))
                    preposition = "on";
                outputString.append(String.format("\n\nHere's every %s %s the %s : \n", searchedFoodType.toString().toLowerCase(), preposition, c.toString().toLowerCase()));
                boolean foodTypeFound = false;
                // Print container content by food type

                for(Food food : c.getContent()) {
                    if (food.getType() == searchedFoodType) {
                        foodCounter++;
                        containerCorrespondingToFoodMap.put(foodCounter, c);
                        foodToRemoveMap.put(foodCounter, food);
                        outputString.append(String.format("%s - The %s that ", foodCounter, food.getName().toLowerCase()));
                        if (food.isExpired(food)) {
                            outputString.append(String.format("has been expired for %s.\n", food.daysSinceExpired(food)));
                        } else {
                            outputString.append(String.format("expires the %s.\n", food.getExpirationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))));
                        }
                        foodTypeFound = true;
                    }
                }

                if (foodTypeFound) {
                    foodTypeContentOutput.append(outputString);
                } else {
                    foodTypeContentOutput.append(String.format("\n\nThere's no %s %s the %s.", searchedFoodType.toString().toLowerCase(), preposition, c.toString().toLowerCase()));
                }
            }

            if (foodCounter != 0) {
                foodTypeContentOutput.append("\n\n\nIf you want to remove a food, please enter the integer corresponding, else, press 0.\n\n");
                foodTypeContentOutput.append("Your answer : ");
                System.out.print(foodTypeContentOutput);
                input = validOption(0, foodCounter);
                if (input != 0) {
                    Container container = containerCorrespondingToFoodMap.get(input);
                    container.removeFood(foodToRemoveMap.get(input));
                }
            } else {
                System.out.print(foodTypeContentOutput);
            }
            System.out.println("\n\n");
        }
    }

    private Food createFood(FoodType type, LocalDate expirationDate, String name) {
        Food food;
        
        switch (type)
        {
            case AnimalProduct -> food = new AnimalProduct(name, expirationDate);
            case Condiment -> food = new Condiment(name, expirationDate);
            case Fruit -> food = new Fruit(name, expirationDate);
            case Grain -> food = new Grain(name, expirationDate);
            case Liquid -> food = new Liquid(name, expirationDate);
            case Vegetable -> food = new Vegetable(name, expirationDate);
            case Other -> food = new Other(name, expirationDate);
            default -> throw new NullPointerException();
        }

        return food;
    }

    private int validOption(int lowestOption, int highestOption) {
        boolean validOptionEntered = false;

        ArrayList<Integer> intList = new ArrayList<>();
        for (int i = lowestOption; i <= highestOption; i++) {
            intList.add(i);
        }

        int option = lowestOption - 1;
        do {
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();

                if (intList.contains(option)) {
                    validOptionEntered = true;
                } else {
                    System.out.print("Enter a valid input : ");
                }

            } else {
                scanner.next();
                System.out.print("Enter a valid input : ");
            }
        } while(!validOptionEntered);

        return option;
    }
}
