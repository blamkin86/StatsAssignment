package com.blamkin.jumpcloud.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.blamkin.jumpcloud.assignment.entities.Action;
import com.blamkin.jumpcloud.assignment.entities.ActionAverage;
import com.blamkin.jumpcloud.assignment.entities.TimeTotalCount;
import com.blamkin.jumpcloud.assignment.util.ActionAverageComparator;
import com.blamkin.jumpcloud.assignment.util.JsonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.exit;

/**
 * Main class for reporting "action" statistics
 *
 *
 */
public class ActionStats {

    // our map of values
    private ConcurrentHashMap<String, TimeTotalCount> actionMap = new ConcurrentHashMap<String, TimeTotalCount>();

    // make it easy to exit from the command line
    private static final String EXIT_CHAR = "Q";

    /**
     *  Add an action to the statistics gatherer
     *
     * @param jsonAction
     * @throws IllegalArgumentException
     */
    public void addAction(String jsonAction) throws IllegalArgumentException {

        // create it, if possible
        Action newAction = JsonUtils.parseActionString(jsonAction);

        // everybody wait on me
        // don't want any changes between the get and the possible replace()
        synchronized (actionMap) {

            // here's the time total for this single action
            TimeTotalCount addTimeTotalCount = new TimeTotalCount(newAction.getTime());

            // look for it
            TimeTotalCount existingTotal = actionMap.get(newAction.getAction());
            if (existingTotal==null) {
                // just start with this one
                actionMap.put(newAction.getAction(), addTimeTotalCount);
            }
            else {
                // we already have a total, let's combine
                // and replace in the list
                TimeTotalCount updatedTotal = new TimeTotalCount(existingTotal, newAction.getTime());
                actionMap.replace(newAction.getAction(), updatedTotal);
            }

        }
    }

    /**
     * Return the statistics we've gathered
     * as a json array
     *
     * @return
     */
    public synchronized String getStats() {

        try {

            // collect them
            ArrayList<ActionAverage> averageList = new ArrayList<ActionAverage>();

            // calc them all
            // concurrency could begin here
            // but if another thread adds/changes totals
            // after this thread invokes the method
            // the changes would be included

            // if that's OK,
            // remove the synchronized
            // and any changes to this point will be captured
            // the entrySet does not change here after invocation
            for (Map.Entry<String, TimeTotalCount> timeEntry: actionMap.entrySet() ) {

                // individually, collect the good ones
                try {
                    // make the average calculation and put it into the list
                    averageList.add(
                            new ActionAverage(timeEntry.getKey(), timeEntry.getValue().getCount(), timeEntry.getValue().getTotal())
                    );
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Unexpected value in statistics map");
                }

            }

            // sort for fun
            // technically JsON is not ordered, so
            // not sure this is necessary
            // but it helps match the expected output
            averageList.sort(new ActionAverageComparator());

            // ok put the collection into a JsON string array
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(averageList);

        }
        catch (Exception e) {
            System.out.println("Unable to calculate statistics");
            e.printStackTrace();
        }

        // probably talk to PM or Dev about handling bad values
        return null;
    }

    // direct invocation from executable jar
    public static void main(String[] args) {

        // read the input
        try ( InputStreamReader isReader = new InputStreamReader(System.in);
                BufferedReader bufReader = new BufferedReader(isReader); ) {

            // collect stats
            ActionStats stats = new ActionStats();

            // start reading from the input
            String in = null;
            while ((in=bufReader.readLine()) != null) {

                // maybe bail from command line
                if (EXIT_CHAR.equalsIgnoreCase(in)) {
                    break;
                }

                // read, but
                // inform problems and ignore bad lines
                try {
                    stats.addAction(in);
                }
                catch (IllegalArgumentException iae) {
                    System.out.println("Invalid line: " + in);
                    iae.printStackTrace();
                }
            }

            // output!
            System.out.println(stats.getStats());

        }
        catch (Exception e ){
            System.out.println("Unable to produce statistics");
            e.printStackTrace();
            exit(1);
        }

    }
}
