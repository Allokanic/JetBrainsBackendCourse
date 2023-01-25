package tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String mainLine;
        while(true) {
            System.out.println("Input command:");
            mainLine = sc.nextLine();
            String[] params = mainLine.split(" ");
            if ("exit".equals(params[0])) {
                break;
            } else {
                if (params.length == 3) {
                    play(params[1], params[2]);
                } else {
                    System.out.println("Bad parameters!");
                }
            }
        }
    }

    public static void play(String t1, String t2) {
        Field mainField = new Field(t1, t2);
        Util.printField(mainField.getField());

       while(Util.checkWin(mainField.getField()) == 0) {
            mainField.callStepManager();
       }

       int result = Util.checkWin(mainField.getField());
        if (result == 1) {
            System.out.println("X wins");
        } else if (result == 2) {
            System.out.println("O wins");
        } else {
            System.out.println("Draw");
        }

    }
}

