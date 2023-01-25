package tictactoe;

class Util {
    public static void printField(char[][] field) {
        System.out.println("---------");
        for(int i = 0; i < 3; ++i) {
            System.out.print("| ");
            for(int j = 0; j < 3; ++j) {
                System.out.print(field[i][j]);
                System.out.print(' ');
            }
            System.out.println('|');
        }
        System.out.println("---------");
    }

    public static int checkWin(char[][] field) {//Draw = -1 None = 0 X = 1 O = 2
        int empty = 0;
        boolean x = false;
        boolean o = false;
        for(int i = 0; i < 3; ++i) {
            int xl = 0;
            int ol = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[i][j] == 'X') {
                    xl++;
                } else if (field[i][j] == 'O') {
                    ol++;
                } else {
                    empty++;
                }
            }
            if (xl == 3) {
                x = true;
            } else if (ol == 3) {
                o = true;
            }
            int xc = 0;
            int oc = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[j][i] == 'X') {
                    xc++;
                } else if (field[j][i] == 'O') {
                    oc++;
                }
            }
            if (xc == 3) {
                x = true;
            } else if (oc == 3) {
                o = true;
            }
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
            if (field[0][0] == 'X') {
                x = true;
            } else if (field[0][0] == 'O') {
                o = true;
            }
        }
        if (field[2][0] == field[1][1] && field[1][1] == field[0][2]) {
            if (field[2][0] == 'X') {
                x = true;
            } else if (field[2][0] == 'O') {
                o = true;
            }
        }
        if (x) {
            //System.out.println("X wins");
            return 1;
        } else if (o) {
            //System.out.println("O wins");
            return 2;
        } else if (empty != 0) {
            return 0;
        } else {
            //System.out.println("Draw");
            return -1;
        }
    }
}
