
import com.blamkin.jumpcloud.assignment.ActionStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.fail;

/**
 * Longer integration testing of ActionStats
 *
 */
@RunWith(JUnit4.class)
public class ActionStatsTest {

    @Test
    public void ActionStats_WithOneAction_ReturnsProperJson() {

        ActionStats actionStats = new ActionStats();

        final String ACTION_1 = "{\"action\":\"jump\", \"time\":100}";
        final String ACTION_2 = "{\"action\":\"run\", \"time\":75}";
        final String ACTION_3 = "{\"action\":\"jump\", \"time\":200}";

        try {

            // add those actions
            actionStats.addAction(ACTION_1);
            actionStats.addAction(ACTION_2);
            actionStats.addAction(ACTION_3);

            // get the stats
            String stats = actionStats.getStats();

            // parse 'em into JsON
            System.out.println(stats);

        }
        catch (Exception e) {

            // yeah, that should have worked.
            e.printStackTrace();
            fail();
        }

    }

}
