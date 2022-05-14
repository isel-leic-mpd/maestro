package org.isel.maestro.streams.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> {

    /**
     * Functional version of iteration interface
     * tryAdvance receives the consumer which is called
     * with the next element of the sequence, if one exists
     * @param action - consumer
     * @return true if the consumer was called with the next element
     *              or false if none exists
     */
    boolean tryAdvance(Consumer<T> action);

    /**
     * returns a new sequence with the elements from
     * the Iterable received
     * @param src the elements source
     * @param <T>
     * @return
     */
    static <T> Sequence<T> from(Iterable<T> src) {
        Iterator<T> it = src.iterator();
        return action -> {
           if (!it.hasNext()) return false;
           action.accept(it.next());
           return true;
        };
    }

    default <R> Sequence<R> map(Function<T,R> mapper) {
        return (Consumer<R> action) ->
                this.tryAdvance(t -> action.accept(mapper.apply(t)) );
    }


    /**
     * returns a new sequence wich is the concatenation of Sequence
     * this and Sequence other
     * Ex: Being this = { 3 4 4 5 } and other = { 6 7 8 8 }
     * then return sequence = { 3 4 4 5 6 7 8 8 }
     * @param other
     * @return
     */
    default Sequence<T> concat(Sequence<T> other) {
        // TO IMPLEMENT
        return null;

    }

    /**
     * Returns a new Sequence that represents the this Sequence
     * without first "n" elements
     * Ex: Being this = { "a" , "b", "c", "d" } and n = 2
     * thenn result Sequence = { "c" , "d" }
     * @param n
     * @return
     */
    default Sequence<T> skip(int n) {
        // TO IMPLEMENT
        return null;
    }



    // operações terminais


    default Optional<T> first() {
        Object[] val = new Object[1];

        if (!tryAdvance(t ->  val[0] = t)) return Optional.empty();
        //noinspection unchecked
        return Optional.of((T) val[0]);
    }


    default List<T> toList() {
        List<T> list = new ArrayList<>();

        while(tryAdvance(t -> list.add(t)));

        return list;
    }

}
