# bootique-demo

A demo application that was written during one of the many Bootique presentations.

## How To Build

```
git clone git@github.com:andrus/bootique-demo.git
cd bootique-demo
mvn clean verify

```

## How to Run

First let's prepare a MySQL DB on Docker. Start Docker daemon and run these commands:
```
docker run --name bq-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:5.7

# connect to 127.0.0.1:3306 with any MySQL client (such as Sequel Pro on Mac, or command line "mysql" tool)
# as root/root and load test schema. E.g.:

cat schema.sql| mysql -u root --password=root -h 127.0.0.1

# Note that no test data is provided. Populating the DB is left as an excercise for the user.
```
Now run the app...

```
# This prints help
java -jar target/bootique-demo-1.0-SNAPSHOT.jar 

# As the help suggests, export MySQL password as a shell var (we could have placed it in the YAML, 
# but this would be a bad practice).
export DB_PASSWORD=root

# This actually starts the server with a proper config
java -jar target/bootique-demo-1.0-SNAPSHOT.jar -s -c demo.yml

```

Now go to [http://127.0.0.1:8080/](http://127.0.0.1:8080/) and experiment with 
[LinkRest controls](http://linkrest.io/docs/protocol-control-parameters.html). You will need some dummy data for it to
be interesting.

## Other Fun Things to Check

* Take a look inside `HelloApiIT.java` to see how integration tests can be implemented for a web service.
* Run the app with `-H` flag to see the included modules and available configuration: `java -jar target/bootique-demo-1.0-SNAPSHOT.jar -H`.

