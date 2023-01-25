package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Player {
    private String difficulty = null;
    private final Scanner SC = new Scanner(System.in);
    private final Random random = new Random();
    private int posX, posY;

    private void fillLine(char[][] field, int index, char type) {
        for(int i = 0; i < 3; ++i) {
            field[index][i] = type;
        }
    }

    private void addToLine(char[][] field, int index, char type) {
        for(int i = 0; i < 3; ++i) {
            if (field[index][i] == ' ') {
                field[index][i] = type;
                break;
            }
        }
    }

    private void addToColumn(char[][] field, int index, char type) {
        for(int i = 0; i < 3; ++i) {
            if (field[i][index] == ' ') {
                field[i][index] = type;
                break;
            }
        }
    }

    private void fillColumn(char[][] field, int index, char type) {
        for(int i = 0; i < 3; ++i) {
            field[i][index] = type;
        }
    }

    public Player(String type) {
        if (!"user".equals(type)) {
            difficulty = type;
        }
    }

    public void makeStep(char[][] field, char type) {
        if (difficulty == null) {
            humanStep(field, type);
        } else {
            if ("easy".equals(difficulty)) {
                System.out.println("Making move level \"easy\"");
                easyAIstep(field, type);
            } else if ("medium".equals(difficulty)) {
                System.out.println("Making move level \"medium\"");
                mediumAIstep(field, type);
            } else {
                System.out.println("Making move level \"hard\"");
                hardAIstep(field, type);
            }
        }
    }

    public void hardAIstep(char[][] field, char type) {
        evaluatePosition(field, type, true, 1);
    }

    public int evaluatePosition(char[][] field, char maximizer, boolean turn, int depth) {
        int checker = Util.checkWin(field);
        if (checker != 0) {
            if (checker == 1 && maximizer == 'X' || checker == 2 && maximizer == 'O') {
                return 20 - depth;
            } else if (checker == -1) {
                return 0;
            } else {
                return depth - 20;
            }
        } else {
            if (turn) {
                int maxEval = Integer.MIN_VALUE;
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j< 3; ++j) {
                        if (field[i][j] == ' ') {
                            char[][] copy = copyArray(field);
                            copy[i][j] = maximizer;
                            int eval = Math.max(evaluatePosition(copy, maximizer, false, depth + 1), maxEval);
                            if (eval > maxEval && depth == 1) {
                                posX = i;
                                posY = j;
                            }
                            maxEval = Math.max(eval, maxEval);
                        }
                    }
                }
                if (depth == 1) {
                    field[posX][posY] = maximizer;
                }
                return maxEval;
            } else {
                int minEval = Integer.MAX_VALUE;
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        if (field[i][j] == ' ') {
                            char[][] copy = copyArray(field);
                            copy[i][j] = maximizer == 'X' ? 'O' : 'X';
                            minEval = Math.min(evaluatePosition(copy, maximizer, true, depth + 1), minEval);
                        }
                    }
                }
                return minEval;
            }
        }
    }

    private char[][] copyArray(char[][] field) {
        char[][] copy = new char[3][3];
        for(int i = 0; i< 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                copy[i][j] = field[i][j];
            }
        }
        return copy;
    }

    public void mediumAIstep(char[][] field, char type) {
        //check opponent
        //check lines
        for(int i = 0; i < 3; ++i) {
            int emptyCount = 0;
            int nonTypeCount = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[i][j] != type && field[i][j] != ' ') {
                    nonTypeCount++;
                } else if (field[i][j] == ' ') {
                    emptyCount++;
                }
            }
            if (nonTypeCount == 2 && emptyCount == 1) {
                addToLine(field, i, type);
                return;
            }
        }
        //check opponent
        //check columns
        for(int i = 0; i < 3; ++i) {
            int emptyCount = 0;
            int nonTypeCount = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[j][i] != type && field[j][i] != ' ') {
                    nonTypeCount++;
                } else if (field[j][i] == ' ') {
                    emptyCount++;
                }
            }
            if (nonTypeCount == 2 && emptyCount == 1) {
                addToColumn(field, i, type);
                return;
            }
        }
        //check opponent
        //check diagonals
        int emptyCount = 0;
        int nonTypeCount = 0;
        for(int i = 0; i < 3; ++i) {
            if (field[i][i] != type && field[i][i] != ' ') {
                nonTypeCount++;
            } else if (field[i][i] == ' ') {
                emptyCount++;
            }
        }
        if (nonTypeCount == 2 && emptyCount == 1) {
            if (field[0][0] == ' ') {
                field[0][0] = type;
            } else if (field[1][1] == ' ') {
                field[1][1] = type;
            } else {
                field[2][2] = type;
            }
            return;
        }
        emptyCount = 0;
        nonTypeCount = 0;
        for(int i = 0; i < 3; ++i) {
            if (field[i][2-i] != type && field[i][2-i] != ' ') {
                nonTypeCount++;
            } else if (field[i][2-i] == ' ') {
                emptyCount++;
            }
        }
        if (nonTypeCount == 2 && emptyCount == 1) {
            if (field[0][2] == ' ') {
                field[0][2] = type;
            } else if (field[1][1] == ' ') {
                field[1][1] = type;
            } else {
                field[2][0] = type;
            }
            return;
        }


        //check my ways
        //check lines
        for(int i = 0; i < 3; ++i) {
            emptyCount = 0;
            int TypeCount = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[i][j] == type) {
                    TypeCount++;
                } else if (field[i][j] == ' ') {
                    emptyCount++;
                }
            }
            if (TypeCount == 2 && emptyCount == 1) {
                fillLine(field, i, type);
                return;
            }
        }
        //check my
        //check columns
        for(int i = 0; i < 3; ++i) {
            emptyCount = 0;
            int TypeCount = 0;
            for(int j = 0; j < 3; ++j) {
                if (field[j][i] == type) {
                    TypeCount++;
                } else if (field[j][i] == ' ') {
                    emptyCount++;
                }
            }
            if (TypeCount == 2 && emptyCount == 1) {
                fillColumn(field, i, type);
                return;
            }
        }
        //check my
        //check diagonals
        emptyCount = 0;
        int TypeCount = 0;
        for(int i = 0; i < 3; ++i) {
            if (field[i][i] == type) {
                TypeCount++;
            } else if (field[i][i] == ' ') {
                emptyCount++;
            }
        }
        if (TypeCount == 2 && emptyCount == 1) {
            field[0][0] = field[1][1] = field[2][2] = type;
            return;
        }
        emptyCount = 0;
        TypeCount = 0;
        for(int i = 0; i < 3; ++i) {
            if (field[i][2-i] == type) {
                TypeCount++;
            } else if (field[i][2-i] == ' ') {
                emptyCount++;
            }
        }
        if (TypeCount == 2 && emptyCount == 1) {
            field[0][2] = field[1][1] = field[2][0] = type;
            return;
        }
        easyAIstep(field, type);
    }

    public void easyAIstep(char[][] field, char type) {
        while (true) {
            int x = random.nextInt(3);
            int y = random.nextInt(3);
            if (field[x][y] == ' ') {
                field[x][y] = type;
                break;
            }
        }
    }

    public void humanStep(char[][] field, char type) {
        System.out.println("Enter the coordinates:");
        int x;
        int y;
        while(true) {
            if (SC.hasNextInt()) {
                x = SC.nextInt();
                if (SC.hasNextInt()) {
                    y = SC.nextInt();
                    if (x < 1 || x > 3 || y < 1 || y > 3) {
                        System.out.println("Coordinates should be from 1 to 3!");
                    } else {
                        if (field[x-1][y-1] == ' ') {
                            field[x-1][y-1] = type;
                            break;
                        } else {
                            System.out.println("This cell is occupied! Choose another one!");
                        }
                    }
                } else {
                    SC.nextLine();
                    System.out.println("You should enter numbers!");
                }
            } else {
                SC.nextLine();
                System.out.println("You should enter numbers!");
            }
        }
    }
}
