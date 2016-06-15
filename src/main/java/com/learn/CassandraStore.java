package com.learn;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

/**
 * Created by abhiso on 6/14/16.
 */
public class CassandraStore {

    private Cluster cluster;

    private Session session;

    private String contactPoints;

    private String keyspace;

    private Integer port;

    public CassandraStore() {
        Cluster.Builder builder = Cluster.builder()
                .addContactPoints(contactPoints)
                .withProtocolVersion(ProtocolVersion.NEWEST_SUPPORTED)
                .withPort(port);
        cluster = builder.build();
        session = cluster.connect(keyspace);
    }
}
