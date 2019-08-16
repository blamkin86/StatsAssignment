package com.blamkin.jumpcloud.assignment.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Check basics with TimeTotalCount class
 *
 */
@RunWith(JUnit4.class)
public class TimeTotalTest {

    @Test
    public void timeTotal_byDefault_hasCountOfOne() {

        TimeTotalCount tt = new TimeTotalCount(3);
        assertEquals(1,tt.getCount());
    }

    @Test
    public void timeTotal_byDefault_hasMatchingTimeTotal() {

        final int time = 100;
        TimeTotalCount tt = new TimeTotalCount(time);
        assertEquals(time,tt.getTotal());
    }

    @Test
    public void timeTotal_whenAdding_hasCorrectCountAndTotal() {

        final int time = 100;
        TimeTotalCount tt = new TimeTotalCount(time);

        final int additionalTime = 200;
        TimeTotalCount tt2 = new TimeTotalCount(tt, additionalTime);

        // we added two times together
        assertEquals(2,tt2.getCount());

        // the total should match
        assertEquals(time + additionalTime,tt2.getTotal());

    }

    @Test
    public void timeTotal_equalObjects_areEqual() {

        TimeTotalCount tt = new TimeTotalCount(3);
        assertTrue(tt.equals(tt));
    }

    @Test
    public void timeTotal_equalDistinctObjects_areEqual() {

        TimeTotalCount tt = new TimeTotalCount(3);
        TimeTotalCount tt2 = new TimeTotalCount(3);
        assertTrue(tt2.equals(tt));
    }
}
