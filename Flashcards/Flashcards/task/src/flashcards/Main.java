package flashcards;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static FlashRepo rep = new FlashRepo();
    private static Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {
        int index = -1;
        if (args.length == 2) {
            if (args[0].equals("-import")) {
                rep.importt(args[1]);
            } else {
                index = 1;
            }
        } else if (args.length == 4) {
            if (args[0].equals("-import")) {
                rep.importt(args[1]);
                index = 3;
            } else {
                rep.importt(args[3]);
                index = 1;
            }
        }
        boolean exit = false;
        while (!exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            rep.addLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String input = SC.nextLine();
            rep.addLog(input);
            switch (input) {
                case "import":
                    rep.importCards();
                    break;
                case "export":
                    rep.exportCards();
                    break;
                case "add":
                    rep.addCard();
                    break;
                case "remove":
                    rep.removeCard();
                    break;
                case "ask":
                    rep.ask();
                    break;
                case "log":
                    rep.exportLog();
                    break;
                case "reset stats":
                    rep.resetStats();
                    break;
                case "hardest card":
                    rep.printHardestCards();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    exit = true;
                    if (index != -1) {
                        rep.export(args[index]);
                    }
                    break;
            }
        }
    }
}
