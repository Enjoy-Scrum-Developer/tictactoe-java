package tictactoe.logic;

import tictactoe.presentation.Board;

import static tictactoe.presentation.Messages.prompt;

public class Game {

    private static final int BOARD_SIZE = 3;
    private static final int AI_LEVEL = 2;

    public void run() {
        GameState gameState = new GameState(BOARD_SIZE);
        Character winner;
        while (true) {
            askPlayerToMove(gameState);
            winner = gameState.findWinner();
            if (winner != null || gameState.allSquaresMarked()) {
                break;
            }
            askComputerToMove(gameState);
            winner = gameState.findWinner();
            if (winner != null || gameState.allSquaresMarked()) {
                break;
            }
        }
    }

    private void askPlayerToMove(GameState gameState) {
        int upperBound = gameState.getSize() * gameState.getSize();
        Integer move = prompt("Enter a number (from 1 to " + upperBound + "): ", Integer::valueOf);
        gameState.markSquare(move, GameState.MARK_X);
        showBoard(gameState);
    }

    private void askComputerToMove(GameState gameState) {
        Ai ai = new Ai(gameState);
        Integer move = ai.nextMove(AI_LEVEL);
        gameState.markSquare(move, GameState.MARK_O);
        showBoard(gameState);
    }

    private void showBoard(GameState gameState) {
        Board board = new Board(gameState);
        board.show();
    }
}
