# webflux-cassandra
Simple app to learn using Spring Webflux and Cassandra. This app downloads and saves the top 500 stories on Hacker News into a local cassandra instance.

### Getting Started
Start a local cassandra instance
`docker run --name cassandra -d -P -e CASSANDRA_BROADCAST_ADDRESS=192.168.99.100 cassandra:latest`

Then retrieve the forwarded port using the command
`docker ps`
The correct port will correlate to `9042`.

Update the `cassandra.*` properties in `application.properties` as necessary.