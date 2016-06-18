package org.learn.processor;

import org.learn.data.Reading;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by abhiso on 6/18/16.
 */
@Component
public class SyncProcessor extends AbstractProcessor {

    /**
     * start the process
     *
     * @param readings readings to inserted
     */
    @Override
    public void process(List<Reading> readings) {
        readings.forEach(reading -> cassandraStore.sync(insertCql, readToArgs(reading)));
    }
}
