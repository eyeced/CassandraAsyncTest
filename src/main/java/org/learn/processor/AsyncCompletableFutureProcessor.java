package org.learn.processor;

import com.datastax.driver.core.ResultSet;
import org.learn.data.Reading;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class AsyncCompletableFutureProcessor extends AbstractProcessor {

    /**
     * start the process
     *
     * @param readings
     */
    @Override
    public void process(List<Reading> readings) {
        Executor executor = Executors.newFixedThreadPool(16);
        final List<CompletableFuture<ResultSet>> collect = readings.parallelStream()
                .map(reading -> cassandraStore.async(insertCql, readToArgs(reading)))
                .map(resultSetFuture -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return resultSetFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, executor))
                .collect(Collectors.toList());

        collect.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
