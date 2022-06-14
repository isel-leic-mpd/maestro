package org.isel.maestro.async;

import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StreamUtilsTest {
    /*
    seq1 = {"isel", "ola", "dup", "super", "jingle" } e
    seq2 = {4, 5, 6, 7} entao fazendo o merge que tenha
    como criterio de juncao (str, nr) -> str.length == nr e
    como transformacao (str, nr) -> str + nr
    resulta numa sequencia {"isel4" "ola0", "dup0", "super5", "jingle6"}
     */

    /*
    @Test
    public void merge1Test() {
        Stream<String> words = Stream.of("isel", "ola", "dup", "super", "jingle" );
        Stream<Integer> numbers = Stream.of(4, 5, 6, 7);

        Stream<String> wn = merge(words, numbers,
                            (str, nr) -> 0,
                            (str, nr) ->
                                    str.length() == nr,
                            (str, nr) ->
                                    str + nr.orElse(0)
        );
        String[] expected  = {"isel4", "ola0", "dup0", "super5", "jingle6"};
        String[] actual = wn.toArray(len -> new String[len]);
        assertArrayEquals(expected,actual);
    }

    @Test
    public void merge2Test() {
        Stream<String> words = Stream.of("isel", "ola", "dup", "super", "jingle" );
        Stream<Integer> numbers = Stream.of(4, 5, 6, 7);

        Stream<String> wn = merge2(words, numbers,
                (str, nr) ->
                        str.length() == nr,
                (str, nr) ->
                        str + nr.orElse(0)
        );
        String[] expected  = {"isel4", "ola0", "dup0", "super5", "jingle6"};
        String[] actual = wn.toArray(len -> new String[len]);
        assertArrayEquals(expected,actual);
    }

    @Test
    public void merge3Test() {
        Stream<String> words = Stream.of("isel", "ola", "dup", "super", "jingle" );
        Stream<Integer> numbers = Stream.of(4, 5, 6, 7);

        Stream<String> wn = merge3(words, numbers,
                (str, nr) -> str.length() == nr,
                (str, nr) -> str + nr.orElse(0)
        );
        String[] expected  = {"isel4", "ola0", "dup0", "super5", "jingle6"};
        String[] actual = wn.toArray(len -> new String[len]);
        assertArrayEquals(expected,actual);
    }
    */

}
