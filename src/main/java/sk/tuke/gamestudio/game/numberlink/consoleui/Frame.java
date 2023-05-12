package sk.tuke.gamestudio.game.numberlink.consoleui;

import sk.tuke.gamestudio.game.numberlink.field.Tile;

public class Frame {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30;1m";
    public static final String WHITE = "\u001B[38m";
    public static final String BLUE = "\u001B[96m";
    public static final String YELLOW = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String RED = "\u001B[31m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
//    public static final String CYAN_BR    = "\u001B[36;1m";


//    public static final String BLACK_BCK   = "\u001B[40m";
//    public static final String WHITE_BCK   = "\u001B[47m";
//    public static final String BLUE_BCK    = "\u001B[44m";
//    public static final String YELLOW_BCK  = "\u001B[43m";
//    public static final String GREEN_BCK   = "\u001B[42m";
//    public static final String RED_BCK     = "\u001B[41m";
//    public static final String MAGENTA_BCK = "\u001B[45m";
//    public static final String CYAN_BCK    = "\u001B[46m";
//    public static final String CYAN_BR_BCK    = "\u001B[46;1m";


    public static void drawFrame(int numColumns, int numRows, Tile[][] tiles) {

        // Draws coordinates
        System.out.print("   ");
        for (int i = 0; i < numColumns; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        // Draws top border
        System.out.print("  +");
        for (int i = 0; i < numColumns; i++) {
            System.out.print("---+");
        }
        System.out.println();

        // Draws rows
        for (int j = 0; j < numRows; j++) {
            System.out.print(j + " ");
            for (int i = 0; i < numColumns; i++) {
                System.out.print("|");
                for (int k = 0; k < 3; k++) {
                    if (k == 1 && tiles[j][i].getNumber() != 0) {
                        switch (tiles[j][i].getNumber()) {
                            case 1 -> System.out.print(BLACK + /*BLACK_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 2 -> System.out.print(WHITE + /*WHITE_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 3 -> System.out.print(BLUE + /*BLUE_BCK + */tiles[j][i].getNumber() + ANSI_RESET);
                            case 4 -> System.out.print(YELLOW + /*YELLOW_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 5 -> System.out.print(GREEN + /*GREEN_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 6 -> System.out.print(RED + /*RED_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 7 ->
                                    System.out.print(MAGENTA + /*MAGENTA_BCK +*/ tiles[j][i].getNumber() + ANSI_RESET);
                            case 8 -> System.out.print(CYAN + /*CYAN_BCK + */tiles[j][i].getNumber() + ANSI_RESET);
//                            case 9 -> System.out.print(CYAN_BR + CYAN_BR_BCK + pattern[j][i] + ANSI_RESET);
                            default -> System.out.print(ANSI_RESET + tiles[j][i].getNumber() + ANSI_RESET);
                        }
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("|");

            // Draw separator between rows
            System.out.print("  +");
            for (int i = 0; i < numColumns; i++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }
}
