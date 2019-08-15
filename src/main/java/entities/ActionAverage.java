package entities;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the average of all times for one action
 * (main class output)
 *
 */
public class ActionAverage {

    // privates
    private String action;
    private double avg;

    public ActionAverage(String action, int count, long total) throws IllegalArgumentException {
        if (StringUtils.isEmpty(action)) {
            throw new IllegalArgumentException("Action cannot be empty");
        }
        if (total<0) {
            throw new IllegalArgumentException("Total cannot be less than zero");
        }
        this.action = action;
        this.avg = total/count;
    }

    public String getAction() {
        return action;
    }
    public double getAvg() {
        return avg;
    }
}
