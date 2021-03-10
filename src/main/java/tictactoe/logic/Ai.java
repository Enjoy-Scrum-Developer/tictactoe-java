package tictactoe.logic;

import tictactoe.logic.SquareIterators.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static tictactoe.logic.GameState.*;
import static tictactoe.logic.SquareIterators.*;

public class Ai {

    private final GameState gameState;
    private final Character aiSymbol;
    private final Character playerSymbol;
    private final RandomPicker randomPicker;

    public Ai(GameState gameState) {
        this(gameState, new RandomPicker(), MARK_O, MARK_X);
    }

    public Ai(GameState gameState, RandomPicker randomPicker) {
        this(gameState, randomPicker, MARK_O, MARK_X);
    }

    public Ai(GameState state, RandomPicker randomPicker, Character aiSymbol, Character playerSymbol) {
        this.gameState = state;
        this.randomPicker = randomPicker;
        this.aiSymbol = aiSymbol;
        this.playerSymbol = playerSymbol;
    }

    public Integer nextMove(int aiLevel) {
        Integer winningMove = findWinningMove(gameState, aiSymbol);
        if (winningMove != null) {
            return winningMove;
        }
        if (aiLevel == 0) {
            return getRandomMove(gameState.getAvailableMoves());
        }
        winningMove = findWinningMove(gameState, playerSymbol);
        if (winningMove != null) {
            return winningMove;
        }
        if (aiLevel == 1) {
            return getRandomMove(gameState.getAvailableMoves());
        }
        return findBestMove(gameState);
    }

    private Integer findBestMove(GameState gameState) {
        List<Integer> squareScores = new SquareScoresBuilder(new GameDetails(gameState, aiSymbol, playerSymbol)).build();
        final Integer maxScore = squareScores.stream().reduce(0, (result, current) -> {
            if (current > result) {
                return current;
            }
            return result;
        });
        List<Integer> bestMoves = IntStream.range(0, squareScores.size())
                .boxed()
                .map(index -> new IndexValue<>(index, squareScores.get(index)))
                .filter(indexValue -> maxScore.equals(indexValue.getValue()))
                .map(indexValue -> indexValue.getIndex() + 1)
                .collect(toList());
        if (bestMoves.size() > 1) {
            return getRandomMove(bestMoves);
        }
        return bestMoves.get(0);
    }

    private Integer findWinningMove(GameState state, Character symbol) {
        for (int no : state.getAvailableMoves()) {
            try {
                GameState stateCopy = (GameState) state.clone();
                if (stateCopy.markSquare(no, symbol)) {
                    if (symbol.equals(stateCopy.findWinner())) {
                        return no;
                    }
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Clone feature is not supported", e);
            }
        }
        return null;
    }

    private Integer getRandomMove(List<Integer> bestMoves) {
        return randomPicker.pick(bestMoves);
    }

    private static class SquareScoresBuilder {

        private final Character aiSymbol;
        private final Character playerSymbol;
        private final GameState state;
        private final int size;
        private final Integer[] scores;

        public SquareScoresBuilder(GameDetails game) {
            aiSymbol = game.getAiSymbol();
            playerSymbol = game.getPlayerSymbol();
            state = game.getGameState();
            size = state.getSize();
            scores = IntStream.range(0, size * size)
                    .mapToObj(value -> 0)
                    .toArray(Integer[]::new);
        }

        public List<Integer> build() {
            iterateAndComputeScores(diagonalIterator());
            iterateAndComputeScores(backwardDiagonalIterator());
            for (int i = 0; i < size; i++) {
                iterateAndComputeScores(horizontalIterator(i));
                iterateAndComputeScores(verticalIterator(i));
            }
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] == null) {
                    scores[i] = 0;
                }
            }
            return Arrays.asList(scores);
        }

        private void iterateAndComputeScores(SquareIterator squareIterator) {
            Counter playerMarks = new Counter();
            Character[] squares = state.getSquares().toArray(new Character[0]);
            squareIterator.iterate(squares, size, entry -> {
                if (!BLANK.equals(entry.getValue())) {
                    scores[entry.getIndex()] = null;
                }
                if (playerSymbol.equals(entry.getValue())) {
                    playerMarks.increment();
                }
            });
            if (playerMarks.getValue() == 0) {
                squareIterator.iterate(squares, size, entry -> {
                    if (scores[entry.getIndex()] != null) {
                        scores[entry.getIndex()] += 1;
                    }
                });
            }
        }
    }

    private static class Counter {

        private int value = 0;

        public void increment() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }

    private static class GameDetails {

        private final GameState gameState;
        private final Character aiSymbol;
        private final Character playerSymbol;

        public GameDetails(GameState gameState, Character aiSymbol, Character playerSymbol) {
            this.gameState = gameState;
            this.aiSymbol = aiSymbol;
            this.playerSymbol = playerSymbol;
        }

        public GameState getGameState() {
            return gameState;
        }

        public Character getAiSymbol() {
            return aiSymbol;
        }

        public Character getPlayerSymbol() {
            return playerSymbol;
        }
    }
}
