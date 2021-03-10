package tictactoe.presentation;

import tictactoe.logic.GameState;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private static PrintStream out = System.out;

    private final GameState state;

    public Board(GameState state) {
        this.state = state;
    }

    public void show() {
        String border = joinAndEnclose(IntStream.range(0, state.getSize())
                .mapToObj(value -> "---").collect(Collectors.toList()), "-");
        out.println(border);
        List<Character> squareSymbols = state.getSquares();
        int size = state.getSize();
        for (int i = 0; i < state.getSize(); i++) {
            List<String> stringifiedSymbols = squareSymbols.subList(i * size, (i + 1) * size).stream()
                    .map(character -> String.valueOf(character))
                    .collect(Collectors.toList());
            String row = joinAndEnclose(stringifiedSymbols, " | ");
            out.println(row.trim());
            out.println(border);
        }
        out.println();
    }

    private <T extends CharSequence> String joinAndEnclose(Iterable<T> values, String separator) {
        return separator + String.join(separator, values) + separator;
    }
}
