package tictactoe.logic;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static tictactoe.logic.GameState.MARK_O;
import static tictactoe.logic.GameState.MARK_X;

public class GameStateTest {

    @Test
    public void testShouldReturnTrueOnAllSquaresMarked() {
        GameState state = new GameState(3);
        state.markSquare(1, MARK_X);
        state.markSquare(2, MARK_O);
        state.markSquare(3, MARK_X);
        state.markSquare(4, MARK_O);
        state.markSquare(5, MARK_X);
        state.markSquare(6, MARK_O);
        state.markSquare(7, MARK_O);
        state.markSquare(8, MARK_X);
        state.markSquare(9, MARK_O);
        assertTrue(state.allSquaresMarked());
        assertEquals(Collections.emptyList(), state.getAvailableMoves());
    }

    @Test
    public void testShouldReturnFalseOnAllSquaresMarked() {
        GameState state = new GameState(3);
        state.markSquare(5, MARK_X);
        assertFalse(state.allSquaresMarked());
    }

    @Test
    public void testShouldNullReturnOnFindWinner() {
        GameState state = new GameState(3);
        state.markSquare(1, MARK_X);
        assertNull(state.findWinner());
    }

    @Test
    public void testShouldReturnXOnFindWinnerDiagonal() {
        GameState state = new GameState(3);
        state.markSquare(1, MARK_X);
        state.markSquare(5, MARK_X);
        state.markSquare(9, MARK_X);
        assertEquals(MARK_X, state.findWinner());
    }

    @Test
    public void testShouldReturnXOnFindWinnerBackwardDiagonal() {
        GameState state = new GameState(3);
        state.markSquare(3, MARK_X);
        state.markSquare(5, MARK_X);
        state.markSquare(7, MARK_X);
        assertEquals(MARK_X, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerHorizontalTop() {
        GameState state = new GameState(3);
        state.markSquare(1, MARK_O);
        state.markSquare(2, MARK_O);
        state.markSquare(3, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerHorizontalMiddle() {
        GameState state = new GameState(3);
        state.markSquare(4, MARK_O);
        state.markSquare(5, MARK_O);
        state.markSquare(6, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerHorizontalBottom() {
        GameState state = new GameState(3);
        state.markSquare(7, MARK_O);
        state.markSquare(8, MARK_O);
        state.markSquare(9, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerVerticalLeft() {
        GameState state = new GameState(3);
        state.markSquare(1, MARK_O);
        state.markSquare(4, MARK_O);
        state.markSquare(7, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerVerticalCenter() {
        GameState state = new GameState(3);
        state.markSquare(2, MARK_O);
        state.markSquare(5, MARK_O);
        state.markSquare(8, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }

    @Test
    public void testShouldReturnOOnFindWinnerVerticalRight() {
        GameState state = new GameState(3);
        state.markSquare(3, MARK_O);
        state.markSquare(6, MARK_O);
        state.markSquare(9, MARK_O);
        assertEquals(MARK_O, state.findWinner());
    }
}
