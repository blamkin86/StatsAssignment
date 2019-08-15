package com.blamkin.jumpcloud.assignment.util;

import com.blamkin.jumpcloud.assignment.entities.Action;
import com.blamkin.jumpcloud.assignment.entities.TimeTotal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class AverageActionJsonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // expected emptiness
    private static final String EMPTY_OBJECT = "{}";

    // empty key yields empty json object return
    @Test
    public void avgAction_whenPassedEmptyKey_returnsEmptyJson() throws Exception {

        // that's an empty key
        String result= JsonUtils.avgAction(null, null);

        // nothing there, right?
        assertEquals(EMPTY_OBJECT, result);

    }

    // empty totals yield empty json return object
    @Test
    public void avgAction_whenPassedTotalKey_returnsEmptyJson() throws Exception {

        // that's an empty timeTotal
        String result= JsonUtils.avgAction("MyAction", null);

        // nothing there, right?
        assertEquals(EMPTY_OBJECT, result);

    }

    // expected object yields expected JsON
    @Test
    public void avgAction_whenPassedGoodData_returnsExpectedJson() throws Exception {

        // just one timeTotal, so we know the count and average
        final int total = 100;
        TimeTotal tt = new TimeTotal(total);

        // that's an empty key
        String result= JsonUtils.avgAction("MyAction", tt);

        // nothing there, right?
        assertEquals("{\"action\":\"MyAction\",\"avg\":100.0}", result);

    }

}
