package tictactoe.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static tictactoe.logic.GameState.MARK_O;
import static tictactoe.logic.GameState.MARK_X;

public class AiTest {

    private final RandomPicker randomPicker = Mockito.mock(RandomPicker.class);

    @Before
    public void setup() {
        when(randomPicker.pick(anyList())).thenReturn(7);
    }

    @Test
    public void testShouldReturnRandomMoveAtLevel0() {
        GameState state = new GameState(3);
        Ai ai = new Ai(state, randomPicker);
        state.markSquare(1, MARK_X);
        state.markSquare(2, MARK_O);
        state.markSquare(3, MARK_X);
        Integer move = ai.nextMove(0);
        assertEquals(7, move.intValue());
    }

    @Test
    public void testShouldReturnWinningMoveAtLevel0() {
        GameState state = new GameState(3);
        Ai ai = new Ai(state, randomPicker);
        state.markSquare(5, MARK_X);
        state.markSquare(2, MARK_O);
        state.markSquare(1, MARK_X);
        state.markSquare(9, MARK_O);
        state.markSquare(3, MARK_X);
        state.markSquare(8, MARK_O);
        state.markSquare(3, MARK_X);
        Integer move = ai.nextMove(0);
        assertEquals(7, move.intValue());
    }

    @Test
    public void testShouldReturnBestMoveAtLevel2() {
        GameState state = new GameState(3);
        Ai ai = new Ai(state, randomPicker);
        Integer move = ai.nextMove(2);
        assertEquals(5, move.intValue());
    }

    @Test
    public void testShouldReturnBestMoveAtLevel2MidGame() {
        GameState state = new GameState(3);
        Ai ai = new Ai(state, randomPicker);
        state.markSquare(5, MARK_X);
        state.markSquare(1, MARK_O);
        Integer move = ai.nextMove(2);
        assertEquals(7, move.intValue());
    }
}
