package tictactoe.logic;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SquareIterators {

    private static final IndexIteratorBuilder diagonalIndexIteratorBuilder = size -> row -> row * size + row;
    private static final IndexIteratorBuilder backwardDiagonalIndexIteratorBuilder = size -> row -> (row + 1) * (size - 1);
    private static final DynamicIndexIteratorBuilder horizontalIndexIteratorBuilder = row -> size -> (column -> row * size + column);
    private static final DynamicIndexIteratorBuilder verticalIndexIteratorBuilder = column -> size -> (row -> row * size + column);

    public static SquareIterator diagonalIterator() {
        return new BaseSquareIterator(diagonalIndexIteratorBuilder);
    }

    public static SquareIterator backwardDiagonalIterator() {
        return new BaseSquareIterator(backwardDiagonalIndexIteratorBuilder);
    }

    public static SquareIterator horizontalIterator(int row) {
        return new DynamicSquareIterator(row, horizontalIndexIteratorBuilder);
    }

    public static SquareIterator verticalIterator(int column) {
        return new DynamicSquareIterator(column, verticalIndexIteratorBuilder);
    }

    private static class DynamicSquareIterator extends BaseSquareIterator {

        public DynamicSquareIterator(int rowOrColumn, DynamicIndexIteratorBuilder dynamicIndexIteratorBuilder) {
            super(dynamicIndexIteratorBuilder.build(rowOrColumn));
        }

    }

    private static class BaseSquareIterator implements SquareIterator {

        private IndexIteratorBuilder indexIteratorBuilder;

        public BaseSquareIterator(IndexIteratorBuilder indexIteratorBuilder) {
            this.indexIteratorBuilder = indexIteratorBuilder;
        }

        @Override
        public void iterate(Character[] squares,
                            int size,
                            Consumer<IndexValue<Character>> consumer) {
            List<IndexValue<Character>> entries = buildEntries(indexIteratorBuilder, squares, size);
            entries.forEach(consumer);
        }

        @Override
        public <T> T iterateAndReduce(Character[] squares,
                                      int size,
                                      T identity,
                                      BiFunction<T, IndexValue<Character>, T> accumulator,
                                      BinaryOperator<T> combiner) {
            List<IndexValue<Character>> entries = buildEntries(indexIteratorBuilder, squares, size);
            return entries.stream().reduce(identity, accumulator, combiner);
        }

        private List<IndexValue<Character>> buildEntries(IndexIteratorBuilder indexIteratorBuilder, Character[] squares, int size) {
            IntUnaryOperator indexIterator = indexIteratorBuilder.build(size);
            return IntStream.range(0, size)
                    .map(indexIterator)
                    .mapToObj(index -> new IndexValue<>(index, squares[index]))
                    .collect(Collectors.toList());
        }
    }

    public static class IndexValue<T> {

        private Integer index;
        private T value;

        public IndexValue(Integer index, T value) {
            this.index = index;
            this.value = value;
        }

        public Integer getIndex() {
            return index;
        }

        public T getValue() {
            return value;
        }
    }

    public interface SquareIterator {
        void iterate(Character[] squares,
                     int size,
                     Consumer<IndexValue<Character>> consumer);

        <T> T iterateAndReduce(Character[] squares,
                               int size,
                               T identity,
                               BiFunction<T, IndexValue<Character>, T> accumulator,
                               BinaryOperator<T> combiner);
    }

    private interface DynamicIndexIteratorBuilder {
        IndexIteratorBuilder build(Integer rowOrColumn);
    }

    private interface IndexIteratorBuilder {
        IntUnaryOperator build(Integer size);
    }
}
