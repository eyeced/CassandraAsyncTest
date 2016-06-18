package org.learn.processor;

import org.learn.data.Reading;
import org.learn.generate.DataGenerator;
import org.learn.store.CassandraStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by abhiso on 6/18/16.
 */
public abstract class AbstractProcessor implements Processor {

    /** the cassandra store */
    @Autowired
    protected CassandraStore cassandraStore;

    @Autowired
    protected DataGenerator dataGenerator;
    /**
     * The insert cql
     */
    protected String insertCql = "insert into reads(device_id, read_time, flags, value) values(?, ?, ?, ?)";

    /**
     * read to args
     * @param reading the reading
     * @return object args for query
     */
    protected Object[] readToArgs(Reading reading) {
        return new Object[]{ reading.getDeviceId(), reading.getReadTime(), reading.getFlag(), reading.getValue() };
    }
}
