package org.learn.processor;

import org.learn.data.Reading;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by abhiso on 6/18/16.
 */
public enum Processors {

    SYNC("sync", readings -> new SyncProcessor().process(readings)),
    ASYNC_FUTURE("async-Future", readings -> new AsyncFutureProcessor().process(readings)),
    ASYNC_GUAVA("async-Guava", readings -> new AsyncListenableFutureProcessor().process(readings)),
    ASYNC_RxJAVA("async-RxJava", readings -> new AsyncRxJavaProcessor().process(readings)),
    ASYNC_JAVA8_FUTURE("async-Java8", readings -> new AsyncCompletableFutureProcessor().process(readings));

    private String type;
    private Consumer<List<Reading>> consumer;

    private static Map<String, Processors> lookup = new HashMap<>();

    /**
     * build the lookup
     */
    static {
        Arrays.asList(Processors.values())
                .forEach(processors -> lookup.put(processors.type, processors));
    }

    /**
     * constructor
     * @param type type of Processor
     * @param consumer consumer for that proccessor
     */
    Processors(String type, Consumer<List<Reading>> consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    /**
     * get the processor for the given type
     * @param type type
     * @return Processor for that
     */
    public static Optional<Processors> getProcessor(String type) {
        return Optional.ofNullable(lookup.get(type));
    }

    /**
     * consume readings for the processor
     * @param readings list of readings
     */
    public void consume(List<Reading> readings) {
        Instant start = Instant.now();
        consumer.accept(readings);
        Instant end = Instant.now();
        System.out.println("Time taken to insert [" + readings.size() + "] in ms " + (end.toEpochMilli() - start.toEpochMilli()));
    }

    /**
     * @return get type
     */
    public String type() {
        return type;
    }

}
