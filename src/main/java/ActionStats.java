import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Action;
import entities.ActionAverage;
import entities.TimeTotal;
import org.json.JSONStringer;
import util.ActionAverageComparator;
import util.JsonUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class for tracking "action" statistics
 *
 *
 */
public class ActionStats {

    // our map of values
    ConcurrentHashMap<String, TimeTotal> actionMap = new ConcurrentHashMap<String, TimeTotal>();

    /**
     *  Add an action to the statistics gatherer
     *
     * @param jsonAction
     * @throws Exception
     */
    public void addAction(String jsonAction) throws IllegalArgumentException {

        // create it, if possible
        Action a = JsonUtils.parseActionString(jsonAction);

        // everybody wait on me
        // don't want any changes between the first put and the possible replace()
        synchronized (actionMap) {

            // here's the time total for this single action
            TimeTotal thisTotal = new TimeTotal(a.getTime());

            // always put it
            // - collection returns prior value so we can recover
            TimeTotal priorTotal = actionMap.put(a.getAction(), thisTotal);

            // wait, was there something out there?
            if (priorTotal!=null) {
                // add the total to the prior value, replace this new one
                priorTotal.addTime(a.getTime());
                actionMap.replace(a.getAction(), thisTotal, priorTotal);
            }
        }
    }

    /**
     * Return the statistics we've gathered
     * as a json array
     *
     * @return
     */
    public String getStats() {

        try {

            // collect them
            ArrayList<ActionAverage> averageList = new ArrayList<ActionAverage>();

            // calc them all
            // concurrency begins here -
            // any items added by other threads
            // after this statement runs
            // are not included in the stats
            for (Map.Entry<String, TimeTotal> timeEntry: actionMap.entrySet() ) {

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
}
