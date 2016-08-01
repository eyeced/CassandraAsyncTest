package org.learn;

import org.learn.data.Reading;
import org.learn.generate.DataGenerator;
import org.learn.processor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class Runner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private DataGenerator dataGenerator;

    @Value("${run.numOfMessages}")
    private Integer numOfMessages;

    @Value("${run.processorType}")
    private String typeOfProcessor;

    @Autowired
    private SyncProcessor syncProcessor;

    @Autowired
    private AsyncFutureProcessor asyncFutureProcessor;

    @Autowired
    private AsyncRxJavaProcessor rxJavaProcessor;

    @Autowired
    private AsyncCompletableFutureProcessor asyncCompletableFutureProcessor;

    @Autowired
    private AsyncListenableFutureProcessor asyncListenableFutureProcessor;

    @Autowired
    private AsyncRxJavaCompletableProcessor asyncRxJavaCompletableProcessor;

    @Autowired
    private AsyncRxJavaSchedulerProcessor asyncRxJavaSchedulerProcessor;

    @Override
    public void run(String... strings) throws Exception {
        // List<Reading> readings = dataGenerator.generate(numOfMessages);

//        LOGGER.info("Starting sync process ..... ");
//        time(() -> syncProcessor.process(readings));
//
//        LOGGER.info("Waiting for 15s");
//        Thread.sleep(15000);
//
//        LOGGER.info("Starting async with future process");
//        time(() -> asyncFutureProcessor.process(readings));
        LOGGER.info("Waiting for 15s");
        Thread.sleep(15000);

        LOGGER.info("Warming up cassandra");
        time(() -> rxJavaProcessor.process(dataGenerator.generate(numOfMessages)));


        LOGGER.info("Waiting for 15s");
        Thread.sleep(15000);

        LOGGER.info("Starting async with rxJava process");
        time(() -> rxJavaProcessor.process(dataGenerator.generate(numOfMessages)));

        LOGGER.info("Waiting for 15s");
        Thread.sleep(15000);

        LOGGER.info("Starting async with listenable future process");
        time(() -> asyncListenableFutureProcessor.process(dataGenerator.generate(numOfMessages)));

        LOGGER.info("Waiting for 15s");
        Thread.sleep(15000);

        LOGGER.info("Starting async with completable future process");
        time(() -> asyncCompletableFutureProcessor.process(dataGenerator.generate(numOfMessages)));

        LOGGER.info("Done");

        LOGGER.info("Starting async with rxJava completable future process");
        time(() -> asyncRxJavaCompletableProcessor.process(dataGenerator.generate(numOfMessages)));

        LOGGER.info("Done");

        LOGGER.info("Starting async with rxJava with subscribe on process");
        time(() -> asyncRxJavaSchedulerProcessor.process(dataGenerator.generate(numOfMessages)));

        LOGGER.info("Done");
    }

    private void time(Action0 action0) {
        Instant start = Instant.now();
        action0.call();
        Instant end = Instant.now();
        LOGGER.info("Time taken to insert in ms " + (end.toEpochMilli() - start.toEpochMilli()));
    }
}
