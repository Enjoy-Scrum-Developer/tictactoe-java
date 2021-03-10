package tictactoe.logic;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class RandomPicker {

    private Random random;

    public RandomPicker() {
        this(new Random());
    }

    public RandomPicker(Random random) {
        this.random = random;
    }

    public <T> T pick(T[] array) {
        if (array == null) {
            return null;
        }
        return pick(asList(array));
    }

    public <T> T pick(List<T> list) {
        if (list != null && !list.isEmpty()) {
            int randomIndex = random.nextInt(list.size());
            return list.get(randomIndex);
        }
        return null;
    }

}
