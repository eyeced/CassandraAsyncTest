package org.learn;

import org.learn.data.Reading;
import org.learn.generate.DataGenerator;
import org.learn.processor.AsyncFutureProcessor;
import org.learn.processor.SyncProcessor;
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

    @Override
    public void run(String... strings) throws Exception {
        List<Reading> readings = dataGenerator.generate(numOfMessages);
        time(() -> syncProcessor.process(readings));

        LOGGER.info("Waiting for 15s");
        Thread.sleep(15000);

        time(() -> asyncFutureProcessor.process(readings));
    }

    private void time(Action0 action0) {
        Instant start = Instant.now();
        action0.call();
        Instant end = Instant.now();
        LOGGER.info("Time taken to insert in ms " + (end.toEpochMilli() - start.toEpochMilli()));
    }
}
