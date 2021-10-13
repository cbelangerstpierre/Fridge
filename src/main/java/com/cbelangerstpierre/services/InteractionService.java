package com.cbelangerstpierre.services;

import com.cbelangerstpierre.Fridge;
import com.cbelangerstpierre.container.Container;
import com.cbelangerstpierre.food.*;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class InteractionService {
    private final Scanner scanner = new Scanner(System.in);

    public void addFoodTo(Fridge fridge) {
        String foodName = askFoodName();
        FoodType foodType = askFoodType();
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

    private FoodType askFoodType() {
        System.out.println("\n\nWhich type of food is it? (Please enter the integer corresponding)\n");

        for (int i = 0; i < FoodType.values().length; i++)
            System.out.printf("%d - %s\n", i + 1, FoodType.values()[i]);
        System.out.print("\nYour answer : ");
        FoodType ft = FoodType.values()[validOption(1, FoodType.values().length) - 1];

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

        System.out.println("The expiration date is the " + LocalDate.parse(foodExpirationDate).format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(userLocale)) + ".\n\n");
        return LocalDate.parse(foodExpirationDate);
    }

    public void askWhatToDo(Fridge fridge) {
        System.out.println("What do you want to do? (Please enter the integer corresponding)\n");
        System.out.println("""
                0 - Quit the program
                1 - Add a food in the fridge
                2 - See the content of the fridge
                """);
        System.out.print("Your answer : ");

        switch (validOption(0, 2)) {
            case 0 -> {
                System.out.println("\nFridge closed, see you next time.\n");
                System.exit(0);
            }
            case 1 -> {
                System.out.println("\nPerfect, we will add a food in the fridge\n\n");
                addFoodTo(fridge);
            }
            case 2 -> seeFridgeContent(fridge);
        }
    }

    public void seeFridgeContent(Fridge fridge) {
        ArrayList<Container> containers = new ArrayList<>(0);

        containers.addAll(fridge.getPalettes());

        containers.addAll(fridge.getDrawers());

        containers.add(fridge.getDoor());
        containers.add(fridge.getFreezer());

        System.out.println("Which type of content do you want to see? (Please enter the integer corresponding)\n");
        System.out.println("1 - See content by food type");

        for (int i = 0; i < containers.size(); i++)
            System.out.printf("%d - See content of the %s\n", i + 2, containers.get(i).toString().toLowerCase());

        System.out.print("Your answer : ");
//TODO
//        switch (validOption(1, 2)) {
//            case 1 -> askFoodContent();
//            case 2 -> askContainerContent();
//        }
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
