import com.blamkin.jumpcloud.assignment.ActionStats;
import com.blamkin.jumpcloud.assignment.entities.Action;
import com.blamkin.jumpcloud.assignment.entities.ActionAverage;
import com.blamkin.jumpcloud.assignment.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 * Run several threads against ActionStats and make sure totals match our calculations
 * NOTE: definitely an integration test, but runs with the unit tests for simplicity
 *
 */
@RunWith(JUnit4.class)
public class ActionStatsMultiThreadTest {

    private ActionStats actionStats = new ActionStats();

    private static final String ACTION_FIZZ = "fizz";
    private static final String ACTION_BUZZ = "buzz";

    @Test
    public void actionStats_sameActionMultipleThreads_works() {

        // so many runs
        final int RUN_COUNT = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(RUN_COUNT);
        List<Callable<Integer>> threads = new ArrayList<Callable<Integer>>();
        for (int i=0; i<RUN_COUNT; i++) {

            // make a task to add an action
            Callable<Integer> callableTask = () -> {
                try {
                    // random time
                    int time = (int)(Math.random() * 1000);
                    // make an action with that time
                    String actionString = makeActionTimeString(ACTION_FIZZ, time);
                    // track it
                    actionStats.addAction(actionString);
                    // return the time so we can calc separately in the test
                    return time;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };

            threads.add(callableTask);

        }

        // and run them!
        try {


            // run and capture times ourselves
            Integer threadTimeTotal = 0;
            List<Future<Integer>> futures = executor.invokeAll(threads);

            int threadCount=0;
            for (Future<Integer> future: futures) {
                Integer time = future.get();
                threadTimeTotal+= time;
                threadCount++;
            }

            // average to one decimal
            Double threadAverage = (double) (Math.round((double)threadTimeTotal/threadCount * 10))/10;

            System.out.println("Thread totals...");
            System.out.println("count: " + threadCount);
            System.out.println("Time Total: " + threadTimeTotal);
            System.out.println("Average: " + threadAverage);

            // output for fun
            System.out.println("ActionStats total... ");
            System.out.println( actionStats.getStats());

            // make sure the class total matches our thread total
            ObjectMapper objectMapper = new ObjectMapper();
            List<ActionAverage> actionAverages = objectMapper.readValue(actionStats.getStats(), new TypeReference<List<ActionAverage>>(){});

            // we sent only one action
            // there should be only one total
            assertEquals(1, actionAverages.size());

            // totals should match
            assertEquals(threadAverage, actionAverages.get(0).getAvg(), 0.0);

        }
        catch (Exception e) {
            System.out.println("Not all threads completed");
            e.printStackTrace();
        }

    }


    @Test
    public void actionStats_multipleActionsMultipleThreads_works() {

        // so many runs
        final int RUN_COUNT = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(RUN_COUNT);
        List<Callable<Action>> threads = new ArrayList<Callable<Action>>();
        for (int i=0; i<RUN_COUNT; i++) {

            // make a task to add an action
            Callable<Action> callableTask = () -> {
                try {

                    // random time
                    int time = (int)(Math.random() * 1000);

                    // random action
                    String actionName = time < 500 ? ACTION_BUZZ : ACTION_FIZZ;
                    String actionString = makeActionTimeString(actionName, time);
                    actionStats.addAction(actionString);

                    // return same
                    return JsonUtils.parseActionString(actionString);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };

            threads.add(callableTask);

        }

        // and run them!
        try {

            // run and capture times ourselves
            List<Future<Action>> futures = executor.invokeAll(threads);

            // output for fun
            System.out.println("ActionStats total... ");
            System.out.println( actionStats.getStats());

            // calculate our own totals from the thread runs
            int fizzTimeTotal = 0;
            int buzzTimeTotal = 0;

            int fizzThreadCount = 0;
            int buzzThreadCount = 0;

            for (Future<Action> future: futures) {

                Action action = future.get();
                if (ACTION_FIZZ.equalsIgnoreCase(action.getAction())) {
                    fizzTimeTotal += action.getTime();
                    fizzThreadCount++;
                }
                else if (ACTION_BUZZ.equalsIgnoreCase(action.getAction())) {
                    buzzTimeTotal+= action.getTime();
                    buzzThreadCount++;
                }
                else {
                    System.out.println("Collection has unexpected non fizz/buzz actions?");
                    fail();
                }
            }

            // and our average is...
            Double fizzAverage = (double) (Math.round((double)fizzTimeTotal/fizzThreadCount * 10))/10;
            Double buzzAverage = (double) (Math.round((double)buzzTimeTotal/buzzThreadCount * 10))/10;

            System.out.println("Thread totals...");
            System.out.println("fizz count: " + fizzThreadCount);
            System.out.println("fizz Time Total: " + fizzTimeTotal);
            System.out.println("fizz Average: " + fizzAverage);
            System.out.println("buzz count: " + buzzThreadCount);
            System.out.println("buzz Time Total: " + buzzTimeTotal);
            System.out.println("buzz Average: " + buzzAverage);


            // make sure the class total matches our thread total
            ObjectMapper objectMapper = new ObjectMapper();
            List<ActionAverage> actionAverages = objectMapper.readValue(actionStats.getStats(), new TypeReference<List<ActionAverage>>(){});

            // we sent only one action
            // there should be only one total
            assertEquals(2, actionAverages.size());

            // totals should match
            // by action name
            for (ActionAverage actionAverage: actionAverages) {
                if (ACTION_BUZZ.equalsIgnoreCase(actionAverage.getAction())) {
                    assertEquals(buzzAverage, actionAverage.getAvg(), 0.0);
                }
                else if (ACTION_FIZZ.equalsIgnoreCase(actionAverage.getAction())) {
                    assertEquals(fizzAverage, actionAverage.getAvg(), 0.0);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Not all threads completed");
            e.printStackTrace();
        }

    }

    // helper to avoid string concat JsON, my favorite.
    private String makeActionTimeString(String actionName, int time) {

        Action action =new Action(actionName, time);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(action);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


}
