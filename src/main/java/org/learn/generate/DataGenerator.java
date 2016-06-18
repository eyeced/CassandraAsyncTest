package org.learn.generate;

import org.learn.data.Reading;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class would generate data using multiple options of Cassandra.
 * via sync or async.
 * Created by abhiso on 6/18/16.
 */
@Component
public class DataGenerator {

    private Random random = new Random();

    /**
     * generate or insert this much number of data.
     * @param num num of data to be generated
     */

    public List<Reading> generate(int num) {
        return IntStream.range(1, num + 1)
                .mapToObj(i ->
                    new Reading(Long.valueOf(i), random.nextDouble(), Date.from(Instant.now()), 0l)
                ).collect(Collectors.toList());
    }
}
