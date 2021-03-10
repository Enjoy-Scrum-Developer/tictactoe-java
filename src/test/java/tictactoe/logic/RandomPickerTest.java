package tictactoe.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class RandomPickerTest {

    @Mock
    private Random random;

    @InjectMocks
    private RandomPicker randomPicker;

    @Before
    public void setup() {
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(4);
    }

    @Test
    public void testShouldReturnNullOnNullArray() {
        assertNull(randomPicker.pick((Integer[]) null));
    }

    @Test
    public void testShouldReturnNullOnEmptyArray() {
        assertNull(randomPicker.pick(new Integer[0]));
    }

    @Test
    public void testShouldReturnNullOnNullList() {
        assertNull(randomPicker.pick((List<Integer>) null));
    }

    @Test
    public void testShouldReturnNullOnEmptyList() {
        assertNull(randomPicker.pick(Collections.emptyList()));
    }

    @Test
    public void testShouldReturnRandomItemFromGivenArray() {
        Integer result = randomPicker.pick(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        Assert.assertEquals(5, result.intValue());
    }

}
