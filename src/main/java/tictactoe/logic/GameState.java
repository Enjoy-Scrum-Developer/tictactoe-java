package tictactoe.logic;

import tictactoe.logic.SquareIterators.SquareIterator;

import java.util.*;
import java.util.stream.IntStream;

import static tictactoe.logic.SquareIterators.backwardDiagonalIterator;
import static tictactoe.logic.SquareIterators.diagonalIterator;

public class GameState implements Cloneable {

    public static final Character MARK_X = 'X';
    public static final Character MARK_O = 'O';
    public static final Character BLANK = ' ';

    private final int size;
    private final Character[] squares;
    private boolean started = false;

    public GameState(int size) {
        this.size = size;
        squares = new Character[size * size];
        Arrays.fill(squares, BLANK);
    }

    public GameState(int size, Character[] squares, boolean started) {
        this.size = size;
        this.squares = squares;
        this.started = started;
    }

    public boolean markSquare(int no, Character symbol) {
        started = true;
        int index = no - 1;
        if (squares[index].equals(BLANK)) {
            squares[index] = symbol;
            return true;
        }
        return false;
    }

    public boolean allSquaresMarked() {
        for (Character symbol : squares) {
            if (symbol.equals(BLANK)) {
                return false;
            }
        }
        return true;
    }

    public Character getMark(int no) {
        return squares[no - 1];
    }

    public int getSize() {
        return size;
    }

    public List<Character> getSquares() {
        return Arrays.asList(squares);
    }

    public List<Integer> getAvailableMoves() {
        List<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].equals(BLANK)) {
                availableMoves.add(i + 1);
            }
        }
        return availableMoves;
    }

    public Character findWinner() {
        return new WinnerFinder(squares, size).find();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new GameState(size, Arrays.copyOf(squares, squares.length), started);
    }

    private static class WinnerFinder {

        private final Character[] squares;
        private final int size;

        public WinnerFinder(Character[] squares, int size) {
            this.squares = squares;
            this.size = size;
        }

        public Character find() {
            return findWinnerByIterator(diagonalIterator())
                    .orElseGet(() -> findWinnerByIterator(backwardDiagonalIterator())
                            .orElseGet(() -> findWinnerOnEachRow()
                                    .orElseGet(() -> findWinnerOnEachColumn().orElse(null))));
        }

        private Optional<Character> findWinnerOnEachColumn() {
            return IntStream.range(0, size)
                    .mapToObj(SquareIterators::verticalIterator)
                    .map(squareIterator -> findWinnerByIterator(squareIterator).orElse(null))
                    .filter(Objects::nonNull)
                    .findFirst();
        }

        private Optional<Character> findWinnerOnEachRow() {
            return IntStream.range(0, size)
                    .mapToObj(SquareIterators::horizontalIterator)
                    .map(squareIterator -> findWinnerByIterator(squareIterator).orElse(null))
                    .filter(Objects::nonNull)
                    .findFirst();
        }

        private Optional<Character> findWinnerByIterator(SquareIterator squareIterator) {
            Map<Character, Integer> initialValue = new HashMap<>();
            return squareIterator.iterateAndReduce(squares, size, initialValue, (tally, entry) -> {
                Map<Character, Integer> updatedTally = new HashMap<>(tally);
                Character symbol = entry.getValue();
                int currentCount = updatedTally.getOrDefault(symbol, 0);
                updatedTally.put(symbol, currentCount + 1);
                return updatedTally;
            }, (result, partial) -> {
                result.putAll(partial);
                return result;
            }).entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(BLANK))
                    .filter(entry -> entry.getValue().equals(size))
                    .map(Map.Entry::getKey)
                    .findFirst();
        }
    }
}
