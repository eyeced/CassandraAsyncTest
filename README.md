# Synchronous to Asynchronous comparison of Cassandra java driver.
The Datastax java driver uses an asynchronous query architecture, which allows client to query to the server in non blocking way, I want to further quantify the results and put in the ways to perform asynchronous queries, and what should be the best way to use them.
First I will start with synchronous queries, then move on to different ways to handle asynchronous queries and then to batch statements and quantifying the results in all cases.

This blog works on some of the methods provided on this page [Asynchronous queries with the Java Driver](http://www.datastax.com/dev/blog/java-driver-async-queries).

### What I will be doing here ?
I would send a million insert on a table in cassandra, which would represent a time series type of data, and then measure the time taken in inserting this data synchronously and then asynchronously.

## Synchronous

