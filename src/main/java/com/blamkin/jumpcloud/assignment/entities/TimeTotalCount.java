package com.blamkin.jumpcloud.assignment.entities;

/**
 * Object representing the count of an action and its total
 * E.G. jump:100 and jump:100 would be count = 2 and total = 200
 */
public class TimeTotalCount {

    // privates
    private final int count;
    private final int total;

    // make one
    public TimeTotalCount(int total) {
        this.count=1;
        this.total=total;
    }

    // make one from another one
    // this is how we increment totals and keep objects immutable
    public TimeTotalCount(TimeTotalCount existingTotal, int thisTime) {
        this.count = existingTotal.getCount()+1;
        this.total = existingTotal.getTotal() + thisTime;
    }

    // get values
    public int getCount() {
        return this.count;
    }
    public int getTotal() {
        return this.total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeTotalCount{");
        sb.append("count=").append(count);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTotalCount timeTotalCount = (TimeTotalCount) o;

        if (count != timeTotalCount.count) return false;
        return total == timeTotalCount.total;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }
}
