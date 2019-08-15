package com.blamkin.jumpcloud.assignment.entities;

/**
 * Object representing the count of actions and their total
 * E.G. jump:100 and jump:100 would be count = 2 and total = 200
 */
public class TimeTotal {

    // privates
    private int count;
    private long total;

    // make one
    public TimeTotal(int total) {
        this.count=1;
        this.total=total;
    }

    // add to the total
    public void addTime(int time) throws ArithmeticException {

        // fail fast
        this.total=Math.addExact(this.total, time);

        // that worked, so one more in the bucket
        this.count++;
    }

    // get values
    public int getCount() {
        return this.count;
    }
    public long getTotal() {
        return this.total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeTotal{");
        sb.append("count=").append(count);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTotal timeTotal = (TimeTotal) o;

        if (count != timeTotal.count) return false;
        return total == timeTotal.total;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }
}
