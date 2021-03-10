package tictactoe.helper;

public class ColoredText {

    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String END = "\u001B[0m";

    public static String greenText(String message) {
        return GREEN + message + END;
    }

    public static String yellowText(String message) {
        return YELLOW + message + END;
    }

    public static String blueText(String message) {
        return BLUE + message + END;
    }

}
