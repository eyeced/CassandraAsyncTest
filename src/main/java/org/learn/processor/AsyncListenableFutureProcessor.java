package org.learn.processor;

import com.datastax.driver.core.ResultSetFuture;
import com.google.common.util.concurrent.Futures;
import org.learn.data.Reading;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class AsyncListenableFutureProcessor extends AbstractProcessor {
    /**
     * start the process
     *
     * @param readings
     */
    @Override
    public void process(List<Reading> readings) {
        List<ResultSetFuture> futures = readings.stream()
                .map(reading -> cassandraStore.async(insertCql, readToArgs(reading)))
                .collect(Collectors.toList());

        Futures.successfulAsList(futures);
    }
}
