package org.learn.processor;

import org.learn.data.Reading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class AsyncRxJavaProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRxJavaProcessor.class);
    /**
     * start the process
     *
     * @param readings
     */
    @Override
    public void process(List<Reading> readings) {
        CountDownLatch latch = new CountDownLatch(1);
        Scheduler scheduler = Schedulers.io();
        Observable.from(readings)
                .flatMap(r -> Observable.from(cassandraStore.async(insertCql, readToArgs(r)), scheduler))
                .subscribe(rows -> {}, Throwable::printStackTrace, () -> {
                    LOGGER.info("RxJava Insert complete");
                    latch.countDown();
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
