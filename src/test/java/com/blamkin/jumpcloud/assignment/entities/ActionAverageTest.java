package com.blamkin.jumpcloud.assignment.entities;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ActionAverageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void actionAverage_withNullKey_throwsException() {

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // the key can't be null
        ActionAverage aa = new ActionAverage(null, 1,2);
    }

    @Test
    public void actionAverage_withEmptyKey_throwsException() {

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // the key can't be empty
        ActionAverage aa = new ActionAverage("", 1,2);
    }

    @Test
    public void actionAverage_withReasonableData_looksReasonable() {

        final String key = "key";
        final int count = 4;
        final int total = 100;

        // the key can't be null or empty
        ActionAverage aa = new ActionAverage(key, count,total);
        assertNotNull(aa);

        // is the total calculated correctly?
        assertEquals(total/count, aa.getAvg(),0.0D);

    }

    @Test
    public void actionAverage_withZeroTotal_reportsZero() {

        // that's a zero total
        ActionAverage aa = new ActionAverage("PoleVault", 1,0);

        // is the total calculated correctly?
        assertEquals(0, aa.getAvg(),0.0D);
    }

    @Test
    public void actionAverage_withNegativeTotal_throwsException() {

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // that's a bad looking total
        ActionAverage aa = new ActionAverage("anykey", 1,-1);
    }

    @Test
    public void actionAverage_withRoundUp_computesProperly() {

        // this should round up to 2.5
        final int count = 100;
        final int time = 246;

        ActionAverage aa = new ActionAverage("anykey", count, time);

        assertEquals(2.5, aa.getAvg(),0.0);

    }

    @Test
    public void actionAverage_withRoundDown_computesProperly() {

        // this should round up to 2.5
        final int count = 100;
        final int time = 244;

        ActionAverage aa = new ActionAverage("anykey", count, time);

        assertEquals(2.4, aa.getAvg(),0.0);

    }



}
