# statsassignment

Programming assignment for tech interview

This project offers a class that keeps running totals of "action" (integer) time values, and collates and reports a sorted list of average times by action.

# class ActionStats()
in com.blamkin.jumpcloud.assignment;

## Method addAction(string) returning error
Add an action and time to track for that action.

String input is formatted JsON, in the form:
```
{
  "action": "<value>",
  "time": <integer>
}
```

Examples:
```
{"action":"jump", "time":100}
{"action":"run", "time":75}
("action":"jump", "time":200}
```

## method getStats() returning string
Return each action added in addAction, and their average time value.
Return is a JsON array of actions and times:
```
[
  {
    "action": "<value>",
    "time": <integer>  
  }
]
```

Example output:
```
[
{"action":"jump","avg":150.0},
{"action":"run","avg":75.0}
]
```

Although not technically necessary, action values are sorted alphabetically for readability.
(Note that JsON may be formatted in a simple contracted string)

## Acceptable "action" JsON objects:
1) Must include both "action" and "time" values (missing/null/empty not allowed)
2) Time values must be natural numbers (0 or greater)
3) Time values must be <= INTEGER.MAX_INT

## results
1) Sorted alphabetically by key
2) One significant digit (double, not int: this differs from requirements)
3) Totals are not allowed to roll over MAX_INT size
4) Blank keys are not output
5) Synchronized so changes during invocation are not returned 

# To build and test code, and make the executable jar
1) Install Java 8
2) Install maven
3) Install git
4) Clone this repo 
5) Have access to the dependencies in pom.xml (internet access to maven repo)

run:
> mvn clean verify 

(this will execute all the unit and integration tests)

# To run the executable jar
From within the repo folder:

> java -jar target/statsassignment-1.0-SNAPSHOT.jar < src/main/resources/exampleactions.json

will run the executable with the assignment inputs, and provide output

You can also run the jar with your own inputs, piped to the running jar

Finally, you can run the jar with standard input - once you've finished typing, enter the single character 'q' on a separate line



