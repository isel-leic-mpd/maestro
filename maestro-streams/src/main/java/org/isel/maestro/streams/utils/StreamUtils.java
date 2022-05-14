package org.isel.maestro.streams.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    /**
     * creates a stream supplier that shares the source stream
     * caching its elements
     * To be defined more properly in next lectures
     * @param src
     * @param <T>
     * @return
     */
    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
        // TO IMPLEMENT
        return null;
    }


    // the intersection operation

    /**
     * @param seq1          first source stream
     * @param seq2          second source stream
     * @param matched       defines equality (matching) between stream elements
     * @param mapper        maps a pair of matched elements
     * @return
     */
    public  static <T,U,V> Stream<V> intersection(
        Stream<T> seq1,
        Stream<U> seq2,
        BiPredicate<T,U> matched,
        BiFunction<T,U, V> mapper) {

        // TO IMPLEMENT
        return null;
    }
}
