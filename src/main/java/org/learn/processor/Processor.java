package org.learn.processor;

import org.learn.data.Reading;

import java.util.List;

/**
 * Processor interface which would take in the data from generator
 * and insert in cassandra.
 *
 * Created by abhiso on 6/18/16.
 */
public interface Processor {

    /**
     * start the process
     */
    void process(List<Reading> readings);
}
