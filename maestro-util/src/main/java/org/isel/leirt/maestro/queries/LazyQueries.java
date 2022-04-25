/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.leirt.maestro.queries;

import org.isel.leirt.maestro.iterators.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LazyQueries {


    // intermediate operations

    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred){
        return () -> new IteratorFilter<>(src, pred);
    }

    /**
     * TO IMPLEMENT
     * @param src
     * @param nr
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> skip(Iterable<T> src, int nr)
    {
         return null;
    }

    /**
     * TO IMPLEMENT
     * @param src
     * @param nr
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> limit(Iterable<T> src, int nr){
        return null;
    }

    public static <T, R> Iterable<R> map(Iterable<T> src, Function<T, R> mapper){
        return () -> new IteratorMap<>(src, mapper);
    }

    /**
     * TO IMPLEMENT
     * @param src
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Iterable<R> flatMap(Iterable<T> src, Function<T, Iterable<R>> mapper){
        return null;
    }

    public static <T> Iterable<T> takeWhile(Iterable<T> src, Predicate<T> pred){
        return () -> new IteratorTakeWhile<>(src,pred);
    }

    /**
     * TO IMPLEMENT
     * @param src
     * @param pred
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> skipWhile(Iterable<T> src, Predicate<T> pred){
        return null;
    }

    /**
     * TO IMPLEMENT
     * @param src
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> cache(Iterable<T> src) {
        return null;
    }

    // factory operations

    /**
     * TO IMPLEMENT
     * generate an infinite sequence with values supplied by the received supplier
     * @param next
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> generate(Supplier<T> next){

        return null;
    }

    /**
     * TO IMPLEMENT
     * @param seed
     * @param next
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> iterate(T seed, Function<T, T> next){
        return null;
    }

    public static <T> Iterable<T> from(T[] items) {
        return () -> new IteratorArray<>(items);
    }

    public static Iterable<Integer> range(int min, int max) {
        return () ->
            new Iterator<Integer>() {
                private int curr = min;

                @Override
                public boolean hasNext() {
                    return curr <= max;
                }

                @Override
                public Integer next() {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    return curr++;
                }
            };
    }

    // terminal operations

    public static <T> int count(Iterable<T> src) {
        int count=0;
        for(T item:src)
            count++;
        return count;
    }

    public static <T> Object[] toArray(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        for(T val:src) {
            res.add(val);
        }
        return res.toArray();
    }

    public static <T> List<T> toList(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        for(T val:src) {
            res.add(val);
        }
        return res;
    }

    public static <T> Optional<T> first(Iterable<T> src) {
        Iterator<T> it = src.iterator();
        if (!it.hasNext()) return Optional.empty();
        return Optional.of(it.next());
    }

    public static <T extends Comparable<T>> Optional<T> max(Iterable<T> src) {

        Iterator<T> it = src.iterator();
        if (!it.hasNext()) return Optional.empty();
        T m = it.next();
        while(it.hasNext()) {
            T n = it.next();
            if (m.compareTo(n) < 0) m =n;
        }
        return Optional.of(m);
    }

    public static <T> Optional<T> reduce(Iterable<T> src, BinaryOperator<T> accumulator) {
        Iterator<T> it = src.iterator();
        if (!it.hasNext()) return Optional.empty();
        T red = it.next();
        while(it.hasNext()) {
            red = accumulator.apply(red, it.next());
        }
        return Optional.of(red);
    }


    /**
     * TO IMPLEMENT
     * try implement without cycles using reduce operation
     * @param src
     * @param <T>
     * @return
     */
    public static <T> T last(Iterable<T> src) {
         return null;
    }




}
