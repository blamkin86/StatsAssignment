# statsassignment

Offer class ActionStats
in com.blamkin.jumpcloud.assignment;

## Method addAction(string) returning error
Keeps track of average time for all input strings such as
1) {"action":"jump", "time":100}
2) {"action":"run", "time":75}
3) ("action":"jump", "time":200}

## method getStats() returning string
Output formatted JsON average by "action"

# Notes

## Acceptable "action" JsON objects:
1) must include both "action" and "time" values (missing/null/empty not allowed)
2) Time values must be natural numbers (0 or greater)
3) Time values must be <= INTEGER.MAX_INT

## results
1) sorted by key
2) One significant digit (double, not int: this differs from example)
3) Totals are not allowed to roll over MAX_INT size
4) Blank keys are not output
5) Synchronized so changes during invocation are not returned 

# To build and test code
1) Install Java 8
2) install maven
3) install git
4) clone the repo (git required)
5) Have access to the dependencies in pom.xml (internet access to maven repo)

run mvn clean verify (this will execute all the unit and integration tests)

# To run the executable jar
From within the repo folder:

java -jar target/statsassignment-1.0-SNAPSHOT.jar < src/main/resources/exampleactions.json

will run the executable with the assignment inputs, and provide output

You can also run the jar with your own inputs, piped to the running jar

Finally, you can run the jar with standard input - once you've finished typing, enter the single character 'q' on a separate line


