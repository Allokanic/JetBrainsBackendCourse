package banking;

import java.util.*;

public class Main {
    private static DataManager mainDb;
    private static final int BIN = 400000;
    private static final Scanner SC = new Scanner(System.in);
    private static boolean exit = false;
    static String curNumber;

    public static void main(String[] args) {
        System.out.println(args[1]);
        mainDb = new DataManager(args[1]);
        String input;
        do {
            FrontMenuManager.printMainMenu();
            do {
                input = SC.nextLine();
            } while ("".equals(input));
            switch (input) {
                case "0":
                    System.out.println("Bye!");
                    break;
                case "1":
                    createAccount();
                    break;
                case "2":
                    if (FrontMenuManager.logIntoAccount(mainDb)) {
                        actionSubMenu();
                    }
                    break;
            }
        } while (!"0".equals(input) && !exit);
    }

    public static void createAccount() {
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        String cardWithoutChecksum = BIN + Util.generateNumber(9);
        String card = cardWithoutChecksum + Util.getCheckSum(cardWithoutChecksum);
        String pin = Util.generateNumber(4);
        while (mainDb.checkAccount(card, pin)) {
            cardWithoutChecksum = BIN + Util.generateNumber(9);
            card = cardWithoutChecksum + Util.getCheckSum(cardWithoutChecksum);
        }
        System.out.println(card);
        System.out.println("Your card PIN:");
        System.out.println(pin);
        mainDb.addToBase(card, pin);
    }

    public static void actionSubMenu() {
        String input;
        do {
            FrontMenuManager.printSubMenu();
            do {
                input = SC.nextLine();
            } while ("".equals(input));
            switch (input ) {
                case "0":
                    System.out.println("\nBye!");
                    exit = true;
                    break;
                case "1" :
                    System.out.print("Balance: ");
                    System.out.println(mainDb.getBalance(curNumber));
                    break;
                case "5" :
                    System.out.println("\nYou have successfully logged out!");
                    break;
                case "2" :
                    FrontMenuManager.addIncome(mainDb);
                    break;
                case "3":
                    FrontMenuManager.makeTransfer(mainDb);
                    break;
                case "4":
                    mainDb.closeAccount(curNumber);
                    break;
            }
        } while (!"0".equals(input) && !"5".equals(input) && !"4".equals(input));
    }
}