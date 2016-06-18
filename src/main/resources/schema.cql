CREATE KEYSPACE async WITH replication = {
    'class' : 'SimpleStrategy',
    'replication_factor': 1
} AND durable_writes = 'true';

USE async;

CREATE table READS (
    device_id bigint,
    value double,
    flags bigint,
    read_time timestamp,
    PRIMARY KEY (device_id, read_time)
) WITH CLUSTERING ORDER BY (read_time DESC);
