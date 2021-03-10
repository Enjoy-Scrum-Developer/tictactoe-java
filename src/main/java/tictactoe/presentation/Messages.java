package tictactoe.presentation;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import static tictactoe.helper.ColoredText.greenText;
import static tictactoe.helper.ColoredText.yellowText;

public class Messages {

    private static PrintStream out = System.out;
    private static Scanner scanner = new Scanner(System.in);

    public static void alertMessage(String message) {
        out.println(yellowText(message));
    }

    public static <T> T prompt(String message, Function<String, T> converterFunction) {
        out.print(greenText(message));
        return converterFunction.apply(askForInput());
    }

    private static String askForInput() {
        String value;
        do {
            try {
                value = scanner.nextLine();
            } catch (NoSuchElementException e) {
                value = "";
            }
        } while (value.isEmpty());
        return value;
    }

    @Override
    protected void finalize() throws Throwable {
        scanner.close();
        out.close();
    }
}
