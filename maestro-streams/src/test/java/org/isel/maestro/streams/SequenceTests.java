package org.isel.maestro.streams;

import static org.isel.maestro.streams.utils.Sequence.from;
import static org.junit.jupiter.api.Assertions.*;

import org.isel.maestro.streams.utils.Sequence;
import org.junit.jupiter.api.Test;
import java.util.List;

public class SequenceTests {


    @Test
    public void mapTest() {
        Sequence<Integer> numbers =
            from(List.of(2, 5, 7));
        Sequence<Integer> evens =
            numbers.map( n -> n *2);

        List<Integer> expected = List.of(4, 10, 14);
        assertEquals(expected, evens.toList());
    }

    // Add Your tests
}
