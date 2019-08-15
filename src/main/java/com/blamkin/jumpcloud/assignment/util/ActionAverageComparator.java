package com.blamkin.jumpcloud.assignment.util;

import com.blamkin.jumpcloud.assignment.entities.ActionAverage;

import java.util.Comparator;

/**
 * Compare two ActionAverages for sorting
 *
 */
public class ActionAverageComparator implements Comparator<ActionAverage> {

    @Override
    public int compare(ActionAverage o1, ActionAverage o2) {
        if (o1==null) return -1;
        if (o2==null) return 1;
        return o1.getAction().compareTo(o2.getAction());
    }
}
