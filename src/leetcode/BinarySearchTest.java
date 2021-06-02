package leetcode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BinarySearchTest {

    private final BinarySearch binarySearch = new BinarySearch();

    @ParameterizedTest
    @MethodSource("dataPrivoder")
    void test(int index, int[] array, int searchTarget) {
        assertEquals(index, binarySearch.search(array, searchTarget));
    }

    private static Stream<Arguments> dataPrivoder() {
        return Stream.of(
            Arguments.of(0, new int[] {5}, 5),
            Arguments.of(-1, new int[] {}, 5)
        );
    }
}
