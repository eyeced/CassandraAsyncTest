package org.learn.processor;

import org.learn.data.Reading;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by abhiso on 7/8/16.
 */
@Component
public class AsyncRxJavaCompletableProcessor extends AbstractProcessor {

    /**
     * start the process
     *
     * @param readings
     */
    @Override
    public void process(List<Reading> readings) {
        Executor executor = Executors.newFixedThreadPool(16);
        Observable.from(readings)
                .map(reading -> cassandraStore.async(insertCql, readToArgs(reading)))
                .map(resultSetFuture -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return resultSetFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, executor))
                .map(CompletableFuture::join)
                .subscribeOn(Schedulers.io())
                .subscribe(row -> {}, throwable -> {}, () -> {});
    }
}
