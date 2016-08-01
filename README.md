# Asynchronous comparison of Cassandra java driver.
The Datastax java driver uses an asynchronous query architecture, which allows client to query to the server in non blocking way, I want to further quantify the results and put in the ways to perform asynchronous queries, and what should be the best way to use them.
First I will start with synchronous queries, then move on to different ways to handle asynchronous queries and then to batch statements and quantifying the results in all cases.

This blog works on some of the methods provided on this page [Asynchronous queries with the Java Driver](http://www.datastax.com/dev/blog/java-driver-async-queries).

### What I will be doing here ?
I would send a million insert on a table in cassandra, which would represent a time series type of data, and then measure the time taken in inserting this data synchronously and then asynchronously.

### My Setup
I am performing these tests on my Mac Book Pro Machine which has
* 2.5 GHz Intel Core i7
* 16 GB 1600 MHz DDR3

### Cassandra Schema to be used

## Synchronous writes
For synchronous writes I would be using Cassandra's BoundStatement queries and calling the following code.
```java
public void sync(String cql, Object[] args) {
    BoundStatement boundStatement = new BoundStatement(getStatement(cql));
    boundStatement.bind(args);
    session.execute(boundStatement);
}
```
Here we would just insert the reads sequentially, obviously this would be slow, as we are not using parallel writes for the inserts, but still we would create a base case for our performance comparisons.
```java
public void process(List<Reading> readings) {
    readings.forEach(reading -> cassandraStore.sync(insertCql, readToArgs(reading)));
}
```
### Results

| Total Inserts  | Total Time Taken | Ops / sec |  
|---|---|---|
| 1,000,000 | | |

## Asynchronous writes
For the following cases I would be using cassandra executeAsync feature for doing cassandra async calls
```java
public ResultSetFuture async(String cql, Object[] args) {
    BoundStatement boundStatement = new BoundStatement(getStatement(cql));
    boundStatement.bind(args);
    return session.executeAsync(boundStatement);
}
```

### Java Futures
Simple executeAsync calls would be made and then I would wait for each future to return
```java
public void process(List<Reading> readings) {
    readings.stream()
            .map(reading -> cassandraStore.async(insertCql, readToArgs(reading)))
            .forEach(resultSetFuture -> {
                try {
                    resultSetFuture.getUninterruptibly(10000, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unable to process data");
                }
            });
}
```
