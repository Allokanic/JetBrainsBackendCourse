package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        while (true) {
            printMainMenu();
            int a = Integer.parseInt(sc.nextLine());
            switch (a) {
                case 1:
                    subMenu();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    public static void subMenu() {
        while (true) {
            printSubMenu();
            int a = Integer.parseInt(sc.nextLine());
            switch (a) {
                case 1:
                    chooseCompanyMenu();
                    break;
                case 2:
                    DBManager.addCompany();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    public static void chooseCompanyMenu() {
        ArrayList<String> list = DBManager.getAllDataFromCompany();
        if (list.size() == 0) {
            System.out.println("The company list is empty!\n");
            return;
        }
        System.out.println("Choose a company:");
        for (String elem : list) {
            System.out.println(elem);
        }
        System.out.println("0. Back\n");
        int a = Integer.parseInt(sc.nextLine());
        if (a == 0) {
            return;
        } else {
            System.out.println(DBRep.getCompanyById(a) + " company");
            companyMenu(a);
        }
    }

    public static void companyMenu(int pos) {
        while (true) {
            printCompanyMenu();
            int a = Integer.parseInt(sc.nextLine());
            switch (a) {
                case 0:
                    return;
                case 1:
                    printCars(pos);
                    break;
                case 2:
                    DBManager.addCar(pos);
                    break;
            }
        }
    }

    public static void printCars(int pos) {
        ArrayList<String> cars = DBManager.getCarList(pos);
        if (cars.size() == 0) {
            System.out.println("The car list is empty!\n");
            return;
        }
        System.out.println("Car list:");
        for (String elem : cars) {
            System.out.println(elem);
        }
        System.out.println();
    }

    public static void printMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit\n");
    }

    public static void printSubMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back\n");
    }

    public static void printCompanyMenu() {
        System.out.println(
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back\n");
    }

}
