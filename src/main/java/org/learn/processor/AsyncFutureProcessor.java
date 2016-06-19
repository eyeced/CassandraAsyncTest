package org.learn.processor;

import org.learn.data.Reading;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class AsyncFutureProcessor extends AbstractProcessor {

    /**
     * start the process
     *
     * @param readings
     */
    @Override
    public void process(List<Reading> readings) {
        readings.parallelStream()
                .map(reading -> cassandraStore.async(insertCql, readToArgs(reading)))
                .forEach(resultSetFuture -> {
                    try {
                        resultSetFuture.getUninterruptibly(10000, TimeUnit.MILLISECONDS);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Unable to process data");
                    }
                });
    }
}
