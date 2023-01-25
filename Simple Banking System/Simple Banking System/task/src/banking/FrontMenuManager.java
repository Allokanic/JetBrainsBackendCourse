package banking;

import javax.xml.crypto.Data;
import java.util.Scanner;

public class FrontMenuManager {

    private static final Scanner SC = new Scanner(System.in);
    public static void printMainMenu() {
        System.out.println("\n1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void printSubMenu() {
        System.out.println("\n1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    public static boolean logIntoAccount(DataManager mainDb) {
        System.out.println("\nEnter your card number:");
        String card = SC.nextLine();
        System.out.println("Enter your PIN:");
        String pin = SC.nextLine();

        if (mainDb.checkAccount(card, pin)) {
            System.out.println("\nYou have successfully logged in!");
            Main.curNumber = card;
            return true;
        } else {
            System.out.println("\nWrong card number or PIN!");
            return false;
        }
    }

    public static void addIncome(DataManager mainDb) {
        System.out.println("\nEnter income");
        int value = SC.nextInt();
        mainDb.addMoney(Main.curNumber, value);
        System.out.println("Income was added!\n");
    }

    public static void makeTransfer(DataManager mainDb) {
        System.out.println("\nTransfer\n" + "Enter card number:");
        String otherNumber;
        do {
            otherNumber =  SC.nextLine();
        } while ("".equals(otherNumber));
        if (Util.getCheckSum(otherNumber.substring(0, 15)).equals(Character.toString(otherNumber.charAt(15)))){
            if (mainDb.checkAccountCard(otherNumber)) {
                int curBalance = mainDb.getBalance(Main.curNumber);
                System.out.println("Enter how much money you want to transfer:");
                int wanted = SC.nextInt();
                if (wanted > curBalance) {
                    System.out.println("Not enough money!");
                } else {
                    System.out.println("Success!\n");
                    mainDb.makeTransfer(Main.curNumber, otherNumber, wanted);
                }
            } else {
                System.out.println("Such a card does not exist.");
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
    }
}
