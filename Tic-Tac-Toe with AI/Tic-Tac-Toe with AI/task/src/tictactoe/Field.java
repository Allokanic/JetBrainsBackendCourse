package tictactoe;

import java.util.Random;
import java.util.Scanner;

class Field {
    private final char[][] field = new char[3][3];
    private boolean xTurn;
    private boolean oTurn;
    private final Scanner SC = new Scanner(System.in);
    private final Random random = new Random();
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Field(String p1, String p2) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                field[i][j] = ' ';
            }
        }
        xTurn = true;
        oTurn = false;
        firstPlayer = new Player(p1);
        secondPlayer = new Player(p2);
    }

    public void callStepManager() {
        if (xTurn) {
            firstPlayer.makeStep(field, 'X');
            Util.printField(field);
            xTurn = false;
            oTurn = true;
        } else {
            secondPlayer.makeStep(field, 'O');
            Util.printField(field);
            xTurn = true;
            oTurn = false;
        }
    }

    public char[][] getField() {
        return field;
    }

    public void parseLine() {
        System.out.println("Enter the cells:");
        String line = SC.nextLine();
        int r = 0;
        int c = 0;
        int x = 0;
        int o = 0;
        for(int i = 0; i < 9; ++i) {
            field[r][c++] = line.charAt(i) == '_' ? ' ' : line.charAt(i);
            if (c > 2) {
                r++;
                c = 0;
            }
            if (line.charAt(i) == 'X') {
                x++;
            } else if (line.charAt(i) == 'O') {
                o++;
            }
        }
        if (o > x || o == x) {
            xTurn = true;
            oTurn = false;
        } else {
            xTurn = false;
            oTurn = true;
        }
    }
}
