package org.isel.maestro.streams.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
        // TO IMPLEMENT
        return null;
    }


    // the intersection operation
    public  static <T,U,V> Stream<V> intersection(
        Stream<T> seq1,
        Stream<U> seq2,
        BiPredicate<T,U> matched,
        BiFunction<T,U, V> mapper) {

        // TO IMPLEMENT
        return null;
    }
}
